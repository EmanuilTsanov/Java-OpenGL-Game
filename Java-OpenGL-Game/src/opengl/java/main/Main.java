package opengl.java.main;

import opengl.java.management.GameManager;
import opengl.java.window.Window;

public class Main
{

	public static void main(String args[])
	{
		Window window = Window.getInstance();
		window.create("OpenGL Game");
		window.setFPScap(300);
		GameManager gm = new GameManager();
		while (window.isOpened())
		{
			window.update();
			gm.update();
		}
		window.destroy();
	}
}
