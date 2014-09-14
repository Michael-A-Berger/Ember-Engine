/* Name:						GameObject
 * Author:						Michael Berger
 * 
 * Description:		The base class for all of the game objects.
 * 		Stores positional, image, and various data to be used
 * 		for drawing and object operations.
 * 
 * NOTES:
 * 
 * 		- "SetAll(...)" MUST BE CALLED IN THE CONSTRUCTOR!!!
 * 		- If you override "run()", you should remember to
 * 			add the 'UpdateRectangle()' function to the end
 * 			of it.
 * 		- You can use the 'UpdateRectangleCustom(...)'
 * 			to add custom boundaries to objects.
 * */

package ember_engine_code;

//Imports
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

public class GameObject
{//Start class GameObject
	
	//Class-Wide Variables
	//::: ::: ::: ::: :::
	
	//GENERAL VARIABLES
	GameEngine game;
	public String stringName = "'?'";
	public int intPosX;
	public int intPosY;
	public int intObjectID;
	public int intDepth = 0;
	BufferedImage[] arrayImageSprites;
	int intCurrentSprite;
	private Rectangle rectangleHitbox = new Rectangle();
	Sound[] sounds = null;
	float floatScale = (float) 1.0;
	
	//MANAGER COMMAND INTS
	final int MC_NEW_SCENE = 0;
	final int MC_REMOVE = 1;
	final int MC_ADD_GO = 2;
	final int MC_ADD_TEXT = 3;
	
	//::: ::: ::: ::: :::
	
	
	//SetAll
	public void SetAll(GameEngine gm, String name, int intX, int intY, int depth, BufferedImage[] sprites, int currentSprite,
						float scale)
	{
		//Setting the variables
		game = gm;
		stringName = name;
		intPosX = intX;
		intPosY = intY;
		arrayImageSprites = sprites;
		intCurrentSprite = currentSprite;
		floatScale = scale;
		intDepth = depth;
		intObjectID = Math.abs(game.objRandom.nextInt());
		
		//Checking & changing the object ID
		CheckAndCorrectID();
		
		//Setting the variables of the rectangle
		rectangleHitbox.setLocation(intPosX, intPosY);
		rectangleHitbox.setSize(arrayImageSprites[intCurrentSprite].getWidth(null),
								arrayImageSprites[intCurrentSprite].getHeight(null));
	}
	
	//CheckAndCorrectID
	public void CheckAndCorrectID()
	{
		//FOR each check (undetermined or false)...
		for (int numCheck = 0; numCheck < 1; numCheck++)
		{
			//FOR each ID in use...
			for (int numCycle = 0; numCycle < game.objGameManager.arrayInUseIDs.length; numCycle++)
			{
				//IF the generated ID is already found in the list...
				if (intObjectID == game.objGameManager.arrayInUseIDs[numCycle])
				{
					//Giving the game object a new ID along with telling the loops to do another check
					intObjectID = game.objRandom.nextInt();
					numCheck--;
					numCycle = game.objGameManager.arrayInUseIDs.length;
				}
			}
		}
		
		//Updating the in-use IDs array
		game.objGameManager.UpdateArrayOfInUseIDs();
	}
	
	//RetrieveSpriteFromFile
	protected BufferedImage RetrieveSpriteFromFile(String fileLocation)
	{
		//Creating the buffered image
		BufferedImage retrievedSprite = null;
		
		//Trying to get the sprite from the file
		try
		{
			URL spriteURL = getClass().getResource(fileLocation);
			retrievedSprite = ImageIO.read(spriteURL);
		}
		catch (Exception e)
		{
			System.out.println("Could not read image file located at '"+fileLocation+"'!");
			e.printStackTrace();
		}
		
		//Returning the retrieved image
		return retrievedSprite;
	}
	
