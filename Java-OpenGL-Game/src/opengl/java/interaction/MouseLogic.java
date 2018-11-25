package opengl.java.interaction;

import org.lwjgl.input.Mouse;

import opengl.java.calculations.Maths;
import opengl.java.terrain.Terrain;
import opengl.java.view.Camera;

public class MouseLogic
{
	private Camera cam = Camera.getInstance();

	private MousePicker picker = MousePicker.getInstance();

	private static final int LEFT_MOUSE_BUTTON = 0;
	private static final int RIGHT_MOUSE_BUTTON = 1;

	private static MouseLogic singleton = new MouseLogic();

	public static MouseLogic getInstance()
	{
		return singleton;
	}

	public void update(Terrain terrain)
	{
		picker.update();
		while (Mouse.next())
		{
			if (Mouse.getEventButtonState())
			{
				if (Mouse.getEventButton() == LEFT_MOUSE_BUTTON)
				{

				}
				if (Mouse.getEventButton() == RIGHT_MOUSE_BUTTON)
				{
					cam.setMode(Camera.SCOPE);
					cam.setZoom(5);
				}
			}
			else
			{
				if (Mouse.getEventButton() == RIGHT_MOUSE_BUTTON)
				{
					cam.setMode(Camera.FIRST_PERSON);
					cam.setZoom(Maths.getDefaultFOV());
				}
			}
		}
	}
}
