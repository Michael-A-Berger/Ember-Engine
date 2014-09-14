/* Name:					ReaderAndWriter
 * Author:					Michael Berger
 * 
 * Description:		A reader and writer for the game engine.
 * 		One can write text data and read text data to/from
 * 		files.
 * */

package ember_engine_code;

//Imports
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


public class ReaderAndWriter
{//Start class ReaderAndWriter
	
	//Class-Wide Variables
	public String stringLineSeparator = "|||";
	
	//ReadFromFileAll()
	public String ReadFromFileAll(String fileLocation)
	{
		//Creating the variables
		String stringReturned = null;
		String stringLoop = "";
		
		//Surrounding Try/Catch Statement
		try
		{
			//Preparing the file reader
			FileReader freader = new FileReader(new File(  getClass().getResource(fileLocation).toURI()  ));
			BufferedReader reader = new BufferedReader(freader);
			
			//Reading the file
			while(  (stringLoop = reader.readLine())  != null)
			{
				//Adding the new line to the returned string
				stringReturned += stringLoop + stringLineSeparator;
			}
			
			//Closing the reader
			reader.close();
		}
		catch (Exception e)
		{
			System.out.println("ReaderFromFileAll() failed to read from the file! Specific error;");
			e.printStackTrace();
		}
		
		
		//Returning the string
		return stringReturned;
	}
	
	//ReadFromFileAllArray()
	public String[] ReadFromFileAllArray(String fileLocation)
	{
		//Creating the variables
		ArrayList<String> stringsReturned = new ArrayList<String>();
		String stringLoop;
		
		//Surrounding Try/Catch Statement
		try
		{
			//Preparing the file reader
			FileReader freader = new FileReader(new File(  getClass().getResource(fileLocation).toURI()  ));
			BufferedReader reader = new BufferedReader(freader);
			
			//Reading the file
			while(  (stringLoop = reader.readLine())  != null)
			{
				//Adding the new line to the returned string
				stringsReturned.add(stringLoop);
			}
			
			//Closing the reader
			reader.close();
		}
		catch (Exception e)
		{
			System.out.println("ReaderFromFileAllArray() failed to read from the file! Specific error;");
			e.printStackTrace();
		}
		
		//Returning the array
		return (String[]) stringsReturned.toArray();
	}
	
	//ReadFromFileGetLine()
	public String ReadFromFileGetLine(String fileLocation, String lineToSearchFor)
	{
		//Creating the variables
		String stringReturned = "";
		
		//Surrounding try/catch statement
		try
		{
			//Creating the reader
			FileReader freader = new FileReader(new File(  getClass().getResource(fileLocation).toURI()  ));
			BufferedReader reader = new BufferedReader(freader);
			
			//Looking for the requested line
			while(stringReturned != null   &&   stringReturned.indexOf(lineToSearchFor)  == -1)
			{
				stringReturned = reader.readLine();
			}
					
			//Closing the reader
			reader.close();
		}
		catch (Exception e)
		{
			System.out.println("ReaderFromFileGetLine() failed to read from the file! Specific error;");
			e.printStackTrace();
		}
		
		//Double-checking the return string
		if (stringReturned == null)
		{
			System.out.println("ReadFromFileGetLine() could not find the specified string! Null value returned.");
		}
		
		//Returning the selected string
		return stringReturned;
	}
	
	//WriteToFileAll()
	public boolean WriteToFileAll(String fileLocation, String[] stringDataArray)
	{
		//Creating the result boolean
		boolean result = false;
		
		//Surrounding try/catch statement
		try
		{
			//Creating the writer
			FileWriter fwriter = new FileWriter(new File(  getClass().getResource(fileLocation).toURI()  ));
			BufferedWriter writer = new BufferedWriter(fwriter);
			
			//Writing all of the strings to the file
			for (int num = 0; num < stringDataArray.length; num++)
			{
				//Writing the current line
				writer.write(stringDataArray[num]);
				
				//Checking to see if writer should create new line
				if (num < stringDataArray.length - 1)
				{
					writer.newLine();
				}
			}
			
			//Closing the writer
			writer.close();
			
			//Setting the result boolean to 'true'
			result = true;
		}
		catch (Exception e)
		{
			System.out.println("WriteToFileAll() could not write to the file! Specific error;");
			e.printStackTrace();
		}
		
		//Returning the result boolean
		return result;
	}
	
	//WriteToFileAppend()
	public boolean WriteToFileAppend(String fileLocation, String[] stringDataArray)
	{
		//Creating the result boolean
		boolean result = false;
		
		//Surrounding try/catch statement
		try
		{
			//Creating the writer (+ setting append boolean to true)
			FileWriter fwriter = new FileWriter(new File(  getClass().getResource(fileLocation).toURI()  ), true);
			BufferedWriter writer = new BufferedWriter(fwriter);
			
			//Writing all of the strings to the file
			for (int num = 0; num < stringDataArray.length; num++)
			{
				//Writing the current line
				writer.write(stringDataArray[num]);
				
				//Checking to see if writer should create new line
				if (num < stringDataArray.length - 1)
				{
					writer.newLine();
				}
			}
			
			//Closing the writer
			writer.close();
			
			//Setting the result boolean to 'true'
			result = true;
			
		}
		catch (Exception e)
		{
			System.out.println("WriteToFileAppend() could not write to the file! Specific error;");
			e.printStackTrace();
		}
		
		//Returning the result boolean
		return result;
	}
	
	
}//End class ReaderAndWriter










