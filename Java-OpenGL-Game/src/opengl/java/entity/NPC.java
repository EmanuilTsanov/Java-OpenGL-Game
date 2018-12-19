package opengl.java.entity;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.window.FrameController;

public class NPC extends Thread
{
	private static float speed = 6f;
	private float direction;
	private long start;
	private boolean walking;
	private long time;
	private Random random = new Random();

	public NPC()
	{
		direction = random.nextFloat() * 360;
		start = System.currentTimeMillis();
		time = random.nextInt(10000);
	}

	public void control(Player player)
	{
		if (walking)
		{
			float a = speed * FrameController.getFrameTimeSeconds();
			float camYaw = (float) Math.toRadians(direction);
			float dx = (float) Math.cos(camYaw) * a;
			float dz = (float) Math.sin(camYaw) * a;
			player.move(-dx, 0, -dz);
			player.setRotation(new Vector3f(0, direction, 0));
			if (System.currentTimeMillis() - start >= time)
			{
				start = System.currentTimeMillis();
				time = random.nextInt(6000)+4000;
				walking = false;
			}
		}
		else
		{
			if (System.currentTimeMillis() - start >= time)
			{
				start = System.currentTimeMillis();
				direction = new Random().nextFloat() * 360;
				time = random.nextInt(6000) + 4000;
				walking = true;
			}
		}
	}
}
