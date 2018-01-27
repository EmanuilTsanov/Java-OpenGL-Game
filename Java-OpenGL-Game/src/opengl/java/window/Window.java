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
	private static int fpsCap;

	private static int width = 1280;
	private static int height = 720;

	private static ContextAttribs attribs = new ContextAttribs(3, 3).withForwardCompatible(true).withProfileCore(true);

	private static WindowFrameController wfc = new WindowFrameController();

	public static void create(String title)
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create(new PixelFormat().withSamples(8), attribs);
			GL11.glEnable(GL13.GL_MULTISAMPLE);
		}
		catch (LWJGLException e)
		{
			System.out.println("An error ocurred while creating the display.");
			e.printStackTrace();
		}
	}

	public static void update()
	{
		Display.sync(fpsCap);
		Display.update();
		wfc.update();
		FPSCounter.getInstance().update();
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
