package opengl.java.management;

import opengl.java.interaction.MouseController;
import opengl.java.interaction.MousePicker;
import opengl.java.render.GameRenderer;

public class GameManager
{
	public void update()
	{
		GameRenderer.getInstance().render();
		MousePicker.getInstance().update();
		MouseController.getInstance().update();
	}
}
