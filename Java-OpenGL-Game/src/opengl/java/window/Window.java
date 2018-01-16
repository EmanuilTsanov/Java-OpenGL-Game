package opengl.java.window;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

public class Window
{
	public int width = 1280;
	public int height = 720;
	private int fpsCap;

	private WindowFrameController wfc = new WindowFrameController();
	private FPSCounter fpsc = new FPSCounter();

	private static Window singleton = new Window();

	public void create(String title)
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create(new PixelFormat(32, 0, 24, 0, 4));
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
		fpsc.update();
	}

	public void destroy()
	{
		Display.destroy();
	}

	public int getFPScap()
	{
		return fpsCap;
	}

	public void setFPScap(int fpsCap)
	{
		this.fpsCap = fpsCap;
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

	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
}
