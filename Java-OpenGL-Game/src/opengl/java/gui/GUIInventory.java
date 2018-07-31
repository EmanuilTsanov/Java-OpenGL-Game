package opengl.java.gui;

import org.lwjgl.opengl.Display;

import opengl.java.shader.GUIShader;

public class GUIInventory
{
	private GUIWindow invWindow = new GUIWindow(0, 0, Display.getWidth() / 3, Display.getHeight());
	private GUIItemList list = new GUIItemList((int) invWindow.getX() + invWindow.getWidth() / 10,
			(int) invWindow.getY() + invWindow.getHeight() / 10, (int) (invWindow.getWidth() * 0.9f),
			(int) (invWindow.getHeight() * 0.9f), 4);

	public GUIInventory() {
		invWindow.addChild(list);
		list.addChild("").addChild("");
	}

	public void update()
	{
		invWindow.update();
	}

	public void render(GUIShader shader)
	{
		invWindow.render(shader);
	}
}
