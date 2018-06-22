package opengl.java.window;

import java.awt.Canvas;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;

public class Window
{
	private static int fpsCap = 120;

	private static int width = 1920;
	private static int height = 1080;
	private static JFrame frame = new JFrame();
	private static Canvas canvas = new Canvas();

	private static ContextAttribs attribs = new ContextAttribs(3, 3).withForwardCompatible(true).withProfileCore(true);

	public static void create(String title)
	{
		frame.add(canvas);
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		try
		{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setParent(canvas);
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
		WindowFrameController.update();
		FPSCounter.update();
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
