package opengl.java.window;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Window
{
	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;
	private static int fpsCap;

	private static WindowFrameController wfc = new WindowFrameController();
	private static FPSCounter fpsc = new FPSCounter();

	public static void create(String title)
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle(title);
			Display.create();
		}
		catch (LWJGLException e)
		{
			System.out.println("An error ocurred while creating display.");
			e.printStackTrace();
		}
	}

	public static void update()
	{
		Display.sync(fpsCap);
		Display.update();
		wfc.update();
		fpsc.update();
	}

	public static void destroy()
	{
		Display.destroy();
	}

	public static void setFPScap(int fpsCap)
	{
		Window.fpsCap = fpsCap;
	}

	public static boolean isOpened()
	{
		return !Display.isCloseRequested();
	}

	public static int getFPScap()
	{
		return fpsCap;
	}
}
