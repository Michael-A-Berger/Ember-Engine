/* Name:				VariableInteger
 * Author:				Michael Berger
 * 
 * Description:		An integer variable accessible
 * 		by game objects and interchangeable within
 * 		the engine.
 * */

package ember_engine_code;

public class VariableInteger
{
	//Class-Wide Variables
	public String stringName;
	public int intData;
	
	//[Constructor]
	public VariableInteger(String varName, int varData)
	{
		//Setting the class variables
		stringName = varName;
		intData = varData;
	}
}










