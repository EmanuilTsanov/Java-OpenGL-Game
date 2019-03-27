package opengl.java.window;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;

public class Window
{
	private static int fpsCap = 1000;

	private static int width = 1366;
	private static int height = 768;

	public static void create(String title)
	{
		ContextAttribs attribs = new ContextAttribs(3, 3).withForwardCompatible(true).withProfileCore(true);
		try
		{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle(title);
			GL11.glEnable(GL13.GL_MULTISAMPLE);
		}
		catch (LWJGLException e)
		{
			System.out.println("An error occured while initializing the display.");
		}
	}

	public static void update()
	{
		Display.sync(fpsCap);
		Display.update();
		FPSCounter.update();
		WindowManager.update();
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
