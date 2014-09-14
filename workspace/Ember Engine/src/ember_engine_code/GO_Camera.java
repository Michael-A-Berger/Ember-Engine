/* Name:					GO_Camera
 * Author:					Michael Berger
 * 
 * Description:		The camera of the game. This object is
 * 		a mandatory object for the game as the render location
 * 		of other game objects is based off of this game
 * 		object.
 * */

package ember_engine_code;

//Imports
import java.awt.image.BufferedImage;

public class GO_Camera extends GameObject
{//Start class GO_Camera
	
	//Class-Wide Variables
	public short timeWait = 1;
	public short timeToNextX = 0;
	public short timeToNextY = 0;
	public short speedCamera = 5;
	private GameObject GO_ToFollow = null;
	public VariableCharacter VarChar_Up = null;
	private VariableCharacter VarChar_Right = null;
	private VariableCharacter VarChar_Down = null;
	private VariableCharacter VarChar_Left = null; 
	
	//[Constructor]
	public GO_Camera(GameEngine gm, int posX, int posY)
	{
		//Getting the blank sprite
		BufferedImage[] sprite = new BufferedImage[1];
		sprite[0] = RetrieveSpriteFromFile("sprites/Blank.png");
		
		//Setting all of the variables
		SetAll(gm, "Camera", posX, posY, (byte) -100, sprite, 0, (float) 1);
	}
	
	//SetMovementVarChars()
	public void SetMovementVarChars(char charUp, char charRight, char charDown, char charLeft)
	{
		//Setting the movement variable characters
		VarChar_Up = new VariableCharacter("CameraUp", charUp);
		VarChar_Right = new VariableCharacter("CameraRight", charRight);
		VarChar_Down = new VariableCharacter("CameraDown", charDown);
		VarChar_Left = new VariableCharacter("CameraLeft", charLeft);
	}
	
	//SetObjectToFollow()
	public void SetObjectToFollow(GameObject go_follow)
	{
		//Setting the game object to follow
		GO_ToFollow = go_follow;
	}
	
	//Reset()
	public void Reset()
	{
		//Resetting the camera position
		intPosX = 0;
		intPosY = 0;
		
		//Removing the key bindings
		VarChar_Up = null;
		VarChar_Right = null;
		VarChar_Down = null;
		VarChar_Left = null;
		
		//Removing the game object to follow
		GO_ToFollow = null;
	}
	
	//run
	public void run()
	{
		//Following the designated game object
		if (GO_ToFollow != null && timeToNextY == 0)
		{
			timeToNextY = 1;
			intPosX = GO_ToFollow.intPosX - (game.CANVAS_WIDTH/2)
						+ (GO_ToFollow.arrayImageSprites[GO_ToFollow.intCurrentSprite].getWidth()/2);
			intPosY = GO_ToFollow.intPosY - (game.CANVAS_HEIGHT/2)
						+ (GO_ToFollow.arrayImageSprites[GO_ToFollow.intCurrentSprite].getHeight()/2);
		}
		
		//Moving based on the key strokes
		else if (VarChar_Up != null)
		{
			//::: ::: ::: ::: :::
			
			//UP
			if (CheckKeyByCharacter(VarChar_Up.charData, true) && timeToNextY == 0)
			{
				intPosY -= speedCamera;
				timeToNextY = timeWait;
			}
			
			//RIGHT
			if (CheckKeyByCharacter(VarChar_Right.charData, true) && timeToNextX == 0)
			{
				intPosX += speedCamera;
				timeToNextX = timeWait;
			}
			
			//DOWN
			if (CheckKeyByCharacter(VarChar_Down.charData, true) && timeToNextY == 0)
			{
				intPosY += speedCamera;
				timeToNextY = timeWait;
			}
			
			//LEFT
			if (CheckKeyByCharacter(VarChar_Left.charData, true) && timeToNextX == 0)
			{
				intPosX -= speedCamera;
				timeToNextX = timeWait;
			}
			
			//::: ::: ::: ::: :::
		}
		
		
		//Decreasing time needed to move again
		if (timeToNextY > 0)
		{
			timeToNextY--;
		}
		if (timeToNextX > 0)
		{
			timeToNextX--;
		}
	}
	
}//End class GO_Camera










