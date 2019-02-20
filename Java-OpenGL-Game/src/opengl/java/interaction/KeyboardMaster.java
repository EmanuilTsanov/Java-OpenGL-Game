package opengl.java.interaction;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.gui.Inventory;
import opengl.java.view.Camera;
import opengl.java.window.FrameController;

public class KeyboardMaster
{

	static Vector3f axis = new Vector3f(0, 0, 0);

	public static void update()
	{
		while (Keyboard.next())
		{
			if (Keyboard.getEventKeyState())
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_A || Keyboard.getEventKey() == Keyboard.KEY_D)
				{
					Vector3f camRotation = Camera.getRotation();
					float distance = (float) (Camera.getDistance() * Math.cos(Math.toRadians(camRotation.x)));
					float dx = (float) (distance * Math.sin(Math.toRadians(camRotation.y)));
					float dy = (float) (distance * Math.cos(Math.toRadians(camRotation.y)));
					axis.set(Camera.getPosition().x + dx, Camera.getPosition().z - dy);
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
			Vector3f camRotation = Camera.getRotation();
			Camera.rotate(0, 100*FrameController.getFrameTimeSeconds(), 0);
			float distance = (float) (Camera.getDistance() * Math.cos(Math.toRadians(camRotation.x)));
			float dx = (float) (distance * Math.sin(Math.toRadians(camRotation.getY())));
			float dy = (float) (distance * Math.cos(Math.toRadians(camRotation.getY())));
			Camera.setPosition(axis.getX() - dx, Camera.getPosition().y, axis.getY() + dy);
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			Vector3f camRotation = Camera.getRotation();
			Camera.rotate(0, -100*FrameController.getFrameTimeSeconds(), 0);
			float distance = (float) (Camera.getDistance() * Math.cos(Math.toRadians(camRotation.x)));
			float dx = (float) (distance * Math.sin(Math.toRadians(camRotation.getY())));
			float dy = (float) (distance * Math.cos(Math.toRadians(camRotation.getY())));
			Camera.setPosition(axis.getX() - dx, Camera.getPosition().y, axis.getY() + dy);
		}
	}
}
