/* Name:					GO_Test
 * Author:					Michael Berger
 * 
 * Description:		A simple testing game object.
 * */

package ember_engine_code;

//Imports
import java.awt.image.BufferedImage;


public class GO_Test extends GameObject
{//Start class GO_Test
	
	//Class-Wide Variables
	Sound soundReward = null;
	
	//[Constructor]
	public GO_Test(GameEngine gm, int posX, int posY, int depth, float scale)
	{
		//Getting the sprite
		BufferedImage[] sprite = new BufferedImage[1];
		sprite[0] = RetrieveSpriteFromFile("sprites/Tile_Test.png");
		
		//Setting all of the variables
		SetAll(gm, "test", posX, posY, depth, sprite, 0, scale);
		
		//Creating all of the sounds
		sounds = new Sound[1];
		//soundReward = new Sound(game, "sounds/Reward.wav");
		sounds[0] = soundReward;
	}
	
	//run
	public void run()
	{
		//TEST TEST TEST
		if (intDepth == 5)
		{
			if (CheckKeyByCharacter('d', true))
			{
				intPosX += 5;
				floatScale += 0.1;
			}
			
			if (CheckKeyByCharacter('a', true))
			{
				intPosX -= 5;
				floatScale -= 0.1;
			}
		}
		

		//TEST TEST TEST
		
		//Updating the rectangle
		UpdateRectangle();
	}
	
}//End class GO_Test










