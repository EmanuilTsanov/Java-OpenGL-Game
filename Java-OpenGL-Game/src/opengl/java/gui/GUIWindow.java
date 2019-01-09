package opengl.java.gui;

import java.util.ArrayList;

import opengl.java.shader.GUIShader;

public class GUIWindow extends GUIComponent
{
	private ArrayList<GUIComponent> components = new ArrayList<GUIComponent>();

	public GUIWindow(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	public void addComponent(GUIComponent component)
	{
		if (component.getWidth() > width || component.getHeight() > height || component.getX() + component.getWidth() > x + width || component.getY() + component.getHeight() > y + height)
		{
			System.out.println("There was a problem adding a component.");
		}
		else
		{
			components.add(component);
		}
	}
	
	public void update() {
		for(GUIComponent component : components) {
			component.update();
		}
	}

	@Override
	public void render(GUIShader shader)
	{
		super.render(shader);
		for (GUIComponent component : components)
		{
			component.render(shader);
		}
	}
}