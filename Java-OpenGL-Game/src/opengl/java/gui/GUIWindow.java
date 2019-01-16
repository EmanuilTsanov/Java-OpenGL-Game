package opengl.java.gui;

import java.util.ArrayList;

import opengl.java.shader.GUIShader;

public class GUIWindow extends GUIComponent
{
	private ArrayList<GUIComponent> components = new ArrayList<GUIComponent>();

	public void move(int x, int y)
	{
		this.x += x;
		this.y += y;
	}

	public void mouseClick()
	{

	}

	@Override
	public void update()
	{
	}

	@Override
	public void render(GUIShader shader)
	{
	}
}
