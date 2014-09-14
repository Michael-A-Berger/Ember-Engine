/* Name:				Scene
 * Author:				Michael Berger
 * 
 * Description:		A scene that holds game objects
 * 		and interface objects for use in the game
 * 		engine. It is meant to be extended for
 * 		proper use, with the StartScene()
 * 		method being overridden for adding the
 * 		wanted game objects to the engine.
 * */

package ember_engine_code;

//Imports
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Scene
{
	//Class-Wide Objects
	BufferedImage background = null;
	ArrayList<VariableString> AL_SceneStrings = new ArrayList<VariableString>();
	ArrayList<VariableInteger> AL_SceneIntegers = new ArrayList<VariableInteger>();
	ArrayList<VariableCharacter> AL_SceneCharacters = new ArrayList<VariableCharacter>();
	ArrayList<VariableBoolean> AL_SceneBoolean = new ArrayList<VariableBoolean>();
	
	//StartScene() -- [MEANT TO BE OVERRIDDEN]
	public void StartScene(GameEngine game)
	{
		//OVERRIDE THIS METHOD!!!
	}
	
	//SetBackground()
	public void SetBackground(String fileLocation)
	{
		//Setting the background image
		background = (BufferedImage) new ImageIcon(getClass().getResource(fileLocation)).getImage();
	}
}










