/* Name:						Game
 * Author:						Michael Berger
 * 
 * Description:		The class that initiates the game frame
 * 		along with the game canvas. It also holds the
 * 		updating and rendering methods.
 * */

package ember_engine_code;

//Imports
import javax.swing.JFrame;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;


public class GameEngine implements Runnable
{//Start class Game
	
	//Class-Wide Variables
	// ::: ::: ::: ::: :::
	
	//GENERAL OBJECTS & VARIABLES
	JFrame gameFrame = null;
	Canvas gameCanvas;
	BufferStrategy gameBuffStrat;
	final byte SCREEN_OFFSET_X = 7;
	final byte SCREEN_OFFSET_Y = 29;
	short SCREEN_WIDTH;
	short SCREEN_HEIGHT;
	short CANVAS_WIDTH;
	short CANVAS_HEIGHT;
	short CANVAS_TOLERANCE;
	public InputManager objInputManager = new InputManager(this);
	public GameManager objGameManager = new GameManager(this);
	public Random objRandom = new Random();
	public float floatVolume = -35f;
	public ReaderAndWriter objReaderAndWriter = new ReaderAndWriter();
	BufferedImage imageBackground = null;
	public GO_Camera gameCamera = null;
	public BufferedImage[] arraySpriteSheets;
	
	//GAME LOOP VARIABLES
	public boolean booleanCanRun = true;
	public boolean stopGame = false;
	public byte timeFPS = 120;
	long timeLastUpdate = System.nanoTime();
	long timeLastRender = System.nanoTime();
	long timeToUpdate = 1000000000/(timeFPS/2);
	long timeBetweenUpdate;
	long timeBetweenRender;
	
	//ARRAY LISTS
	ArrayList<GameObject> AL_GameObjects = new ArrayList<GameObject>();
	ArrayList<TextObject> AL_TextObjects = new ArrayList<TextObject>();
	ArrayList<VariableString> AL_GlobalVariableStrings = new ArrayList<VariableString>();
	ArrayList<VariableInteger> AL_GlobalVariableIntegers = new ArrayList<VariableInteger>();
	ArrayList<VariableCharacter> AL_GlobalVariableCharacters = new ArrayList<VariableCharacter>();
	ArrayList<VariableBoolean> AL_GlobalVariableBooleans = new ArrayList<VariableBoolean>();
	ArrayList<VariableString> AL_SceneVariableStrings = new ArrayList<VariableString>();
	ArrayList<VariableInteger> AL_SceneVariableIntegers = new ArrayList<VariableInteger>();
	ArrayList<VariableCharacter> AL_SceneVariableCharacters = new ArrayList<VariableCharacter>();
	ArrayList<VariableBoolean> AL_SceneVariableBooleans = new ArrayList<VariableBoolean>();
	
	// ::: ::: ::: ::: :::
	
