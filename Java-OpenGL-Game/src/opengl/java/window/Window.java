package opengl.java.window;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Window
{
	private int fpsCap;

	private int width = 1600;
	private int height = 900;

	private WindowFrameController wfc = new WindowFrameController();
	private static Window singleton = new Window();

	public void create(String title)
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create();
		}
		catch (LWJGLException e)
		{
			System.out.println("An error ocurred while creating the display.");
			e.printStackTrace();
		}
	}

	public void update()
	{
		Display.sync(fpsCap);
		Display.update();
		wfc.update();
		FPSCounter.getInstance().update();
	}

	public void destroy()
	{
		Display.destroy();
	}

	public int getFPScap()
	{
		return fpsCap;
	}

	public void setFPScap(int cap)
	{
		this.fpsCap = cap;
	}

	public boolean isOpened()
	{
		return !Display.isCloseRequested();
	}

	public static Window getInstance()
	{
		return singleton;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}
