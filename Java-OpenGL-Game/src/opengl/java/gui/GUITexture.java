package opengl.java.gui;

import opengl.java.management.FileManager;

public class GUITexture extends GUIComponent
{

	public GUITexture(int x, int y, int width, int height, String imageName)
	{
		super(x, y, width, height);
		this.image = FileManager.loadTexture(imageName);
	}

	@Override
	public void update()
	{
	}

	@Override
	public void render()
	{
	}

}
