package opengl.java.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.view.Camera;
import opengl.java.window.Window;
import opengl.java.window.WindowFrameController;

public class Player
{
	private float x, y, z;
	private float rotX, rotY, rotZ;

	private static final float speed = 0.03f;

	private static final int LEFT_MOUSE_BUTTON = 0;
	private static final int RIGHT_MOUSE_BUTTON = 1;
	private static final int MIDDLE_MOUSE_BUTTON = 2;

	private Vector2f mouseH;

	public Player()
	{
		y = 2;
		mouseH = new Vector2f(0, 0);
	}

	public void update()
	{
		float fts = WindowFrameController.getInstance().getFrameTimeSeconds();
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			float dx = (float) (fts / speed * Math.sin(Math.toRadians(rotY - 90)));
			float dz = (float) (fts / speed * Math.cos(Math.toRadians(rotY - 90)));
			x += dx;
			z -= dz;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			float dx = (float) (fts / speed * Math.sin(Math.toRadians(rotY + 90)));
			float dz = (float) (fts / speed * Math.cos(Math.toRadians(rotY + 90)));
			x += dx;
			z -= dz;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			float dx = (float) (fts / speed * Math.sin(Math.toRadians(rotY)));
			float dz = (float) (fts / speed * Math.cos(Math.toRadians(rotY)));
			x += dx;
			z -= dz;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			float dx = (float) (fts / speed * Math.sin(Math.toRadians(rotY)));
			float dz = (float) (fts / speed * Math.cos(Math.toRadians(rotY)));
			x -= dx;
			z += dz;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			y += 0.03f;
		}
		if (Mouse.next())
		{
			if (Mouse.getEventButtonState())
			{
				if (getEventButton(LEFT_MOUSE_BUTTON))
				{
					mouseH = new Vector2f(Mouse.getX(), Mouse.getY());
					Mouse.setGrabbed(true);
				}
			}
			else
			{
				if (getEventButton(LEFT_MOUSE_BUTTON))
				{
					Mouse.setGrabbed(false);
				}
			}
			if (Mouse.isButtonDown(LEFT_MOUSE_BUTTON) && Mouse.isGrabbed())
			{
				rotX -= (Mouse.getY() - mouseH.y) * 0.1f;
				rotY += (Mouse.getX() - mouseH.x) * 0.1f;
				Mouse.setCursorPosition(Window.getWidth() / 2, Window.getHeight() / 2);
				mouseH = new Vector2f(Mouse.getX(), Mouse.getY());
			}
		}
		Camera.getInstance().move(new Vector3f(x, y, z));
		Camera.getInstance().setRotation(new Vector3f(rotX, rotY, rotZ));
	}

	private boolean getEventButton(int button)
	{
		return Mouse.getEventButton() == button;
	}
}
