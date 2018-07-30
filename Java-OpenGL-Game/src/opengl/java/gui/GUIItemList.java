package opengl.java.gui;

import java.util.ArrayList;
import java.util.HashMap;

import opengl.java.shader.GUIShader;

public class GUIItemList extends GUIComponent
{
	private int gridWidth;
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

	public GUIItemList addChild(String image)
	{
		int x1 = (itemPages.get(currentPage).size() % gridWidth);
		int x2 = x1 * itemWinWidth + (x1 + 1) * gridSpace;
		int y1 = (int) (itemPages.get(currentPage).size() / gridWidth);
		int y2 = y1 * itemWinWidth + (y1 + 1) * gridSpace;
		if (y2 + itemWinWidth < y + height)
		{
			GUIItemWindow item = new GUIItemWindow(x2, y2, itemWinWidth, itemWinWidth).setImage(image);
			if (itemPages.get(currentPage) == null)
				itemPages.put(currentPage, new ArrayList<GUIItemWindow>());
			itemPages.get(currentPage).add(item);
		}
		else
			currentPage++;
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
