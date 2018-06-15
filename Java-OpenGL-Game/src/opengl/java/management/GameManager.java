package opengl.java.management;

import opengl.java.interaction.MouseController;
import opengl.java.interaction.MousePicker;
import opengl.java.render.GameRenderer;

public class GameManager
{
	public void update()
	{
		MouseController.getInstance().update();
		MousePicker.getInstance().update();
	}

	public void render()
	{
		GameRenderer.getInstance().render();
	}
}
