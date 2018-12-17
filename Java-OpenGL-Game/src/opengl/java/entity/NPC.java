package opengl.java.entity;

import java.util.Random;

import opengl.java.window.WindowFrameController;

public class NPC
{
	private static float speed = 6f;

	private float angle = new Random().nextInt(90);

	public void control(Player player)
	{
		float a = speed * WindowFrameController.getFrameTimeSeconds();
		float camYaw = (float) Math.toRadians(angle);
		float dx = (float) Math.cos(camYaw) * a;
		float dz = (float) Math.sin(camYaw) * a;
		player.move(-dx, 0, -dz);
		int r = new Random().nextInt(1000);
		if (r <= 5)
		{
			angle += new Random().nextInt(90);
		}
		else if (r > 5 && r < 10)
		{
			if(speed != 0)
			speed = 0;
			else speed = 6f;
		}
	}
}
