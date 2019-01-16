package opengl.java.gui;

import org.lwjgl.opengl.GL11;

import opengl.java.shader.GUIShader;

public class Inventory
{
	private GUIShader shader;

	private static GUIButton button;

	public Inventory()
	{
		shader = new GUIShader();
		button = new GUIButton();
		button.setPosition(10, 10);
		button.setSize(100, 100);
		button.addAction(new Action()
		{
			@Override
			public void onClick()
			{
			}
		});
	}

	public static void mouseClick()
	{
		button.mouseClick();
	}

	public void update()
	{
		button.update();
	}

	public void render()
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		shader.start();
		button.render(shader);
		shader.stop();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
}
