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
		window = new GUIWindow();
		window.setColor(45, 137, 239);
		itemMenu = new GUIItemMenu(3);
		window.setPosition(0, 0);
		window.setSize(Display.getWidth() / 3, Display.getHeight());
		itemMenu.setPosition(window.getX(), window.getY()+30);
		itemMenu.setSize(window.getWidth(), window.getHeight()-60);
		GUIButton button = new GUIButton();
		button.setColor(10, 10, 120);
		button.addAction(new ActionInterface() {

			@Override
			public void onClick()
			{
				System.exit(0);
			}
			
		});
		itemMenu.addButton(button, 0, 0);
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
