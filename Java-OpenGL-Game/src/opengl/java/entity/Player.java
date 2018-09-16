package opengl.java.entity;

import org.lwjgl.input.Keyboard;

import opengl.java.model.TexturedModel;
import opengl.java.terrain.Terrain;

public class Player extends Entity
{
	public Player()
	{
		super(TexturedModel.PLAYER.getID());
	}

	private static final float speed = 0.2f;

	public void update(Terrain terrain)
	{
		float camYaw = (float) Math.toRadians(rotation.y);
		float dx = (float) Math.sin(camYaw) * speed;
		float dz = (float) Math.cos(camYaw) * speed;
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			position.x += dx;
			position.z += dz;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			position.x -= dx;
			position.z -= dz;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			rotate(0, 1, 0);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{

			rotate(0, -1, 0);
		}
		position.y = terrain.getHeightOfTerrain(position.x, position.z);
	}
}
