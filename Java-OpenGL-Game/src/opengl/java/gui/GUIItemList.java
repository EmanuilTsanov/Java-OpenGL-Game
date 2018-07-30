package opengl.java.gui;

import java.util.ArrayList;
import java.util.HashMap;

import opengl.java.shader.GUIShader;

public class GUIItemList extends GUIComponent
{
	private int gridWidth, gridHeight;
	private int itemWinWidth;

	private static final int gridSpace = 10;

	private HashMap<Integer, ArrayList<GUIItemWindow>> itemPages;

	private int currentPage = 0;

	public GUIItemList(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.itemWinWidth = (width - ((gridWidth + 1) * gridSpace)) / gridWidth;
		itemPages = new HashMap<Integer, ArrayList<GUIItemWindow>>();
	}

	public GUIItemList setLayoutSize(int width, int height)
	{
		this.gridWidth = width;
		return this;
	}

	public GUIItemList addChild(GUIComponent child)
	{
		int x1 = (itemPages.get(currentPage).size() % gridWidth);
		int x2 = x1 * itemWinWidth + (x1 + 1) * gridSpace;
		int y1 = (int) (itemPages.get(currentPage).size() / gridWidth);
		int y2 = y1 * itemWinWidth + (y1 + 1) * gridSpace;
		child.setPosition(x2, y2);
		child.setSize(itemWinWidth, itemWinWidth);
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
