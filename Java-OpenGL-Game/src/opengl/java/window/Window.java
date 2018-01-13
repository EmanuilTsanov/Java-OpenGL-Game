package opengl.java.window;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

public class Window
{
	public int width = 1920;
	public int height = 1080;
	private int fpsCap;
	private boolean fullscreen;

	private WindowFrameController wfc = new WindowFrameController();
	private FPSCounter fpsc = new FPSCounter();

	private static Window singleton = new Window();

	public void create(String title)
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
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

	public void setFullscreen(boolean b)
	{
		fullscreen = b;
		try
		{
			if(b) {
				Display.setDisplayMode(new DisplayMode(1920,1080));
			}
			else 
				Display.setDisplayModeAndFullscreen(new DisplayMode(width, height));
		}
		catch (LWJGLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isFullscreen()
	{
		return fullscreen;
	}

	public void setFPScap(int fpsCap)
	{
		this.fpsCap = fpsCap;
	}

	public boolean isOpened()
	{
		return !Display.isCloseRequested();
	}

	public int getFPScap()
	{
		return fpsCap;
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
