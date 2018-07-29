package opengl.java.gui;

import opengl.java.shader.GUIShader;

public abstract class GUIComponent
{
	protected int x, y;
	protected int width, height;

	public GUIComponent(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public GUIComponent setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	public GUIComponent setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		return this;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public abstract void update();

	public abstract void render(GUIShader shader);
}
