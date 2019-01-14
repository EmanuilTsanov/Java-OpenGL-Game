package opengl.java.gui;

import org.lwjgl.opengl.GL11;

import opengl.java.shader.GUIShader;

public class Inventory
{
	private GUIShader shader;

	public Inventory()
	{
	}

	public void setupItemMenu()
	{
	}

	public void update()
	{
	}

	public void render()
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		shader.start();
		shader.stop();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
}
