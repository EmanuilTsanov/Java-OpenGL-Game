package opengl.java.gui;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import opengl.java.shader.GUIShader;

public class Inventory
{
	private GUIShader shader;
	private GUIWindow window;
	private GUIItemMenu itemMenu;

	public Inventory()
	{
		shader = new GUIShader();
		window = new GUIWindow(0, 0, Display.getWidth() / 3, Display.getHeight());
		window.setColor(45, 137, 239);
		itemMenu = new GUIItemMenu(window.getX(),window.getY() + 30, window.getWidth(), window.getHeight()-60, 3);
		window.addComponent(itemMenu);
	}
	
	public void update() {
		window.update();
		
	}

	public void render()
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		shader.start();
		window.render(shader);
		shader.stop();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
}
