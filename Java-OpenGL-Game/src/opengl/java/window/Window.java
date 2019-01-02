package opengl.java.window;

import java.awt.Canvas;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import opengl.java.logger.Logger;

public class Window
{
	private static int fpsCap =1000;

	private static int width = 1920;
	private static int height = 1080;
	private static JFrame frame = new JFrame();
	private static Canvas canvas = new Canvas();

	public static void create(String title)
	{
		frame.add(canvas);
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		try
		{
			Logger.log("1");
			Display.setDisplayMode(new DisplayMode(width, height));
			Logger.log("1");
			Display.setParent(canvas);
			Display.create();
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
