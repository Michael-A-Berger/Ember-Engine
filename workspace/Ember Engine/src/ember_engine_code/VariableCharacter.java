/* Name:				VariableCharacter
 * Author:				Michael Berger
 * 
 * Description:		A character variable accessible
 * 		by game objects and interchangeable within
 * 		the engine.
 * */

package ember_engine_code;

public class VariableCharacter
{
	//Class-Wide Variables
	public String stringName;
	public char charData;
	
	//[Constructor]
	public VariableCharacter(String varName, char varData)
	{
		//Setting the class variables
		stringName = varName;
		charData = varData;
	}
}










