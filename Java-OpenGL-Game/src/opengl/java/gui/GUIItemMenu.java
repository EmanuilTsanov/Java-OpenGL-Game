package opengl.java.gui;

import java.util.ArrayList;

import opengl.java.shader.GUIShader;

public class GUIItemMenu extends GUIComponent
{
	private int margin;
	private int buttonWidth;
	
	private int rowSize;

	private ArrayList<GUIButton> buttons = new ArrayList<GUIButton>();

	public GUIItemMenu(int row)
	{
		rowSize = row;
	}

	public void addButton(GUIButton button, int gridX, int gridY)
	{
		button.setPosition(x + gridX * buttonWidth + (gridX + 1) * margin, y + gridY * buttonWidth + (gridY + 1) * margin);
		button.setSize(buttonWidth, buttonWidth);
		if (button.getWidth() > width || button.getHeight() > height || button.getX() + button.getWidth() > x + width || button.getY() + button.getHeight() > y + height)
		{
			System.out.println("There was a problem adding a component.");
		}
		else
		{
			buttons.add(button);
		}
	}
	
	@Override
	public void setPosition(int width, int height) {
		super.setPosition(width, height);
		margin = width / (5 * rowSize);
		buttonWidth = (width - ((rowSize + 1) * margin)) / rowSize;
	}

	@Override
	public void render(GUIShader shader)
	{
		for (GUIButton button : buttons)
		{
			button.render(shader);
		}
	}

	@Override
	public void update()
	{
		for (GUIButton button : buttons)
		{
			button.update();
		}
	}
}