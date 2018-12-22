package opengl.java.interaction;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import opengl.java.view.Camera;
import opengl.java.window.FrameController;

public class MouseLogic
{
	private MousePicker picker = MousePicker.getInstance();

	private static final int LEFT_MOUSE_BUTTON = 0;
	private static final int RIGHT_MOUSE_BUTTON = 1;

	private float mouseSpeed = 200f;

	private Vector2f midPoint = new Vector2f(0, 0);

	private int scrCenterX = Display.getWidth() / 2, scrCenterY = Display.getHeight() / 2;

	private static MouseLogic singleton = new MouseLogic();

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
		float distance = (float) camera.getDistToLookPoint();
		float dx = distance * (float) Math.sin(Math.toRadians(90-camera.getRotation().y));
		float dy = distance * (float) Math.sin(Math.toRadians(camera.getRotation().y));
		if (Mouse.isButtonDown(LEFT_MOUSE_BUTTON))
		{
			camera.rotate(0, 0.1f * FrameController.getFrameTimeSeconds() * mouseSpeed, 0);
			if(camera.getRotation().y < 0.0f) camera.setRotationY(360);
			else if(camera.getRotation().y >= 360.0f) camera.setRotationY(0);
			camera.setPosition(midPoint.x + dx, 5, midPoint.y + dy);
			System.out.println(camera.getPosition() + " / " + midPoint);
		}
	}

	public void handleClicks(Camera camera)
	{
		if (Mouse.getEventButtonState())
		{
			if (Mouse.getEventButton() == LEFT_MOUSE_BUTTON)
			{
				float distance = camera.getDistToLookPoint();
				float dx = distance * (float) Math.sin(Math.toRadians(90-camera.getRotation().y));
				float dy = distance * (float) Math.sin(Math.toRadians(camera.getRotation().y));
				midPoint = new Vector2f(camera.getPosition().x + dx, camera.getPosition().z + dy);
				System.out.println(distance);
			}
			if (Mouse.getEventButton() == RIGHT_MOUSE_BUTTON)
			{

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
		}
	}
}
