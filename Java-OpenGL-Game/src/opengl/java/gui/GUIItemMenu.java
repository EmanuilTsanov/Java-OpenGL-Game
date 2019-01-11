package opengl.java.gui;

import java.util.HashMap;
import java.util.Map;

import opengl.java.shader.GUIShader;

public class GUIItemMenu extends GUIComponent
{
	private int rowLength;
	private int colLength;

	private int page;
	private int currentPage;

	private int margin;
	private int buttonWidth;

	private HashMap<Integer, HashMap<Integer, GUIButton>> buttons = new HashMap<Integer, HashMap<Integer, GUIButton>>();

	public GUIItemMenu(int x, int y, int width, int height, GUIComponent parent, int rowLength)
	{
		super(x, y, width, height, parent);
		this.rowLength = rowLength;
		calculateDimensions();
	}

	public void calculateDimensions()
	{
		margin = width / (5 * this.rowLength);
		buttonWidth = (width - ((this.rowLength + 1) * margin)) / this.rowLength;
		colLength = height / (buttonWidth + margin);
	}

	public GUIButton addButton()
	{
		if (buttons.get(page) == null)
		{
			HashMap<Integer, GUIButton> inner = new HashMap<Integer, GUIButton>();
			buttons.put(page, inner);
		}
		if (buttons.get(page).size() < rowLength * colLength)
		{
			int x = (buttons.get(page).size() - ((buttons.get(page).size() / rowLength) * rowLength));
			int y = buttons.get(page).size() / rowLength;
			GUIButton button = new GUIButton(getCellX(x), getCellY(y), buttonWidth, buttonWidth, this);
			buttons.get(page).put(buttons.get(page).size(), button);
			return button;
		}
		else
		{
			page++;
			return addButton();
		}
	}

	public int getButtonWidth()
	{
		return buttonWidth;
	}

	public int getMargin()
	{
		return margin;
	}

	public int getCellX(int x)
	{
		if (x >= rowLength)
		{
			System.out.println("Invalid cell position.");
			return 0;
		}
		return this.x + x * buttonWidth + (x + 1) * margin;
	}

	public int getCellY(int y)
	{
		if (y >= colLength)
		{
			System.out.println("Invalid cell position.");
			return 0;
		}
		return this.y + y * buttonWidth + (y + 1) * margin;
	}

	public void changePage(int page)
	{
		this.currentPage = page;
	}

	@Override
	public void update()
	{
		if (parent != null)
		{
			renderX = parent.getRenderX() + x;
			renderY = parent.getRenderY() + y;
		}
		else
		{
			renderX = x;
			renderY = y;
		}
		for (Map.Entry<Integer, GUIButton> button : buttons.get(currentPage).entrySet())
		{
			button.getValue().update();
		}
	}

	@Override
	public void render(GUIShader shader)
	{
		for (Map.Entry<Integer, GUIButton> button : buttons.get(currentPage).entrySet())
		{
			button.getValue().render(shader);
		}
	}
}