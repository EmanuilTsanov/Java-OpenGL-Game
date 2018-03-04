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

	private static final float speed = 20f;

	private static final float jumpSpeed = 1f;
	private float currentJumpSpeed;
	private boolean jumping;

	private Vector2f mouseH;

	public Player()
	{
		y = 2;
		mouseH = new Vector2f(0, 0);
		Mouse.setGrabbed(true);
	}

	public void update()
	{
		float finalSpeed = speed * WindowFrameController.getFrameTimeSeconds();
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			float dx = (float) (finalSpeed * Math.sin(Math.toRadians(rotY - 90)));
			float dz = (float) (finalSpeed * Math.cos(Math.toRadians(rotY - 90)));
			x += dx;
			z -= dz;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			float dx = (float) (finalSpeed * Math.sin(Math.toRadians(rotY + 90)));
			float dz = (float) (finalSpeed * Math.cos(Math.toRadians(rotY + 90)));
			x += dx;
			z -= dz;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			float dx = (float) (finalSpeed * Math.sin(Math.toRadians(rotY)));
			float dz = (float) (finalSpeed * Math.cos(Math.toRadians(rotY)));
			x += dx;
			z -= dz;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			float dx = (float) (finalSpeed * Math.sin(Math.toRadians(rotY)));
			float dz = (float) (finalSpeed * Math.cos(Math.toRadians(rotY)));
			x -= dx;
			z += dz;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			if (!jumping)
			{
				jumping = true;
				currentJumpSpeed = jumpSpeed;
			}
		}
		if (Mouse.next())
		{
			rotX -= (Mouse.getY() - mouseH.y) * 0.1f;
			rotY += (Mouse.getX() - mouseH.x) * 0.1f;
			if (rotX < -90)
				rotX = -90;
			else if (rotX > 90)
				rotX = 90;
			Mouse.setCursorPosition(Window.getWidth() / 2, Window.getHeight() / 2);
			mouseH = new Vector2f(Mouse.getX(), Mouse.getY());
		}
		if (jumping)
		{
			y += currentJumpSpeed * finalSpeed;
			currentJumpSpeed -= 0.1f * finalSpeed;
			if (y < 2)
			{
				y = 2;
				jumping = false;
			}
		}
		Camera.getInstance().move(new Vector3f(x, y, z));
		Camera.getInstance().setRotation(new Vector3f(rotX, rotY, rotZ));
	}
}
