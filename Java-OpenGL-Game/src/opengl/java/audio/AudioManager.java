package opengl.java.audio;

public class AudioManager extends Thread
{
	public static int buffer;

	public static AudioSource src;

	@Override
	public void run()
	{
		AudioMaster.initialize();
		AudioMaster.setListenerData(0, 0, 0);
		buffer = AudioMaster.loadSound("assets/audio/bounce.wav");
		src = new AudioSource();
		src.setLooping(true);
		float x = 10;
		float speed = 0.000001f;
		src.setPosition(x, 0, 2);
		src.play(buffer);
		boolean b = true;
		while (b)
		{
			if (x >= 10)
				speed = 0.000001f;
			else if (x <= -10)
				speed = -0.000001f;
			x -= speed;
			src.setPosition(x, 0, 2);
		}
		src.delete();
		AudioMaster.clean();
	}
}
