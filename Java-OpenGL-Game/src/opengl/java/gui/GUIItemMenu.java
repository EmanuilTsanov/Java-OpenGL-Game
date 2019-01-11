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

	private float margin;
	private float buttonWidth;

	private HashMap<Integer, HashMap<Integer, GUIButton>> buttons = new HashMap<Integer, HashMap<Integer, GUIButton>>();

	public GUIItemMenu(float x, float y, float width, float height, GUIComponent parent, int rowLength)
	{
		super(x, y, width, height, parent);
		this.rowLength = rowLength;
		calculateDimensions();
	}

	public void calculateDimensions()
	{
		margin = width / (5 * this.rowLength);
		buttonWidth = (width - ((this.rowLength + 1) * margin)) / this.rowLength;
		colLength = (int)(height / (buttonWidth + margin));
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

	public float getButtonWidth()
	{
		return buttonWidth;
	}

	public float getMargin()
	{
		return margin;
	}

	public float getCellX(int x)
	{
		if (x >= rowLength)
		{
			System.out.println("Invalid cell position.");
			return 0;
		}
		return this.x + x * buttonWidth + (x + 1) * margin;
	}

	public float getCellY(int y)
	{
		if (y >= colLength)
		{
			System.out.println("Invalid cell position.");
			return 0;
		}
		return this.y + y * buttonWidth + (y + 1) * margin;
	}

	@Override
	public void moveByX(float distance)
	{
		super.moveByX(distance);
		for (Map.Entry<Integer, GUIButton> button : buttons.get(currentPage).entrySet())
		{
			button.getValue().moveByX(distance);
		}
	}

	@Override
	public void moveByY(float distance)
	{
		super.moveByY(distance);
		for (Map.Entry<Integer, GUIButton> button : buttons.get(currentPage).entrySet())
		{
			button.getValue().moveByY(distance);
		}
	}

	public void changePage(int page)
	{
		this.currentPage = page;
	}

	@Override
	public void update()
	{
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