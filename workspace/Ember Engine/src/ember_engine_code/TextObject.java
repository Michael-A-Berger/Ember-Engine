/* Name:				TextObject
 * Author:				Michael Berger
 * 
 * Description:		An object that holds text
 * 		which is drawn in the game.
 * */

package ember_engine_code;

//Imports
import java.awt.Font;
import java.io.InputStream;


public class TextObject
{//Start class TextObject
	
	//Class-Wide Variables
	//::: ::: ::: ::: :::
	
	//GENERAL VARIABLES
	GameEngine game;
	int intObjectID;
	String stringName = "'?'";
	String stringText = "???";
	int intPosX;
	int intPosY;
	Font fontToUse;
	int intFontSize = 0;
	int intRed = 0;
	int intGreen = 0;
	int intBlue = 0;
	int intAlpha = 255;
	
	//MANAGER COMMAND INTS
	final int MC_NEW_SCENE = 0;
	final int MC_REMOVE = 1;
	final int MC_ADD_GO = 2;
	final int MC_ADD_TEXT = 3;
	
	//::: ::: ::: ::: :::
	
	//SetAllSimple()
	public void SetAllSimple(GameEngine gm, String name, String text, int posX, int posY, String fontName, int fontSize,
								boolean customFont)
	{
		//Setting the game engine
		game = gm;
		
		//Setting the text variables
		stringName = name;
		stringText = text;
		intFontSize = fontSize;
		intPosX = posX;
		intPosY = posY;
		
		//Getting the font
		GetFont(fontName, customFont);
		
		//Setting the object ID
		intObjectID = game.objRandom.nextInt();
		
		//Checking the object ID
		CheckAndCorrectID();
	}
	
	//SetAllAdvanced()
	public void SetAllAdvanced(GameEngine gm, String name, String text, int posX, int posY, String fontName, int fontSize,
								boolean customFont, int red, int green, int blue, int alpha)
	{
		//Setting the game engine
		game = gm;
		
		//Setting the text object variables
		stringName = name;
		stringText = text;
		intFontSize = fontSize;
		intPosX = posX;
		intPosY = posY;
		
		//Setting the red value
		if (red > 255) { intRed = 255; }
		else if (red < 0) { intRed = 0; }
		else { intRed = red; }
		
		//Setting the green value
		if (green > 255) { intGreen = 255; }
		else if (green < 0) { intGreen = 0; }
		else { intGreen = green; }
		
		//Setting the blue value
		if (blue > 255) { intBlue = 255; }
		else if (blue < 0) { intBlue = 0; }
		else { intBlue = blue; }
		
		//Setting the alpha value
		if (alpha > 255) { intAlpha = 255; }
		else if (alpha < 0) { intAlpha = 0; }
		else { intAlpha = alpha; }
		
		//Getting the font
		GetFont(fontName, customFont);
		
		//Setting the object ID
		intObjectID = game.objRandom.nextInt();
		
		//Checking the object ID
		CheckAndCorrectID();
	}
	
	//GetFont()
	public void GetFont(String fontName, boolean customFont)
	{
		//IF the font is not a custom font...
		if (customFont == false)
		{
			//Creating the standard font
			fontToUse = new Font(fontName, Font.TRUETYPE_FONT, intFontSize);
		}
		//ELSE... (the font is a custom font)
		else
		{
			//Creating the input stream
			InputStream streamFont = TextObject.class.getResourceAsStream(fontName);
			
			//Trying to set the font and font variables
			try
			{
				fontToUse = Font.createFont(Font.TRUETYPE_FONT, streamFont);
				fontToUse = fontToUse.deriveFont((float) intFontSize);
				
			}
			catch (Exception e)
			{
				//Notifying the user of an error
				System.out.println("ERROR: Custom font could not be found! Details:\n");
				e.printStackTrace();
				System.out.println("\nUsing default Times New Roman font.");
				
				//Defaulting to Times New Roman font
				fontToUse = new Font("Times New Roman", Font.TRUETYPE_FONT, intFontSize);
			}
		}
	}
	
	//CheckAndCorrectID
	public void CheckAndCorrectID()
	{
		//FOR each check (undetermined or false)...
		for (int numCheck = 0; numCheck < 1; numCheck++)
		{
			//FOR each ID in use...
			for (int numCycle = 0; numCycle < game.objGameManager.arrayInUseIDs.length; numCycle++)
			{
				//IF the generated ID is already found in the list...
				if (intObjectID == game.objGameManager.arrayInUseIDs[numCycle])
				{
					//Giving the text object a new ID along with telling the loops to do another check
					intObjectID = game.objRandom.nextInt();
					numCheck--;
					numCycle = game.objGameManager.arrayInUseIDs.length;
				}
			}
		}
		
		//Updating the in-use IDs array
		game.objGameManager.UpdateArrayOfInUseIDs();
	}
	
	//run()
	public void run()
	{
		//(Mostly meant for overriding)
	}
	
}//End class TextObject










