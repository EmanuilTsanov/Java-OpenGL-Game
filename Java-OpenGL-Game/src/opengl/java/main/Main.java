package opengl.java.main;

import opengl.java.management.GameManager;
import opengl.java.window.Window;

public class Main
{
	private static GameManager manager = new GameManager();

	public static void main(String args[])
	{
		Window.create("OpenGL Game");
		Window.setFPScap(120);
		while (Window.isOpened())
		{
			Window.update();
			manager.update();
			manager.render();
		}
		Window.destroy();
	}
}
