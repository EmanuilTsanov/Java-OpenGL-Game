package opengl.java.main;

import opengl.java.management.GameManager;
import opengl.java.window.Window;

public class Main
{

	public static void main(String args[])
	{
		Window.create("OpenGL Game");
		Window.setFPScap(120);
		GameManager gm = new GameManager();
		while (Window.isOpened())
		{
			Window.update();
			gm.update();
		}
		Window.destroy();
	}
}
