package opengl.java.gui;

import java.util.ArrayList;

import opengl.java.shader.GUIShader;

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
		this.childWidth = (width - ((gridWidth + 1) * gridSpace)) / gridWidth;
		this.childHeight = (height - ((gridHeight + 1) * gridSpace)) / gridHeight;
		children = new ArrayList<GUIComponent>();
	}

	public GUIWindow addChild(GUIComponent child)
	{
		int x1 = (children.size() % gridWidth);
		int x2 = x1 * childWidth + (x1 + 1) * gridSpace;
		int y1 = (int) (children.size() / gridWidth);
		int y2 = y1 * childHeight + (y1 + 1) * gridSpace;
		child.setPosition(x2, y2);
		child.setSize(childWidth, childHeight);
		child.create();
		children.add(child);
		System.out.println(childWidth+" / " + childHeight);
		System.out.println(x2+" / " + y2);
		return this;
	}

	public void update()
	{

	}

	public void render(GUIShader shader)
	{
		for (GUIComponent component : children)
		{
			component.render(shader);
		}
	}
}
