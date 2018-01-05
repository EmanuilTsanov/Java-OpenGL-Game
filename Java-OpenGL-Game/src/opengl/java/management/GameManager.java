package opengl.java.management;

import opengl.java.controls.MouseController;
import opengl.java.controls.MousePicker;
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
