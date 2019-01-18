package opengl.java.gui;

import java.util.ArrayList;

import opengl.java.shader.GUIShader;

public class GUIMenuBar extends GUIComponent
{
	public int rowWidth;
	public int buttonSize;
	public int border;

	private ArrayList<GUIButton> buttons = new ArrayList<GUIButton>();

	public GUIMenuBar(int rowWidth)
	{
		this.rowWidth = rowWidth;
	}

	public void addButton(GUIButton button)
	{
		
	}

	@Override
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