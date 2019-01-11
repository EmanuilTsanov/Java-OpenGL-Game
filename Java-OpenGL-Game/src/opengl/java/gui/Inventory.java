package opengl.java.gui;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import opengl.java.shader.GUIShader;
import opengl.java.window.FrameController;

public class Inventory
{
	private GUIShader shader;
	private GUIWindow window;
	private GUIItemMenu menu;
	private GUIButton button, button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13, button14, button15;

	private GUIButton buttonInv;

	private boolean isOpened = true;

	public Inventory()
	{
		shader = new GUIShader();
		window = new GUIWindow(0, 0, Display.getWidth() / 3, Display.getHeight(), null);
		window.setColor(45, 147, 239);
		setupItemMenu();
		window.addComponent(menu);
	}

	public void setupItemMenu()
	{
		menu = new GUIItemMenu(window.getX(), window.getY() + 30, window.getWidth(), window.getHeight() - 60, window, 3);
		button = menu.addButton();
		button.addAction(new ActionInterface() {

			@Override
			public void onClick()
			{
				menu.changePage(1);
			}

		});
		button1 = menu.addButton();
		button2 = menu.addButton();
		button3 = menu.addButton();
		button4 = menu.addButton();
		button5 = menu.addButton();
		button6 = menu.addButton();
		button7 = menu.addButton();
		button8 = menu.addButton();
		button9 = menu.addButton();
		button10 = menu.addButton();
		button11 = menu.addButton();
		button12 = menu.addButton();
		button13 = menu.addButton();
		button14 = menu.addButton();
		button15 = menu.addButton();
		button15.addAction(new ActionInterface() {

			@Override
			public void onClick()
			{
				menu.changePage(0);
			}

		});

		buttonInv = new GUIButton(10, 10, 50, 50, null);
		buttonInv.addAction(new ActionInterface() {

			@Override
			public void onClick()
			{
				if (isOpened)
					close();
				else
					open();
			}

		});
	}

	public void open()
	{
		isOpened = true;
	}

	public void close()
	{
		isOpened = false;
	}

	public void update()
	{
		if (window.getX() + window.getWidth() > 0 || isOpened)
		{
			window.update();
			if (isOpened && window.getX() < 0)
			{
				window.moveByX((int)FrameController.getFrameTimeSeconds() * 1000);
				System.out.println(1);
			}
			else if (!isOpened && window.getX() + window.getWidth() > 0)
			{
				window.moveByX((int)-(FrameController.getFrameTimeSeconds() * 1000));
				System.out.println(1);
			}
		}
		buttonInv.update();
	}

	public void render()
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		shader.start();
		if (window.getX() + window.getWidth() > 0 || isOpened)
		{
			window.render(shader);
		}
		buttonInv.render(shader);
		shader.stop();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
}
