package opengl.java.gui;

import java.util.ArrayList;
import java.util.HashMap;

import opengl.java.shader.GUIShader;

public class GUIButtonGrid extends GUIComponent
{
	private int gridWidth;
	private int gridHeight;
	private float buttonSize;
	private float xSpacing, ySpacing;

	private int lastPage;

	private int currentPage;

	private HashMap<Integer, ArrayList<GUIButton>> buttons = new HashMap<Integer, ArrayList<GUIButton>>();

	public GUIButtonGrid(int gridWidth, int gridHeight)
	{
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		buttons.put(lastPage, new ArrayList<GUIButton>());
	}

	public void addButton(GUIButton button)
	{
		if (buttons.get(lastPage).size() == gridWidth * gridHeight)
		{
			lastPage++;
			buttons.put(lastPage, new ArrayList<GUIButton>());
		}
		int gridX = buttons.get(lastPage).size() % gridWidth;
		int gridY = (int) (buttons.get(lastPage).size() / gridWidth);
		button.setPosition((int) (x + (gridX * buttonSize) + ((gridX + 1) * xSpacing)), (int) (y + (gridY * buttonSize) + ((gridY + 1) * ySpacing)));
		button.setSize((int) buttonSize, (int) buttonSize);
		buttons.get(lastPage).add(button);
	}

	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		float spacing1 = (50 / gridWidth) + 1;
		float spacing2 = (50 / gridHeight) + 1;
		float btn1 = (this.width - ((gridWidth + 1) * spacing1)) / gridWidth;
		float btn2 = (this.height - ((gridHeight + 1) * spacing2)) / gridHeight;
		if (btn1 < btn2)
		{
			xSpacing = spacing1;
			buttonSize = btn1;
			ySpacing = (height - (gridHeight * buttonSize)) / (gridHeight + 1);
		}
		else
		{
			ySpacing = spacing2;
			buttonSize = btn2;
			xSpacing = (width - (gridWidth * buttonSize)) / (gridWidth + 1);
		}
	}

	@Override
	public void mouseClick()
	{
		for (GUIButton button : buttons.get(currentPage))
		{
			button.mouseClick();
		}
	}

	@Override
	public void update()
	{
		for (GUIButton button : buttons.get(currentPage))
		{
			button.update();
		}
	}

	@Override
	public void render(GUIShader shader)
	{
		for (GUIButton button : buttons.get(currentPage))
		{
			button.render(shader);
		}
	}

}
