package opengl.java.audio;

public class AudioManager
{
	public static int bounce, background;

	public static AudioSource src;

	public static void initialize()
	{
		AudioMaster.initialize();
		AudioMaster.setListenerData(0, 0, 0);
		loadBuffers();
		src = new AudioSource();
		src.setLooping(true);
		src.setPosition(0, 0, 0);
	}

	public static void play()
	{
		src.play(bounce);
	}

	public static void loadBuffers()
	{
		bounce = AudioMaster.loadSound("bounce");
		background = AudioMaster.loadSound("background");
	}

	public static void destroy()
	{
		src.delete();
		AudioMaster.clean();
	}
}
