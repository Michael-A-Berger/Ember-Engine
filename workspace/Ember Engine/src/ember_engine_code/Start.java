/* Name:						Start
 * Author:						Michael Berger
 * 
 * Description:		The Ember Engine, a custom game engine
 * 		that is open-source. (Kind of. It's still not
 * 		released.)
 *
 * NOTE: This file is not important to the engine. It's
 * 		simply a testing file.
 * 
 * */

package ember_engine_code;

import java.util.ArrayList;
import java.util.Arrays;

public class Start
{//Start class Start
	
	//Class-Wide Variables
	
	
	//Main
	public static void main(String[] args)
	{
		//Creating the engine
		GameEngine engine = new GameEngine();
		
		//Preparing the engine
		engine.PrepareEngine("Ember Engine 0.5 (No Changes, just Github testing)", (short) 800, (short) 600, (short) 200, true);
		
		//Adding some test game objects
		//TEST TEST TESTS
		Scene[] arrayScenes = new Scene[2];
		
		arrayScenes[0] = new SC_Test1();
		arrayScenes[1] = new SC_Test2();
		
		engine.SetAllScenes(arrayScenes);
		
		engine.NewScene(engine.objGameManager.arrayGameScenes[0]);
		
		//TEST TEST TEST
		
		//Running the engine
		engine.run();
	}
	
}//End class Start










