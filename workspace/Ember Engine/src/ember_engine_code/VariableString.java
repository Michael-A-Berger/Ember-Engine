/* Name:				VariabeString
 * Author:				Michael Berger
 * 
 * Description:		A string variable accessible by
 * 		game objects and interchangeable within the
 * 		engine.
 * */

package ember_engine_code;

public class VariableString
{
	//Class-Wide Variables
	public String stringName = null;
	public String stringData = null;
	
	//[Constructor]
	public VariableString(String varName, String varData)
	{
		//Setting the class variables
		stringName = varName;
		stringData = varData;
	}
}










