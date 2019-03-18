package opengl.java.player;

import org.lwjgl.input.Keyboard;

import opengl.java.view.Camera;

public class Player
{
	public void update()
	{
		float dx = (float) (0.2f * Math.sin(Math.toRadians(Camera.getRotation().y - 90)));
		float dz = (float) (0.2f * Math.cos(Math.toRadians(Camera.getRotation().y - 90)));
		float dx1 = (float) (0.2f * Math.sin(Math.toRadians(Camera.getRotation().y)));
		float dz1 = (float) (0.2f * Math.cos(Math.toRadians(Camera.getRotation().y)));
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			Camera.move(dx, 0, -dz);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			Camera.move(-dx, 0, dz);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			Camera.move(dx1, 0, -dz1);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			Camera.move(-dx1, 0, dz1);
		}
	}
}
