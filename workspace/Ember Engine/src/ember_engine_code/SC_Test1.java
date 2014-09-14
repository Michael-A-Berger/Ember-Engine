/* Name:				SC_Test1
 * Author:				Michael Berger
 * 
 * Description:			A class used to test the
 * 		'Scene' class.
 * */

package ember_engine_code;

public class SC_Test1 extends Scene
{//Start class SC_Test1
	
	//Class-Wide Variables
	
	
	//StartScene()
	public void StartScene(GameEngine game)
	{
		//Adding the game objects
		game.AL_GameObjects.add(new GO_Test(game, 100, 100, 5, (float) 1.5));
		game.AL_GameObjects.add(new GO_Test(game, -50, -50, 3, 1));
		TextObject to_Test1 = new TextObject();
		to_Test1.SetAllAdvanced(game, "TextText1", "THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG. 1234567890", -100, -100,
								"fonts/SDS_6x6.ttf", 14, true, 0, 200, 0, 100);
		game.AL_TextObjects.add(to_Test1);
		TextObject to_Test2 = new TextObject();
		to_Test2.SetAllAdvanced(game, "TextText2", "The quick brown fox jumps over the lazy dog. 1234567890", -100, 90,
								"Comic Sans MS", 20, false, 100, 0, 0, 255);
		game.AL_TextObjects.add(to_Test2);
		game.gameCamera.SetObjectToFollow(game.AL_GameObjects.get(0));
		
		//Adding the global variables
	}
	
}//End class SC_Test1










