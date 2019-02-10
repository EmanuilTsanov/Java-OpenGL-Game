package opengl.java.interaction;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.gui.Inventory;
import opengl.java.view.Camera;

public class MouseMaster
{
	private static final int LEFT_MOUSE_BUTTON = 0;
	private static final int RIGHT_MOUSE_BUTTON = 1;
	private static final int MIDDLE_MOUSE_BUTTON = 2;

	private static boolean lmb, rmb, mmb;

	private static final int ZOOM_STEPS = 10;
	private static final float FINAL_STEP = 3.6f;
	private static final float FIRST_STEP = 1;
	private static float currentStep = FIRST_STEP;
	private static float stepLength = Camera.getDistance() / (float) ZOOM_STEPS;

	private static Vector2f axis = new Vector2f(0, 0);
	private static Vector2f mouseCoords = new Vector2f(0, 0);

	public static void update()
	{
		while (Mouse.next())
		{
			handleClicks();
			if (Mouse.getEventButtonState())
			{
				if (Mouse.getEventButton() == 0)
				{
					Inventory.mouseClick();
				}
			}
			else
			{
			}
		}
		Vector3f camRotation = Camera.getRotation();
		int dWheel = Mouse.getDWheel();
		if (mmb)
		{
			Camera.rotate(0, 0.2f * (Mouse.getX() - mouseCoords.getX()), 0);
			mouseCoords.set(Mouse.getX(), Mouse.getY());
			float distance = (float) (Camera.getDistance() * Math.cos(Math.toRadians(camRotation.x)));
			float dx = (float) (distance * Math.sin(Math.toRadians(camRotation.getY())));
			float dy = (float) (distance * Math.cos(Math.toRadians(camRotation.getY())));
			Camera.setPosition(axis.getX() - dx, Camera.getPosition().y, axis.getY() + dy);
		}
		else if (rmb)
		{
			float distanceX = 0.3f * (Mouse.getX() - mouseCoords.getX()) / currentStep;
			float distanceY = 0.3f * (Mouse.getY() - mouseCoords.getY()) / currentStep;
			mouseCoords.set(Mouse.getX(), Mouse.getY());
			float dx = (float) (distanceX * Math.sin(Math.toRadians(camRotation.y - 90)));
			float dy = (float) (distanceX * Math.cos(Math.toRadians(camRotation.y - 90)));
			float dx1 = (float) (distanceY * Math.sin(Math.toRadians(camRotation.y)));
			float dy1 = (float) (distanceY * Math.cos(Math.toRadians(camRotation.y)));
			Camera.move(dx - dx1, 0, -dy + dy1);
		}
		else if (!lmb && !rmb && !mmb)
		{
			if (dWheel > 0)
			{
				if (currentStep < FINAL_STEP)
				{
					currentStep += 0.4f;
					zoom(stepLength);
				}
			}
			else if (dWheel < 0)
			{
				if (currentStep > FIRST_STEP)
				{
					currentStep -= 0.4f;
					zoom(-stepLength);
				}
			}
		}
	}

	public static void zoom(float speed)
	{
		Vector3f camRotation = Camera.getRotation();
		float dst = (float) (speed * Math.cos(Math.toRadians(camRotation.x)));
		float dy = (float) (speed * Math.sin(Math.toRadians(camRotation.x)));
		float dx = (float) (dst * Math.sin(Math.toRadians(camRotation.y)));
		float dz = (float) (dst * Math.cos(Math.toRadians(camRotation.y)));
		Camera.move(dx, -dy, -dz);
	}

	public static void handleClicks()
	{
		if (Mouse.getEventButtonState())
		{
			if (Mouse.getEventButton() == LEFT_MOUSE_BUTTON)
			{

			}
			if (Mouse.getEventButton() == RIGHT_MOUSE_BUTTON)
			{
				if (!lmb && !mmb)
				{
					rmb = true;
					mouseCoords.set(Mouse.getX(), Mouse.getY());
				}
			}
			if (Mouse.getEventButton() == MIDDLE_MOUSE_BUTTON)
			{
				if (!lmb && !rmb)
				{
					mmb = true;
					Vector3f camRotation = Camera.getRotation();
					float distance = (float) (Camera.getDistance() * Math.cos(Math.toRadians(camRotation.x)));
					float dx = (float) (distance * Math.sin(Math.toRadians(camRotation.y)));
					float dy = (float) (distance * Math.cos(Math.toRadians(camRotation.y)));
					axis.set(Camera.getPosition().x + dx, Camera.getPosition().z - dy);
					mouseCoords.set(Mouse.getX(), Mouse.getY());
				}
			}
		}
		else
		{
			if (Mouse.getEventButton() == LEFT_MOUSE_BUTTON)
			{
				lmb = false;
			}
			if (Mouse.getEventButton() == RIGHT_MOUSE_BUTTON)
			{
				rmb = false;
			}
			if (Mouse.getEventButton() == MIDDLE_MOUSE_BUTTON)
			{
				mmb = false;
			}
		}
	}
}
