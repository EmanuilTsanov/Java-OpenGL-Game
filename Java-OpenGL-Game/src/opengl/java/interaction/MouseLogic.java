package opengl.java.interaction;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import opengl.java.view.Camera;

public class MouseLogic
{
	private MousePicker picker = MousePicker.getInstance();

	private static final int LEFT_MOUSE_BUTTON = 0;
	private static final int RIGHT_MOUSE_BUTTON = 1;
	private static final int MIDDLE_MOUSE_BUTTON = 2;

	private static final int ZOOM_STEPS = 10;
	private static final int FINAL_STEP = 8;
	private static final int FIRST_STEP = 1;
	private static int currentStep;
	private static float stepLength;

	private Vector2f axis = new Vector2f(0, 0);
	private Vector2f mouseCoords = new Vector2f(0, 0);

	private static MouseLogic singleton = new MouseLogic();

	private MouseLogic()
	{
		currentStep = FIRST_STEP;
		stepLength = Camera.getInstance().getDistance() / (float) ZOOM_STEPS;
	}

	public static MouseLogic getInstance()
	{
		return singleton;
	}

	public void update(Camera camera)
	{
		picker.update();
		while (Mouse.next())
		{
			handleClicks(camera);
		}
		if (Mouse.isButtonDown(MIDDLE_MOUSE_BUTTON))
		{
			camera.rotate(0, 0.2f * (Mouse.getX() - mouseCoords.getX()), 0);
			mouseCoords.set(Mouse.getX(), Mouse.getY());
			float distance = (float) (camera.getDistance() * Math.cos(Math.toRadians(camera.getRotation().x)));
			float dx = (float) (distance * Math.sin(Math.toRadians(camera.getRotation().getY())));
			float dy = (float) (distance * Math.cos(Math.toRadians(camera.getRotation().getY())));
			camera.setPosition(axis.getX() - dx, camera.getPosition().y, axis.getY() + dy);
		}
		if (Mouse.isButtonDown(RIGHT_MOUSE_BUTTON))
		{
			float distanceX = 0.2f * (Mouse.getX() - mouseCoords.getX());
			float distanceY = 0.2f * (Mouse.getY() - mouseCoords.getY());
			mouseCoords.set(Mouse.getX(), Mouse.getY());
			float dx = (float) (distanceX * Math.sin(Math.toRadians(camera.getRotation().y - 90)));
			float dy = (float) (distanceX * Math.cos(Math.toRadians(camera.getRotation().y - 90)));
			float dx1 = (float) (distanceY * Math.sin(Math.toRadians(camera.getRotation().y)));
			float dy1 = (float) (distanceY * Math.cos(Math.toRadians(camera.getRotation().y)));
			camera.move(dx - dx1, 0, -dy + dy1);
		}
		int dWheel = Mouse.getDWheel();
		if (dWheel > 0)
		{
			if (currentStep < FINAL_STEP)
			{
				currentStep++;
				zoom(stepLength, camera);
			}
		}
		else if (dWheel < 0)
		{
			if (currentStep > FIRST_STEP)
			{
				currentStep--;
				zoom(-stepLength, camera);
			}
		}
	}

	public void zoom(float speed, Camera camera)
	{
		float dst = (float) (speed * Math.cos(Math.toRadians(camera.getRotation().x)));
		float dy = (float) (speed * Math.sin(Math.toRadians(camera.getRotation().x)));
		float dx = (float) (dst * Math.sin(Math.toRadians(camera.getRotation().y)));
		float dz = (float) (dst * Math.cos(Math.toRadians(camera.getRotation().y)));
		camera.move(dx, -dy, -dz);
	}

	public void handleClicks(Camera camera)
	{
		if (Mouse.getEventButtonState())
		{
			if (Mouse.getEventButton() == LEFT_MOUSE_BUTTON)
			{

			}
			if (Mouse.getEventButton() == RIGHT_MOUSE_BUTTON)
			{
				mouseCoords.set(Mouse.getX(), Mouse.getY());
			}
			if (Mouse.getEventButton() == MIDDLE_MOUSE_BUTTON)
			{
				float distance = (float) (camera.getDistance() * Math.cos(Math.toRadians(camera.getRotation().x)));
				float dx = (float) (distance * Math.sin(Math.toRadians(camera.getRotation().y)));
				float dy = (float) (distance * Math.cos(Math.toRadians(camera.getRotation().y)));
				axis.set(camera.getPosition().x + dx, camera.getPosition().z - dy);
				mouseCoords.set(Mouse.getX(), Mouse.getY());
			}
		}
		else
		{
			if (Mouse.getEventButton() == LEFT_MOUSE_BUTTON)
			{

			}
			if (Mouse.getEventButton() == RIGHT_MOUSE_BUTTON)
			{

			}
			if (Mouse.getEventButton() == MIDDLE_MOUSE_BUTTON)
			{
			}
		}
	}
}
