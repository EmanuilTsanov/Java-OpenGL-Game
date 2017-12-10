package opengl.java.main;

import opengl.java.render.MainRenderer;
import opengl.java.window.Window;

public class Main
{

	public static void main(String args[])
	{
		Window.create("OpenGL Game");
		Window.setFPScap(120);
		MainRenderer renderer = new MainRenderer();
		while (Window.isOpened())
		{
			Window.update();
			renderer.render();
		}
		Window.destroy();
	}
}
