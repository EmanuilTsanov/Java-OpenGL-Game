package opengl.java.main;

import opengl.java.audio.AudioManager;
import opengl.java.render.MainRenderer;
import opengl.java.window.Window;

public class Main
{
	public static void main(String args[])
	{
		Window.create("OpenGL Game");
		MainRenderer.initialize();
		AudioManager.initialize();
		while (Window.isOpened())
		{
			Window.update();
			MainRenderer.update();
			MainRenderer.render();
		}
		AudioManager.destroy();
		Window.destroy();
	}
}