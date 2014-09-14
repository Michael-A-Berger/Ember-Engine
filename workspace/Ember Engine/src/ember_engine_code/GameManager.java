/* Name:						GameManager
 * Author:						Michael Berger
 * 
 * Description:		The class that manages the game objects
 * 		within the game. Can add, change, and remove objects
 * 		from the game object array lists.
 * 
 * FORMAT:			An array of integer values.
 * */

package ember_engine_code;

//Imports
import java.util.ArrayList;

public class GameManager
{//Start class GameManager
	
	//Class-Wide Variables
	// ::: ::: ::: ::: :::
	
	//GENERAL VARIABLES
	public ArrayList<int[]> AL_ManagerCommands = new ArrayList<int[]>();
	public ArrayList<GameObject> AL_GameObjectsToAdd = new ArrayList<GameObject>();
	public ArrayList<TextObject> AL_TextObjectsToAdd = new ArrayList<TextObject>();
	int[] arrayInUseIDs = new int[0];
	GameEngine game;
	Scene[] arrayGameScenes;
	
	//COMMAND VARIABLES
	private final byte NEW_SCENE = 0;
	private final byte REMOVE = 1;
	private final byte ADD_GO = 2;
	private final byte ADD_TEXT = 3;
	
	// ::: ::: ::: ::: :::
	
	
	//[Constructor]
	public GameManager(GameEngine gm)
	{
		//Getting the game instance
		game = gm;
	}
	
	//DestroyByID()
	private void DestroyByID(int identity)
	{
		//Creating the 'Deleted' variable
		boolean objectDeleted = false;
		
		//Looking through the standard game objects
		for (int num = 0; num < game.AL_GameObjects.size()   &&   objectDeleted == false; num++)
		{
			if (game.AL_GameObjects.get(num).intObjectID == identity)
			{
				game.AL_GameObjects.remove(num);
				objectDeleted = true;
			}
		}
		
		//Looking through the text game objects
		for (int num = 0; num < game.AL_TextObjects.size()   &&   objectDeleted == false; num++)
		{
			if (game.AL_TextObjects.get(num).intObjectID == identity)
			{
				game.AL_TextObjects.remove(num);
				objectDeleted = true;
			}
		}
		
		//If the object was not deleted...
		if (objectDeleted == false)
		{
			System.out.println("Object with ID '"+identity+"' was not found & thus not deleted!");
		}
	}
	
	//SetNewScene()
	public void SetNewScene(int specificScene)
	{
		//If the requested scene number is in the array...
		if (specificScene >= 0   &&   specificScene < arrayGameScenes.length)
		{
			//...Delete the current scene and add the new scene
			game.RemoveScene();
			game.NewScene(arrayGameScenes[specificScene]);
		}
		//Else...
		else
		{
			//Display that the requested scene number does not exist
			System.out.println("The requested scene with number '"+specificScene+"' does not exist!");
		}
	}
	
	//AddNewGameObject()
	public void AddNewGameObject(int objectID)
	{
		//Adding the object to the game objects ArrayList
		for (int num = 0; num < AL_GameObjectsToAdd.size(); num++)
		{
			if (AL_GameObjectsToAdd.get(num).intObjectID == objectID)
			{
				game.AL_GameObjects.add(AL_GameObjectsToAdd.get(num));
				AL_GameObjectsToAdd.remove(num);
				break;
			}
			else if (num == AL_GameObjectsToAdd.size() - 1)
			{
				System.out.println("Object with ID '"+objectID+"' could not be found & added to AL_GameObject!");
			}
		}
	}
	
	//AddNewTextObject()
	public void AddNewTextObject(int objectID)
	{
		//Adding the object to the text objects ArrayList
		for (int num = 0; num < AL_TextObjectsToAdd.size(); num++)
		{
			if (AL_TextObjectsToAdd.get(num).intObjectID == objectID)
			{
				game.AL_TextObjects.add(AL_TextObjectsToAdd.get(num));
				AL_TextObjectsToAdd.remove(num);
				break;
			}
			else if (num == AL_TextObjectsToAdd.size() - 1)
			{
				System.out.println("Object with ID '"+objectID+"' could not be found & added to AL_TextObject!");
			}
		}
	}
	
	//UpdateArrayOfInUseIDs()
	public void UpdateArrayOfInUseIDs()
	{
		//Redefining the 'In Use IDs' array
		arrayInUseIDs = new int[game.AL_GameObjects.size() + game.AL_TextObjects.size()];
		
		//Filling the 'In Use IDs' array
		for (int numCycle = 0; numCycle < arrayInUseIDs.length; numCycle++)
		{
			if (game.AL_GameObjects.size() > 0   &&   numCycle < game.AL_GameObjects.size())
			{
				arrayInUseIDs[numCycle] = game.AL_GameObjects.get(numCycle).intObjectID;
			}
			else if (game.AL_TextObjects.size() > 0   &&   game.AL_GameObjects.size() > 0)
			{
				arrayInUseIDs[numCycle] = game.AL_TextObjects.get(   numCycle - (game.AL_GameObjects.size())   ).intObjectID;
			}
			else if (game.AL_TextObjects.size() > 0   &&   game.AL_GameObjects.size() <= 0)
			{
				arrayInUseIDs[numCycle] = game.AL_TextObjects.get(numCycle).intObjectID;
			}
		}
	}
	
	//run()
	public void run()
	{
		//WHILE loop for game commands
		while (AL_ManagerCommands.size() > 0)
		{
			//Checking which command to pick
			// ::: ::: ::: ::: :::
			
			/* DESTROY
			 * 
			 * Format: [COMMAND #], object ID
			 * */
			if (AL_ManagerCommands.get(0)[0] == REMOVE)
			{
				DestroyByID(AL_ManagerCommands.get(0)[1]);
			}
			
			/* SET NEW SCENE
			 * 
			 * Format: [COMMAND #], Scene #
			 * */
			if (AL_ManagerCommands.get(0)[0] == NEW_SCENE)
			{
				SetNewScene(AL_ManagerCommands.get(0)[1]);
			}
			
			/* ADD_GO
			 * 
			 * Format: [COMMAND #], object ID (of game object in ToAdd arraylist)
			 * */
			if (AL_ManagerCommands.get(0)[0] == ADD_GO)
			{
				AddNewGameObject(AL_ManagerCommands.get(0)[1]);
			}
			
			/* ADD_TEXT
			 * 
			 * Format: [COMMAND #], object ID (of text object in ToAdd arraylist)
			 * */
			if (AL_ManagerCommands.get(0)[0] == ADD_TEXT)
			{
				AddNewTextObject(AL_ManagerCommands.get(0)[1]);
			}
			
			// ::: ::: ::: ::: :::
			
			//Removing the command
			AL_ManagerCommands.remove(0);
		}
		
		UpdateArrayOfInUseIDs();
	}
	
}//End class GameManager










