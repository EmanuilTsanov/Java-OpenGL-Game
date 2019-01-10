package opengl.java.gui;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import opengl.java.shader.GUIShader;

public class Inventory
{
	private GUIShader shader;
	private GUIWindow window;
	private GUIItemMenu menu;
	private GUIButton button,button1, button2, button3;

	public Inventory()
	{
		shader = new GUIShader();
		window = new GUIWindow(0,0, Display.getWidth()/3, Display.getHeight());
		window.setColor(45, 147, 239);
		setupItemMenu();
		window.addComponent(menu);
	}
	
	public void setupItemMenu() {
		menu = new GUIItemMenu(window.getX(), window.getY()+30, window.getWidth(), window.getHeight()-60, 3);
		button = menu.addButton();
		button.addAction(new ActionInterface() {

			@Override
			public void onClick()
			{
			}
			
		});
		button1 = menu.addButton();
		button2 = menu.addButton();
		button3 = menu.addButton();
	}

	public void update()
	{
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
