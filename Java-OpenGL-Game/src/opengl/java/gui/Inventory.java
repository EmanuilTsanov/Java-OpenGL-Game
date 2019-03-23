package opengl.java.gui;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import opengl.java.shader.GUIShader;
import opengl.java.window.WindowManager;

public class Inventory
{
	private GUIShader shader;

	private static GUIWindow window;
	private static GUIButtonGrid grid;
	public static GUIMenuBar bar;

	public static boolean isOpened;

	public Inventory()
	{
		shader = new GUIShader();
		window = new GUIWindow();
		grid = new GUIButtonGrid(3, 4);
		grid.setParent(window);
		bar = new GUIMenuBar(8);
		bar.setParent(window);
		window.setSize(Display.getWidth() / 3, Display.getHeight());
		window.setPosition(-window.getWidth(), 0);
		grid.setPosition(0, 60);
		grid.setSize(window.getWidth(), window.getHeight() - 120);
		window.addComponent(grid);
		window.addComponent(bar);
		bar.setSize(window.getWidth(), 60);
		bar.setPosition(0, 0);
		for (int i = 0; i < 50; i++)
		{
			grid.addButton(new GUIButton());
		}
		for (int i = 0; i < 13; i++)
		{
			bar.addButton(new GUIButton());
		}
	}

	public static void toggle()
	{
		if (isOpened)
			isOpened = false;
		else
			isOpened = true;
	}

	public static void mouseClick()
	{
		if (isOpened || window.getX() > -window.getWidth())
			window.mouseClick();
	}

	public void update()
	{
		if (isOpened && window.getX() < 0)
		{
			window.move(3000f * WindowManager.getFrameTimeSeconds(), 0f);
			if (window.getX() > 0)
				window.x = 0;
		}
		else if (!isOpened && window.getX() > -window.getWidth())
		{
			window.move(-3000f * WindowManager.getFrameTimeSeconds(), 0f);
			if (window.getX() < -window.getWidth())
				window.x = -window.getWidth();
		}
		if (isOpened || window.getX() > -window.getWidth())
			window.update();
	}

	public void render()
	{
		if (isOpened || window.getX() > -window.getWidth())
		{
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			shader.start();
			window.render(shader);
			shader.stop();
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
	}
}
