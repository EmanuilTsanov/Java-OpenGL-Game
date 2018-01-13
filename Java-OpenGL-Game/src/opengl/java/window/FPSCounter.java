package opengl.java.window;

import org.lwjgl.Sys;

public class FPSCounter
{
	private long lastFrame;
	private int fps;
	private long lastFPS;

	public FPSCounter()
	{
		getDelta();
		lastFPS = getTime();
	}

	public int getDelta()
	{
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	public long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public void update()
	{
		if (getTime() - lastFPS > 1000)
		{
			String cap = Window.getInstance().getFPScap() < 0 ? "UNLIMITED" : Window.getInstance().getFPScap() + "";
			System.out.println("FPS: " + fps + "; FPS limit: " + cap);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
}
