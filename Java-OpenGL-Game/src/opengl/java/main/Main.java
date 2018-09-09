package opengl.java.main;

import opengl.java.interaction.MouseLogic;
import opengl.java.render.MainRenderer;
import opengl.java.window.Window;

public class Main
{
	public static void main(String args[])
	{
		Window.create("OpenGL Game");
		while (Window.isOpened())
		{
			Window.update();
			MouseLogic.getInstance().update();
			MainRenderer.render();
		}
		Window.destroy();
	}
}
