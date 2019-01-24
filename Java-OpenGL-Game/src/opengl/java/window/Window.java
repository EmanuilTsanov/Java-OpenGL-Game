package opengl.java.window;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import opengl.java.logger.Logger;

public class Window
{
	private static int fpsCap = 1500;

	private static int width = 1366;
	private static int height = 768;

	public static void create(String title)
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.setFullscreen(true);
			Display.create();
			DisplayMode[] modes = Display.getAvailableDisplayModes();

			for (int i = 0; i < modes.length; i++)
			{
				DisplayMode current = modes[i];
				System.out.println(current.getWidth() + "x" + current.getHeight() + "x" + current.getBitsPerPixel() + " " + current.getFrequency() + "Hz");
			}
		}
		catch (LWJGLException e)
		{
			System.out.println("An error occured while initializing the display.");
			Logger.log("An error occured while initializing the display.");
		}
	}

	public static void update()
	{
		Display.sync(fpsCap);
		Display.update();
		FPSCounter.update();
		FrameController.update();
	}

	public static void destroy()
	{
		Display.destroy();
	}

	public static int getWidth()
	{
		return width;
	}

	public static int getHeight()
	{
		return height;
	}

	public static int getFPScap()
	{
		return fpsCap;
	}

	public static boolean isOpened()
	{
		return !Display.isCloseRequested();
	}

	public static void setFPScap(int cap)
	{
		fpsCap = cap;
	}
}
