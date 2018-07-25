package opengl.java.gui;

import java.util.ArrayList;

public class GUIWindow
{
	private int x, y;
	private int width, height;
	private int gridWidth, gridHeight;
	private int childWidth;
	private int childHeight;
	
	private static final int gridSpace = 10;

	private ArrayList<GUIComponent> children;

	public GUIWindow(int x, int y, int width, int height, int gridWidth, int gridHeight)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		this.childWidth = (width - ((gridWidth+1)*gridSpace))/gridWidth;
		this.childHeight = (height - ((gridHeight+1)*gridSpace))/gridHeight;
	}

	public void addChild(GUIComponent child)
	{
		child.setPosition(x, y);
		children.add(child);
	}

	public void update()
	{

	}

	public void render()
	{

	}
}
