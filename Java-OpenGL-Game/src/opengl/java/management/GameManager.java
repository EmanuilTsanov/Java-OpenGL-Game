package opengl.java.management;

import opengl.java.controls.MouseController;
import opengl.java.render.GameRenderer;

public class GameManager
{
	public void update()
	{
		GameRenderer.getInstance().render();
		MouseController.getInstance().update();
	}
}
