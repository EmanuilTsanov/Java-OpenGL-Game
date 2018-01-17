package opengl.java.window;

import org.lwjgl.Sys;

import opengl.java.fonts.FontReader;
import opengl.java.fonts.FontType;
import opengl.java.fonts.GUIText;

public class FPSCounter
{
	private long lastFrame;
	private int fps;
	private long lastFPS;
	
	private int stackFPS;

	private FontReader reader = new FontReader();
	private FontType t = new FontType(reader, "font");
	private GUIText g = new GUIText(10,10,"FPS: " + stackFPS, t, 0.25f, 1920);

	private static FPSCounter singleton = new FPSCounter();

	public FPSCounter()
	{
		getDelta();
		lastFPS = getTime();
	}

	public GUIText getMesh() {
		return g;
	}
	
	public static FPSCounter getInstance()
	{
		return singleton;
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
			stackFPS = fps;
			fps = 0;
			lastFPS += 1000;
			g.update("FPS: " + stackFPS);
		}
		fps++;
	}

	public int getFPS()
	{
		return stackFPS;
	}
}
