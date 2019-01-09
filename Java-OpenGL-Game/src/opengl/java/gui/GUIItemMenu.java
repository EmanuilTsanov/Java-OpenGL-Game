package opengl.java.gui;

import java.util.ArrayList;

import opengl.java.shader.GUIShader;

public class GUIItemMenu extends GUIComponent
{
	private static final int BUTTONS_IN_ROW = 3;
	private static final int BUTTONS_IN_COLUMN = 5;

	private ArrayList<GUIButton> buttons = new ArrayList<GUIButton>();

	public GUIItemMenu(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		int marginX = width / (5 * BUTTONS_IN_ROW);
		int buttonWidth = (width - ((BUTTONS_IN_ROW + 1) * marginX)) / BUTTONS_IN_ROW;
		for (int i = 0; i < BUTTONS_IN_COLUMN; i++)
		{
			for (int j = 0; j < BUTTONS_IN_ROW; j++)
			{
				GUIButton button = new GUIButton(x + (j + 1) * marginX + j * buttonWidth, y + (i + 1) * marginX + i * buttonWidth, buttonWidth, buttonWidth);
				button.setColor(45, 65, 120);
				addButton(button);
			}
		}
	}

	public void addButton(GUIButton button)
	{
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
		for(GUIButton button : buttons) {
			button.update();
		}
	}
}