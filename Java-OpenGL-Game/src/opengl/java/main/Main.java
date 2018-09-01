package opengl.java.main;

import opengl.java.interaction.MouseLogic;
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
			MouseLogic.getInstance().update();
			GameRenderer.render();
		}
		Window.destroy();
	}
}
