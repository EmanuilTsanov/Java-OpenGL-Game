package opengl.java.main;

import opengl.java.interaction.MouseController;
import opengl.java.interaction.MousePicker;
import opengl.java.render.GameRenderer;
import opengl.java.window.Window;

public class Main
{
	public static void main(String args[])
	{
		Window.create("OpenGL Game");
		while (Window.isOpened())
		{
			Window.update();
			update();
			render();
		}
		Window.destroy();
	}

	private static void update()
	{
		MouseController.getInstance().update();
		MousePicker.getInstance().update();
	}

	private static void render()
	{
		GameRenderer.getInstance().render();
	}
}
