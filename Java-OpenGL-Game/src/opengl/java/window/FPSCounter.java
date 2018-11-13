package opengl.java.window;

import org.lwjgl.Sys;

import opengl.java.fonts.FontReader;
import opengl.java.fonts.FontType;
import opengl.java.fonts.GUIText;

public class FPSCounter
{
	private static int fps;
	private static int stackFPS=1;
	
	private static long lastFPS = getTime();

	private static FontReader reader = new FontReader();
	private static FontType t = new FontType(reader, "font");
	private static GUIText g = new GUIText(10, 10, "FPS: " + stackFPS, t, 0.1f, Window.getWidth());

	public static GUIText getMesh()
	{
		return g;
	}

	private static long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public static void update()
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

	public static int getFPS()
	{
		return stackFPS;
	}
}
