package opengl.java.interaction;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.gui.Inventory;
import opengl.java.view.Camera;
import opengl.java.window.Window;
import opengl.java.window.WindowManager;

public class KeyboardMaster
{

	private static Vector3f axis = new Vector3f(0, 0, 0);
	private static float length;

	public static void update()
	{
		while (Keyboard.next())
		{
			if (Keyboard.getEventKeyState())
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_A || Keyboard.getEventKey() == Keyboard.KEY_D)
				{
					findAxis();
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_W || Keyboard.getEventKey() == Keyboard.KEY_S)
				{
					findAxis();
					length = Camera.getDistance();
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_1)
				{
					Window.setDisplayMode(1280, 720, false);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_2)
				{
					Window.setDisplayMode(1280, 720, true);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_3)
				{
					Window.setDisplayMode(2715, 1527, true);
				}
			}
			else
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_TAB)
				{
					Inventory.toggle();
				}
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			rotateAroundAxis(100);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			rotateAroundAxis(-100);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			rotateOverAxis(100);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			rotateOverAxis(-100);
		}
	}

	public static void rotateAroundAxis(float speed)
	{
		Vector3f camRotation = Camera.getRotation();
		float camDistance = Camera.getDistance();
		Camera.rotate(0, speed * WindowManager.getFrameTimeSeconds(), 0);
		float distance = (float) (camDistance * Math.cos(Math.toRadians(camRotation.x)));
		float dx = (float) (distance * Math.sin(Math.toRadians(camRotation.getY())));
		float dy = (float) (distance * Math.cos(Math.toRadians(camRotation.getY())));
		Camera.setPosition(axis.getX() - dx, (float) (camDistance * Math.sin(Math.toRadians(camRotation.x))), axis.getY() + dy);
	}

	public static void rotateOverAxis(float speed)
	{
		Vector3f camRotation = Camera.getRotation();
		Camera.rotate(speed * WindowManager.getFrameTimeSeconds(), 0, 0);
		float y = (float) (length * Math.sin(Math.toRadians(camRotation.x)));
		float dist = (float) (length * Math.cos(Math.toRadians(camRotation.x)));
		float x = (float) (dist * Math.sin(Math.toRadians(camRotation.y)));
		float z = (float) (dist * Math.cos(Math.toRadians(camRotation.y)));
		Camera.setPosition(axis.getX() - x, y, axis.getY() + z);
	}

	public static void findAxis()
	{
		Vector3f camRotation = Camera.getRotation();
		float distance = (float) (Camera.getDistance() * Math.cos(Math.toRadians(camRotation.x)));
		float dx = (float) (distance * Math.sin(Math.toRadians(camRotation.y)));
		float dy = (float) (distance * Math.cos(Math.toRadians(camRotation.y)));
		axis.set(Camera.getPosition().x + dx, Camera.getPosition().z - dy);
	}
}
