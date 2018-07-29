package opengl.java.gui;

import java.util.ArrayList;
import java.util.HashMap;

import opengl.java.shader.GUIShader;

public class GUIItemList extends GUIComponent
{
	private int gridWidth;

	private static final int gridSpace = 10;

	private HashMap<Integer, ArrayList<GUIComponent>> itemPages;

	public GUIItemList(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.childWidth = (width - ((gridWidth + 1) * gridSpace)) / gridWidth;
		children = new ArrayList<GUIComponent>();
	}

	public GUIItemList setLayoutSize(int width, int height)
	{
		this.gridWidth = width;
		this.gridHeight = height;
		return this;
	}

	public GUIWindow addChild(GUIComponent child)
	{
		int x1 = (children.size() % gridWidth);
		int x2 = x1 * childWidth + (x1 + 1) * gridSpace;
		int y1 = (int) (children.size() / gridWidth);
		int y2 = y1 * childWidth + (y1 + 1) * gridSpace;
		child.setPosition(x2, y2);
		child.setSize(childWidth, childWidth);
		child.create();
		children.add(child);
		return this;
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