	//RetrieveSpriteFromSheet
	protected BufferedImage RetrieveSpriteFromSheet(int numSheet, int startPosX, int startPosY, int imageWidth, int imageHeight)
	{
		//Creating the buffered image
		BufferedImage retrievedImage = null;
		
		//Getting the sprite from the sheet
		if (0 <= numSheet && numSheet < game.arraySpriteSheets.length)
		{
			try
			{
				retrievedImage = game.arraySpriteSheets[numSheet].getSubimage(startPosX, startPosY, imageWidth, imageHeight);
			}
			catch (Exception e)
			{
				System.out.println("Could not retrieve the desired image from sprite sheet #"+numSheet+"!");
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Sprite sheet #"+numSheet+" does not exist.");
		}
		
		//Returning the retrieved sprite
		return retrievedImage;
	}
	
	//GetCurrentSprite
	public BufferedImage GetCurrentSprite()
	{
		//Returning the current sprite
		return arrayImageSprites[intCurrentSprite];
	}
	
	//SetCurrentSprite
	public void SetCurrentSprite(int num)
	{
		//Setting the current sprite
		if (num > -1 && num < arrayImageSprites.length)
		{
			intCurrentSprite = num;
		}
		else
		{
			System.out.println("GameObject "+stringName+", "+intObjectID+": Requested sprite #"+num+" not found!");
		}
	}
	
	//GetRectangle
	public Rectangle GetRectangle()
	{
		//Returning the game object's rectangle
		return rectangleHitbox;
	}
	
	//UpdateRectangle
	protected void UpdateRectangle()
	{
		//Updating the rectangle
		rectangleHitbox.setLocation(intPosX, intPosY);
		rectangleHitbox.setSize(GetCurrentSprite().getWidth(null), GetCurrentSprite().getHeight(null));
	}
	
	//UpdateRectangleCustom
	protected void UpdateRectangleCustom(int intPlusX, int intPlusY, int intMinusX, int intMinusY)
	{
		//Updating the rectangle to the custom boundaries
		rectangleHitbox.setLocation(intPlusX + intPosX, intPlusY + intPosY);
		rectangleHitbox.setSize(GetCurrentSprite().getWidth(null) + intMinusX, GetCurrentSprite().getHeight(null) + intMinusY);
	}
	
	//AddManagerCommand
	public void AddManagerCommand(int mc_command, int mc_specific, int mc_other)
	{
		//Creating and setting the command
		int[] command = new int[3];
		command[0] = mc_command;
		command[1] = mc_specific;
		command[2] = mc_other;
		
		//Adding the command
		game.objGameManager.AL_ManagerCommands.add(command);
	}
	
	//CheckForCollisionByName
	public boolean CheckForCollisionByName(String name)
	{
		//Checking for collisions with objects that have the name
		boolean check = false;
		for (int num = 0; num < game.AL_GameObjects.size(); num++)
		{
			if (game.AL_GameObjects.get(num).stringName == name
				&& CheckForCollisionByRectangle(game.AL_GameObjects.get(num).GetRectangle()) == true
				&& game.AL_GameObjects.get(num).intObjectID != intObjectID)
			{
				check = true;
			}
			
			//Diagnostics/Debugging
			if (false)		//Change to 'true' to enable
			{
				System.out.println("ID: "+stringName+", "+intObjectID);
				System.out.println("Name Chk: "+(game.AL_GameObjects.get(num).stringName == name));
				System.out.println("Rect Chk: "+CheckForCollisionByRectangle(game.AL_GameObjects.get(num).GetRectangle()));
				System.out.println("Same Chk: "+(game.AL_GameObjects.get(num).intObjectID != intObjectID)   );
				System.out.println("Overall : "+check+"\n");
			}
		}
		
		//Returning the boolean
		return check;
	}
	
	//CheckForCollisionByRectangle
	public boolean CheckForCollisionByRectangle(Rectangle rectangleOther)
	{
		//Checking to see if the rectangles collide
		boolean check = false;
		if (GetRectangle().intersects(rectangleOther))
		{
			check = true;
		}
		
		//Returning the boolean
		return check;
	}
	
	//CheckForCollisionCustom()
	public boolean CheckForCollisionCustom(String name, int posX, int posY, int width, int height)
	{
		/* NOTES:
		 * 
		 * - A string value matching [""] will mean that there
		 * 		will be no name checking for the collision.
		 * 
		 * */
		
		//Creating the variables
		boolean checkBoolean = false;
		boolean checkName = false;
		Rectangle checkRectangle = new Rectangle(posY, posX, width, height);
		
		//Determining whether to check by name
		if (name != "")
		{
			checkName = true;
		}
		
		//Doing the collision checks
		for (int num = 0; num < game.AL_GameObjects.size(); num++)
		{
			//With Name Check
			if (checkName == true
				&& game.AL_GameObjects.get(num).CheckForCollisionByRectangle(checkRectangle) == true
				&& game.AL_GameObjects.get(num).stringName == name)
			{
				//Setting the check boolean to 'true'
				checkBoolean = true;
			}
			
			//Without Name Check
			else if (game.AL_GameObjects.get(num).CheckForCollisionByRectangle(checkRectangle) == true)
			{
				//Setting the check boolean to 'true'
				checkBoolean = true;
			}
		}
		
		//Returning the collision booleans
		return checkBoolean;
	}
	
	//GetSceneVarBool
	protected VariableBoolean GetSceneVarBool(String varName)
	{
		//Defining important variables
		VariableBoolean result = null;
		
		//Finding the requested VariableBoolean in the scene ArrayList
		if (game.AL_SceneVariableBooleans.size() > 0)
		{
			//FOR loop for finding the specific VariableBoolean
			for (int num = 0; num < game.AL_SceneVariableBooleans.size(); num++)
			{
				if (game.AL_SceneVariableBooleans.get(num).stringName == varName)
				{
					result = game.AL_SceneVariableBooleans.get(num);
				}
			}
			//END FOR loop for finding the specific VariableBoolean
		}
		
		//Returning the boolean
		return result;
	}
	
	//GetSceneVarChar
	protected VariableCharacter GetSceneVarChar(String varName)
	{
		//Defining important variables
		VariableCharacter result = null;
		
		//Finding the requested VariableCharacter in the scene ArrayList
		if (game.AL_SceneVariableCharacters.size() > 0)
		{
		//FOR loop for finding the specific VariableCharacter
			for (int num = 0; num < game.AL_SceneVariableCharacters.size(); num++)
			{
				if (game.AL_SceneVariableCharacters.get(num).stringName == varName)
				{
					result = game.AL_SceneVariableCharacters.get(num);
				}
			}
			//END FOR loop for finding the specific VariableCharacter
		}
		
		//Returning the VariableCharacter
		return result;
	}
	
	//GetSceneVarInt
	protected VariableInteger GetSceneVarInt(String varName)
	{
		//Defining important variables
		VariableInteger result = null;
		
		//Finding the requested VariableInteger in the scene ArrayList
		if (game.AL_SceneVariableIntegers.size() > 0)
		{
			//FOR loop for finding the specific VariableInteger
			for (int num = 0; num < game.AL_SceneVariableIntegers.size(); num++)
			{
				if (game.AL_SceneVariableIntegers.get(num).stringName == varName)
				{
					result = game.AL_SceneVariableIntegers.get(num);
				}
			}
			//END FOR loop for finding the specific VariableInteger
		}
		
		//Returning the result
		return result;
	}
	
	//GetSceneVarString
	protected VariableString GetSceneVarString(String varName)
	{
		//Defining important variables
		VariableString result = null;
		
		//Finding the requested VariableString in the scene ArrayList
		if (game.AL_SceneVariableStrings.size() > 0)
		{
			//FOR loop for finding the specific VariableString
			for (int num = 0; num < game.AL_SceneVariableStrings.size(); num++)
			{
				if (game.AL_SceneVariableStrings.get(num).stringName == varName)
				{
					result = game.AL_SceneVariableStrings.get(num);
				}
			}
			//END FOR loop for finding the specific VariableString
		}
		
		//Returning the result
		return result;
	}
	
	//GetGlobalVarBool
	protected VariableBoolean GetGlobalVarBool(String varName)
	{
		//Defining important variables
		VariableBoolean result = null;
		
		//Finding the requested VariableBoolean in the global ArrayList
		if (game.AL_GlobalVariableBooleans.size() > 0)
		{
			//FOR loop for finding the specific VariableBoolean
			for (int num = 0; num < game.AL_GlobalVariableBooleans.size(); num++)
			{
				if (game.AL_GlobalVariableBooleans.get(num).stringName == varName)
				{
					result = game.AL_GlobalVariableBooleans.get(num);
				}
			}
			//END FOR loop for finding the specific VariableBoolean
		}
		
		//Returning the result
		return result;
	}
	
	//GetGlobalVarChar
	protected VariableCharacter GetGlobalVarChar(String varName)
	{
		//Defining important variables
		VariableCharacter result = null;
		
		//Finding the requested VariableCharacter in the global ArrayList
		if (game.AL_GlobalVariableCharacters.size() > 0)
		{
			//FOR loop for finding the specific VariableCharacter
			for (int num = 0; num < game.AL_GlobalVariableCharacters.size(); num++)
			{
				if (game.AL_GlobalVariableCharacters.get(num).stringName == varName)
				{
					result = game.AL_GlobalVariableCharacters.get(num);
				}
			}
			//END FOR loop for finding the specific VariableCharacter
		}
		
		//Returning the VariableCharacter
		return result;
	}
	
	//GetGlobalVarInt
	protected VariableInteger GetGlobalVarInt(String varName)
	{
		//Defining important variables
		VariableInteger result = null;
		
		//Finding the requested VariableInteger in the global ArrayList
		if (game.AL_GlobalVariableIntegers.size() > 0)
		{
			//FOR loop for finding the specific VariableInteger
			for (int num = 0; num < game.AL_GlobalVariableIntegers.size(); num++)
			{
				if (game.AL_GlobalVariableIntegers.get(num).stringName == varName)
				{
					result = game.AL_GlobalVariableIntegers.get(num);
				}
			}
			//END FOR loop for finding the specific VariableInteger
		}
		
		//Returning the result
		return result;
	}
	
	//GetGlobalVarString
	protected VariableString GetGlobalVarString(String varName)
	{
		//Defining important variables
		VariableString result = null;
		
		//Finding the requested VariableString in the global ArrayList
		if (game.AL_GlobalVariableStrings.size() > 0)
		{
			//FOR loop for finding the specific VariableString
			for (int num = 0; num < game.AL_GlobalVariableStrings.size(); num++)
			{
				if (game.AL_GlobalVariableStrings.get(num).stringName == varName)
				{
					result = game.AL_GlobalVariableStrings.get(num);
				}
			}
			//END FOR loop for finding the specific VariableString
		}
		
		//Returning the result
		return result;
	}
	
	//CheckKeyByCharacter()
	protected boolean CheckKeyByCharacter(char key, boolean position)
	{
		//Checking to see if the key is in the desired position
		boolean result = false;
		if (game.objInputManager.allKeys[(key-32)] == position)
		{
			result = true;
		}
		
		//Returning the boolean
		return result;
	}
	
	//CheckKeyByCode
	protected boolean CheckKeyByCode(byte code, boolean position)
	{
		//Checking to see if the key is in the desired position
		boolean result = false;
		if (game.objInputManager.allKeys[code] == position)
		{
			result = true;
		}
		
		//Returning the boolean
		return result;
	}
	
	//CheckMouseButton()
	protected boolean CheckMouseButton(int buttonMouse, boolean position)
	{
		//Checking to see if the desired mouse button is in the desired position
		boolean result = false;
		if (game.objInputManager.allMouseActions[(buttonMouse - 1)] == position)
		{
			result = true;
		}
		
		//Returning the result
		return result;
	}
	
	
	//CheckMousePositionRegion
	protected boolean CheckMousePositionRegion(int posX, int posY, int width, int height, boolean checkInside)
	{
		//Defining the result variable
		boolean result = false;
		
		//External check
		if (checkInside == true)
		{
			//Checking inside the region
			if (game.objInputManager.shortMouseX >= posX
				&& game.objInputManager.shortMouseX <= (posX + width)
				&& game.objInputManager.shortMouseY >= posY
				&& game.objInputManager.shortMouseY <= (posY + height))
			{
				//Setting the result boolean to true
				result = true;
			}
		}
		else
		{
			//Checking outside the region
			if (game.objInputManager.shortMouseX < posX
				|| game.objInputManager.shortMouseX > (posX + width)
				|| game.objInputManager.shortMouseY < posY
				|| game.objInputManager.shortMouseY > (posY + height))
			{
				//Setting the result boolean to true
				result = true;
			}
		}
		
		//Returning the result
		return result;
	}
	
	
	//run() -- [Really only meant to be over-written.]
	public void run()
	{
		//IF NO RUN OPTION; UPDATE RECTANGLE
		UpdateRectangle();
	}
	
	//RemoveObject()
	public void RemoveObject()
	{
		//Checking if any sounds are present
		if (sounds != null)
		{
			//If any sounds are found, stop them
			for (int num = 0; num < sounds.length; num++)
			{
				sounds[num].Stop();
			}
		}
		
		//Adding a remove command to the game manager
		AddManagerCommand(MC_REMOVE, intObjectID, 0);
	}
	
}//End class GameObject










