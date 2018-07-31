package opengl.java.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import opengl.java.shader.GUIShader;

public class GUIItemList extends GUIComponent
{
	private int gridWidth;
	private int itemWinWidth;

	private static final int gridSpace = 10;

	private HashMap<Integer, ArrayList<GUIItemWindow>> itemPages;

	private int currentPage = 0;

	public GUIItemList(int x, int y, int width, int height, int gridWidth)
	{
		super(x, y, width, height);
		this.gridWidth = gridWidth;
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
		if (itemPages.get(currentPage) == null)
			itemPages.put(currentPage, new ArrayList<GUIItemWindow>());
		ArrayList<GUIItemWindow> arr = itemPages.get(currentPage);
		int x1 = (arr.size() % gridWidth);
		int x2 = x1 * itemWinWidth + (x1 + 1) * gridSpace;
		int y1 = (int) (arr.size() / gridWidth);
		int y2 = y1 * itemWinWidth + (y1 + 1) * gridSpace;
		if (y2 + itemWinWidth < y + height)
		{
			GUIItemWindow item = new GUIItemWindow(x2, y2, itemWinWidth, itemWinWidth).setImage(image);
			itemPages.get(currentPage).add(item);
		}
		else
			currentPage++;
		return this;
	}

	@Override
	public void update()
	{
		for (Map.Entry<Integer, ArrayList<GUIItemWindow>> entry : itemPages.entrySet())
		{
			for (GUIItemWindow g : entry.getValue())
			{
				g.update();
			}
		}
	}

	@Override
	public void render(GUIShader shader)
	{
		for (Map.Entry<Integer, ArrayList<GUIItemWindow>> entry : itemPages.entrySet())
		{
			for (GUIItemWindow g : entry.getValue())
			{
				g.render(shader);
			}
		}
	}
}
