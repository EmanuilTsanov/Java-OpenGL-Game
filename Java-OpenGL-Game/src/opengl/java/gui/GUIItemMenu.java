package opengl.java.gui;

import java.util.ArrayList;

import opengl.java.shader.GUIShader;

public class GUIItemMenu extends GUIComponent
{
	// private int buttonsInRow;

	// private int margin;
	// private int buttonWidth;

	private ArrayList<GUIButton> buttons = new ArrayList<GUIButton>();

	public GUIItemMenu(int x, int y, int width, int height, int row)
	{
		super(x, y, width, height);
//		this.buttonsInRow = row;
		// margin = width / (5 * buttonsInRow);
		// buttonWidth = (width - ((buttonsInRow + 1) * margin)) / buttonsInRow;
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
		for (GUIButton button : buttons)
		{
			button.update();
		}
	}
}