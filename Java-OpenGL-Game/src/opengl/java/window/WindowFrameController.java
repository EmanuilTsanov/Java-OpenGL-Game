package opengl.java.window;

import org.lwjgl.Sys;

public class WindowFrameController
{
	private static long lastFrameTime;
	private static float delta;

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

	private static long getCurrentTime()
	{
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

	public static float getFrameTimeSeconds()
	{
		return delta;
	}
}
