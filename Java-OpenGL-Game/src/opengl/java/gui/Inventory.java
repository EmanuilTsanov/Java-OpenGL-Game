package opengl.java.gui;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import opengl.java.shader.GUIShader;

public class Inventory
{
	private GUIShader shader;

	private static GUIWindow window;
	private static GUIButtonGrid grid;
	private static GUIButton button;
	public static GUIMenuBar bar;

	public Inventory()
	{
		shader = new GUIShader();
		window = new GUIWindow();
		grid = new GUIButtonGrid(3, 4);
		bar = new GUIMenuBar(8);
		button = new GUIButton();
		window.setPosition(0, 0);
		window.setSize(Display.getWidth() / 3, Display.getHeight());
		grid.setPosition(0, 60);
		grid.setSize(window.getWidth(), window.getHeight() - 120);
		window.setBackgroundColor(239, 46, 137);
		window.addComponent(grid);
		window.addComponent(bar);
		bar.setSize(window.getWidth(), 60);
		bar.setBackgroundColor(76, 88, 205);
		grid.addButton(button);
		for (int i = 0; i < 50; i++)
		{
			grid.addButton(new GUIButton());
		}
		for (int i = 0; i < 13; i++)
		{
			bar.addButton(new GUIButton());
		}
		button.addAction(new Action()
		{
			@Override
			public void onClick()
			{
				System.out.println(1);
			}
		});
	}

	public static void mouseClick()
	{
		window.mouseClick();
	}

	public void update()
	{
		window.update();
	}

	public void render()
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		shader.start();
		window.render(shader);
		shader.stop();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
}
