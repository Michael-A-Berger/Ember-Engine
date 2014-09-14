/* Name:				VariableBoolean
 * Author:				Michael Berger
 * 
 * Description:		A boolean variable accessible
 * 		by game objects and interchangeable within
 * 		the engine.
 * */

package ember_engine_code;

public class VariableBoolean
{
	//Class-Wide Variables
	public String stringName;
	public boolean booleanData;
	
	//[Constructor]
	public VariableBoolean(String varName, boolean varData)
	{
		//Setting the class variables
		stringName = varName;
		booleanData = varData;
	}
}










