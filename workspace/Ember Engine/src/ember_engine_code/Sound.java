/* Name:					Sounds
 * Author:					Michael Berger
 * 
 * Description:		The class that holds and plays a
 * 		sound file.
 * */

package ember_engine_code;

//Imports
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Sound
{
	//Class-Wide Variables
	GameEngine game;
	private Clip clip = null;
	AudioInputStream audioIn;
	FloatControl audioVolume;
	
	//[Constructor]
	public Sound(GameEngine gm, String soundFile)
	{
		//Setting the 'game' instance
		game = gm;
		
		//Trying to load sound
		try
		{
			audioIn = AudioSystem.getAudioInputStream(Sound.class.getResource(soundFile));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}
		
		//Setting the volume of the sound
		audioVolume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		audioVolume.setValue(game.floatVolume);
	}
	
	//Play
	public void Play()
	{
		//Simple 'Is Clip Playing?' check
		if (clip.isRunning() != true)
		{
			//Resetting the sound if completed
			if (clip.getMicrosecondPosition() == clip.getMicrosecondLength())
			{
				clip.setFramePosition(0);
			}
			
			//Playing the sound once
			clip.start();
		}
	}
	
	//Loop
	public void Loop(int num)
	{
		//Simple 'Is Clip Playing' check
		if (clip.isRunning() != true)
		{
			//Looping the sound 'num' times. ('-1' is infinitely looping.)
			clip.loop(num);
		}
	}
	
	//Stop
	public void Stop()
	{
		//Simple 'Is Clip Playing' check
		if (clip.isRunning() == true)
		{
			//Stopping the sound (does not reset sound)
			clip.stop();
		}
	}
	
	//Rewind
	public void Rewind()
	{
		//Stopping the clip if it is playing
		if (clip.isRunning() == true)
		{
			clip.stop();
		}
		
		//Resetting the clip
		clip.setFramePosition(0);
	}
}










