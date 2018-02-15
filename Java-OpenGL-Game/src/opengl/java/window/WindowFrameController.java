package opengl.java.window;

import org.lwjgl.Sys;

public class WindowFrameController
{
	private long lastFrameTime;
	private float delta;

	private static WindowFrameController singleton = new WindowFrameController();

	public WindowFrameController()
	{
		lastFrameTime = getCurrentTime();
	}

	public void update()
	{
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime;
	}

	private long getCurrentTime()
	{
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

	public float getFrameTimeSeconds()
	{
		return delta;
	}

	public static WindowFrameController getInstance()
	{
		return singleton;
	}
}
