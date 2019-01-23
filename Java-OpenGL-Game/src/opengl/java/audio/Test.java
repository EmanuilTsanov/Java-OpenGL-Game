package opengl.java.audio;

import java.io.IOException;

public class Test
{
	public static int buffer;

	public static AudioSource src;

	public static void main(String args[])
	{
		AudioMaster.initialize();
		AudioMaster.setListenerData();
		buffer = AudioMaster.loadSound("assets/audio/bounce.wav");
		src = new AudioSource();

		while (true)
		{
			int c = 0;
			try
			{
				c = System.in.read();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(c=='q') {
				src.play(buffer);
			}
			else if(c=='e') {
				break;
			}
		}
		src.delete();
		AudioMaster.clean();
	}
}