	//Prepare()
	public void PrepareEngine(String name, short width, short height, short tolerance, boolean enableClosing)
	{
		//Setting the variables
		SCREEN_WIDTH = (short) (width + SCREEN_OFFSET_X);
		SCREEN_HEIGHT = (short) (height + SCREEN_OFFSET_Y);
		CANVAS_WIDTH = SCREEN_WIDTH;
		CANVAS_HEIGHT = SCREEN_HEIGHT;
		CANVAS_TOLERANCE = tolerance;
		
		//Defining the game frame
		gameFrame = new JFrame(name);
		gameFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		if (enableClosing == true)
		{
			gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		else
		{
			gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
		
		
		gameFrame.setLayout(null);
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null);
		
		//Defining and adding the game canvas
		gameCanvas = new Canvas();
		gameCanvas.setBounds(-(CANVAS_TOLERANCE/2), -(CANVAS_TOLERANCE/2), CANVAS_WIDTH + CANVAS_TOLERANCE, CANVAS_HEIGHT + CANVAS_TOLERANCE);
		gameCanvas.setIgnoreRepaint(true);
		gameCanvas.addKeyListener(objInputManager);
		gameCanvas.addMouseListener(objInputManager);
		gameCanvas.addMouseMotionListener(objInputManager);
		gameFrame.add(gameCanvas);
		
		//Creating the game camera
		gameCamera = new GO_Camera(this, 0, 0);
		
		//Setting the frame to be visible
		gameFrame.setVisible(true);
		
		//Creating the game buffer strategy
		gameCanvas.createBufferStrategy(2);
		gameBuffStrat = gameCanvas.getBufferStrategy();
		

	}
	
	//SetAllScenes()
	public void SetAllScenes(Scene allScenes[])
	{
		//Setting all of the scenes in the game manager
		objGameManager.arrayGameScenes = allScenes;
	}
	
	//AddScene
	public void NewScene(Scene scene)
	{
		//Starting the scene
		scene.StartScene(this);
		
		//Setting the background (if available)
		if (scene.background != null)
		{
			imageBackground = scene.background;
		}
	}
	
	//RemoveScene
	public void RemoveScene()
	{
		//Removing all of the game objects
		for (int num = 0; num < AL_GameObjects.size(); num++)
		{
			AL_GameObjects.get(num).RemoveObject();
		}
		
		//Removing the scene variables
		AL_SceneVariableStrings.removeAll(AL_SceneVariableStrings);
		AL_SceneVariableIntegers.removeAll(AL_SceneVariableIntegers);
		AL_SceneVariableCharacters.removeAll(AL_SceneVariableCharacters);
		AL_SceneVariableBooleans.removeAll(AL_SceneVariableBooleans);
		
		//Removing the background image
		imageBackground = null;
	}
	
	//RunEngine()
	public void RunEngine()
	{
		//Starting the game engine
		this.run();		
	}
	
	//Update()
	public void Update()
	{
		//Running the game manager
		objGameManager.run();
		
		//Running the game camera
		gameCamera.run();
		
		//Running the GAME objects
		if (AL_GameObjects.size() > 0)
		{
			for (int num = 0; num < AL_GameObjects.size(); num++)
			{
				AL_GameObjects.get(num).run();
			}
		}
		
		//Running the TEXT objects
		
		//Reorganizing the GAME objects array for drawing
		AL_GameObjects = OrganizeForDrawing(AL_GameObjects);
	}
	
	
	//Render()
	public void Render()
	{
		//Creating the 2D graphics object
		Graphics2D g2d = (Graphics2D) gameBuffStrat.getDrawGraphics();
		
		//Clearing the game canvas
		g2d.clearRect(0, 0, SCREEN_WIDTH + CANVAS_TOLERANCE, SCREEN_HEIGHT + CANVAS_TOLERANCE);
		
		//Drawing the objects
		// ::: ::: ::: ::: :::
		
		//BACKGROUND IMAGE
		if (imageBackground != null)
		{
			g2d.drawImage(imageBackground, CANVAS_TOLERANCE, CANVAS_TOLERANCE, null);
		}
		
		//GAME OBJECTS FOR loop
		for (int num = 0; num < AL_GameObjects.size(); num++)
		{
			/*
			//The Within-Camera-Range Check
			if (AL_GameObjects.get(num).intPosX > gameCamera.intPosX - (CANVAS_TOLERANCE/2)
					&& AL_GameObjects.get(num).intPosX < gameCamera.intPosX + SCREEN_WIDTH + (CANVAS_TOLERANCE/2)
					&& AL_GameObjects.get(num).intPosY > gameCamera.intPosY - (CANVAS_TOLERANCE/2)
					&& AL_GameObjects.get(num).intPosY < gameCamera.intPosY + SCREEN_HEIGHT + (CANVAS_TOLERANCE/2))
			{
		
				//Drawing the sprite
				g2d.drawImage(AL_GameObjects.get(num).GetCurrentSprite(),
								((CANVAS_TOLERANCE/2) + AL_GameObjects.get(num).intPosX - gameCamera.intPosX),
								((CANVAS_TOLERANCE/2) + AL_GameObjects.get(num).intPosY - gameCamera.intPosY),
								null);
			}
			*/
			
			//Drawing the sprite
			g2d.drawImage(AL_GameObjects.get(num).GetCurrentSprite(),
							((CANVAS_TOLERANCE/2) + AL_GameObjects.get(num).intPosX - gameCamera.intPosX),
							((CANVAS_TOLERANCE/2) + AL_GameObjects.get(num).intPosY - gameCamera.intPosY),
							(int) (AL_GameObjects.get(num).GetCurrentSprite().getWidth() * AL_GameObjects.get(num).floatScale),
							(int) (AL_GameObjects.get(num).GetCurrentSprite().getHeight() * AL_GameObjects.get(num).floatScale),
							null);
		}
		//END GAME OBJECTS FOR loop
		
		//TEXT OBJECTS FOR loop
		for (int num = 0; num < AL_TextObjects.size(); num++)
		{
			/*
			//The Within-Camera-Range Check
			if (AL_TextObjects.get(num).intPosX > gameCamera.intPosX - (CANVAS_TOLERANCE/2)
					&& AL_TextObjects.get(num).intPosX < gameCamera.intPosX + SCREEN_WIDTH + (CANVAS_TOLERANCE/2)
					&& AL_TextObjects.get(num).intPosY > gameCamera.intPosY - (CANVAS_TOLERANCE/2)
					&& AL_TextObjects.get(num).intPosY < gameCamera.intPosY + SCREEN_HEIGHT + (CANVAS_TOLERANCE/2))
			{
				//Writing the text
				g2d.setFont(AL_TextObjects.get(num).fontToUse);
				g2d.setColor(new Color(AL_TextObjects.get(num).intRed, AL_TextObjects.get(num).intGreen,
										AL_TextObjects.get(num).intBlue, AL_TextObjects.get(num).intAlpha));
				g2d.drawString(AL_TextObjects.get(num).stringText,
								((CANVAS_TOLERANCE/2) + AL_TextObjects.get(num).intPosX - gameCamera.intPosX),
								((CANVAS_TOLERANCE/2) + AL_TextObjects.get(num).intPosY - gameCamera.intPosY));
			}
			*/
			
			//Writing the text
			g2d.setFont(AL_TextObjects.get(num).fontToUse);
			g2d.setColor(new Color(AL_TextObjects.get(num).intRed, AL_TextObjects.get(num).intGreen,
									AL_TextObjects.get(num).intBlue, AL_TextObjects.get(num).intAlpha));
			g2d.drawString(AL_TextObjects.get(num).stringText,
							((CANVAS_TOLERANCE/2) + AL_TextObjects.get(num).intPosX - gameCamera.intPosX),
							((CANVAS_TOLERANCE/2) + AL_TextObjects.get(num).intPosY - gameCamera.intPosY));
			
		}
		//END TEXT OBJECTS FOR loop
		
		//RESETTING THE DRAW STRING FUNCTION
		g2d.setFont(new Font("TimesRoman", Font.TRUETYPE_FONT, 12));
		g2d.setColor(new Color(0, 0, 0, 255));
		
		//DEBUG INFO
		if (objInputManager.allKeys[121] == true)
		{
			g2d.drawString("UPS: " + (   1000000000/((timeBetweenUpdate/2)+1)   ), SCREEN_WIDTH, SCREEN_HEIGHT);
			g2d.drawString("RPS: " + (   1000000000/((timeBetweenRender/2)+1)   ), SCREEN_WIDTH, SCREEN_HEIGHT+15);
		}
		
		// ::: ::: ::: ::: :::
		
		//Disposing of the 2D graphics objects
		g2d.dispose();
		
		//Showing the buffer strategy
		gameBuffStrat.show();
	}
	
	//run()
	public void run()
	{
		//The over-arching WHILE loop
		while (stopGame == false)
		{
			//Boolean check to see if the thread can run
			if (booleanCanRun == true)
			{
				//Checking the time to update
				if (timeLastUpdate + timeToUpdate <= System.nanoTime())
				{
					//Getting the number for calculating the frames per second
					timeBetweenUpdate = System.nanoTime() - timeLastUpdate;
					
					//Setting the last update time
					timeLastUpdate = System.nanoTime();
				
					//Updating the game objects
					Update();
				}
				
				//Getting the time between calling the render command
				timeBetweenRender = System.nanoTime() - timeLastRender;
				
				//Setting the last render time
				timeLastRender = System.nanoTime();
				
				//Drawing the game objects
				Render();
			}
		}
	}
	
	//OrganizeForDrawing
	public ArrayList<GameObject> OrganizeForDrawing(ArrayList<GameObject> AL_messy)
	{
		//Creating the variables
		ArrayList<GameObject> AL_organized = new ArrayList<GameObject>();
		int numLowest = Integer.MAX_VALUE;
		int numChecksNeeded = AL_messy.size();
		int numInArray = 0;
		
		//Organizing the array list
		if (AL_messy.size() > 0)
		{
			for (int num1 = 0; num1 < numChecksNeeded; num1++)
			{
				//FOR loop for identifying lowest depth
				for (int num2 = 0; num2 < AL_messy.size(); num2++)
				{
					if (numLowest > AL_messy.get(num2).intDepth)
					{
						numLowest = AL_messy.get(num2).intDepth;
						numInArray = num2;
					}
				}
				
				//Adding and removing the game object
				AL_organized.add(AL_messy.get(numInArray));
				AL_messy.remove(numInArray);
				
				//Resetting the important variables
				numLowest = Integer.MAX_VALUE;
				numInArray = 0;
			}
		}
		
		//Returning the correct array
		return AL_organized;
	}
	
}//End class Game










