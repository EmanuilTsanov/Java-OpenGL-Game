package opengl.java.entity;

import org.lwjgl.input.Keyboard;

import opengl.java.window.WindowFrameController;

public class Car extends Entity
{
	private float speed;
	private float turnSpeed;

	public Car(int asset)
	{
		super(asset);
	}

	public void update()
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			speed += 0.1f * WindowFrameController.getFrameTimeSeconds();
		}
		if (!Keyboard.isKeyDown(Keyboard.KEY_W) && speed > 0)
		{
			speed -= 0.05f * WindowFrameController.getFrameTimeSeconds();
			if (speed < 0)
				speed = 0;
		}
		if (speed > 0.05f)
			this.increaseRotation(0, turnSpeed, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			speed -= 0.3f * WindowFrameController.getFrameTimeSeconds();
			if (speed < 0)
				speed = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A) && speed > 0)
		{
			turnSpeed = 30f * WindowFrameController.getFrameTimeSeconds();
		}
		if (!Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			turnSpeed = 0;
		}
		float dx = (float) Math.sin(rotation.y) * speed;
		float dz = (float) Math.cos(rotation.y) * speed;
		this.increasePosition(dx, 0, dz);
	}
}
