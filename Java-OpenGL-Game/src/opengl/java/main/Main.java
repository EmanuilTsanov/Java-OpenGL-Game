package opengl.java.main;

import opengl.java.audio.AudioManager;
import opengl.java.render.MainRenderer;
import opengl.java.window.Window;

public class Main
{
	private static MainRenderer renderer;
	public static void main(String args[])
	{
		Window.create("OpenGL Game");
		AudioManager.initialize();
		renderer = new MainRenderer();
		while (Window.isOpened())
		{
			Window.update();
			renderer.update();
			renderer.render();
		}
		renderer.destroy();
		AudioManager.destroy();
		Window.destroy();
	}
}