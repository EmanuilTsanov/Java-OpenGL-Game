package opengl.java.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.terrain.Terrain;
import opengl.java.view.Camera;

public class Player
{
	private float x, y, z;
	private float xR, yR, zR;

	private static final float speed = 0.2f;

	public Player(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void update(Camera camera, Terrain terrain)
	{
		float camYaw = camera.getYRotation()+ (float)Math.toRadians(90);
		float dx = (float) Math.cos(camYaw) * speed;
		float dz = (float) Math.sin(camYaw) * speed;
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			x -= dx;
			z -= dz;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			x += dx;
			z += dz;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{

			yR -= 1;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{

			yR += 1;
		}
		camera.move(x, terrain.getHeightOfTerrain(x, z)+5, z);
		camera.rotate(new Vector3f(xR, yR, zR));
	}
}
