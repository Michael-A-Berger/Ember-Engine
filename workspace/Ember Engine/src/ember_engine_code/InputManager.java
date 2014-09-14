/* Name:						InputManager
 * Author:						Michael Berger
 * 
 * Description:		The class that handles all of the inputs.
 * 		"Inputs" includes keyboard and mouse actions.
 * */

package ember_engine_code;

//Imports
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class InputManager implements KeyListener, MouseMotionListener, MouseListener
{//Start class InputManager
	
	//Class-Wide Variables
	GameEngine game;
	public boolean[] allKeys = new boolean[256];
	public boolean[] allMouseActions = new boolean[3];
	public short shortMouseX = 0;
	public short shortMouseY = 0;
	
	
	//[Constructor]
	public InputManager(GameEngine gm)
	{
		//Setting the Game object
		game = gm;
		
		//Defining all of the key booleans as 'False'
		for (int num = 0; num < 256; num++)
		{
			allKeys[num] = false;
		}
		
		//Defining all of the mouse action booleans as 'False'
		for (int num = 0; num < 3; num++)
		{
			allMouseActions[num] = false;
		}
	}

	//keyPressed()
	public void keyPressed(KeyEvent ke)
	{
		//Setting the key boolean to 'True'
		if (ke.getKeyCode() >= 0   &&   ke.getKeyCode() <= 255   &&   allKeys[ke.getKeyCode()] != true)
		{
			allKeys[ke.getKeyCode()] = true;
		}
	}
	
	//keyReleased()
	public void keyReleased(KeyEvent ke)
	{
		//Setting the key boolean to 'False'
		if (ke.getKeyCode() >= 0   &&   ke.getKeyCode() <= 255   &&   allKeys[ke.getKeyCode()] != false)
		{
			allKeys[ke.getKeyCode()] = false;
		}
	}

	//keyTyped
	public void keyTyped(KeyEvent ke)
	{
		
	}
	
	//mouseDragged
	public void mouseDragged(MouseEvent me)
	{
		//Getting the new mouse cursor positions
		shortMouseX = (short) (me.getX() - (game.CANVAS_TOLERANCE/2) );
		shortMouseY = (short) (me.getY() - (game.CANVAS_TOLERANCE/2) );
	}

	//mouseMoved
	public void mouseMoved(MouseEvent me)
	{
		//Getting the new mouse cursor positions
		shortMouseX = (short) (me.getX() - (game.CANVAS_TOLERANCE/2) );
		shortMouseY = (short) (me.getY() - (game.CANVAS_TOLERANCE/2) );
	}
	
	//GetMousePosX
	public int GetMousePosX()
	{
		//Returning the X-axis mouse position
		return (int) (shortMouseX - (game.CANVAS_TOLERANCE/2));
	}
	
	//GetMousePosY
	public int GetMousePosY()
	{
		//Returning the X-axis mouse position
		return (int) (shortMouseY - (game.CANVAS_TOLERANCE/2));
	}
	
	//mousePressed
	public void mousePressed(MouseEvent me)
	{
		//Changing the appropriate mouse action to true
		allMouseActions[(me.getButton() - 1)] = true;
	}
	
	//mouseReleased
	public void mouseReleased(MouseEvent me)
	{
		//Changing the appropriate mouse action to false
		allMouseActions[(me.getButton() - 1)] = false;
	}
	
	//mouseClicked
	public void mouseClicked(MouseEvent me)
	{
		
	}
	
	//mouseEntered
	public void mouseEntered(MouseEvent me)
	{
		
	}
	
	//mouseExited
	public void mouseExited(MouseEvent me)
	{
		
	}
	
}//End class InputManager










