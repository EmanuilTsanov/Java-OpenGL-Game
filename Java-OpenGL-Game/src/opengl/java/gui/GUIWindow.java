package opengl.java.gui;

import opengl.java.shader.GUIShader;

public class GUIWindow
{
	private int x, y;
	private int width, height;

	public GUIWindow(int x, int y, int width, int height, int gridWidth, int gridHeight)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void update()
	{

	}

	public void render(GUIShader shader)
	{
	}
}
