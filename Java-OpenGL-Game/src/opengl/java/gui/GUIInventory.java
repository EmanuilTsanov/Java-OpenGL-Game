package opengl.java.gui;

import org.lwjgl.opengl.Display;

import opengl.java.shader.GUIShader;

public class GUIInventory
{
	private GUIWindow invWindow = new GUIWindow(0, 0, Display.getWidth() / 3, Display.getHeight());

	public void update()
	{
		invWindow.update();
	}

	public void render(GUIShader shader)
	{
		invWindow.render(shader);
	}
}
