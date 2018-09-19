package opengl.java.entity;

import org.lwjgl.input.Keyboard;

import opengl.java.model.TexturedModel;
import opengl.java.terrain.Terrain;
import opengl.java.view.Camera;

public class Player extends Entity
{
	// private boolean jumping;
	// private int jumpSpeed;

	public Player()
	{
		super(TexturedModel.PLAYER.getID());
	}

	private static final float speed = 0.2f;

	public void update(Camera camera, Terrain terrain)
	{
		if (camera.getMode() == Camera.THIRD_PERSON)
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
		else if (camera.getMode() == Camera.FIRST_PERSON)
		{

			float camYaw = (float) Math.toRadians(camera.getRotation().y + 90);
			float dx = (float) Math.cos(camYaw) * speed;
			float dz = (float) Math.sin(camYaw) * speed;
			if (Keyboard.isKeyDown(Keyboard.KEY_W))
			{
				position.x -= dx;
				position.z -= dz;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S))
			{
				position.x += dx;
				position.z += dz;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A))
			{
				float rot = (float) Math.toRadians(camera.getRotation().y);
				float dx1 = (float) Math.cos(rot) * speed;
				float dz1 = (float) Math.sin(rot) * speed;
				position.x -= dx1;
				position.z -= dz1;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
			{
				float rot = (float) Math.toRadians(camera.getRotation().y - 180);
				float dx1 = (float) Math.cos(rot) * speed;
				float dz1 = (float) Math.sin(rot) * speed;
				position.x -= dx1;
				position.z -= dz1;
			}
			position.y = terrain.getHeightOfTerrain(position.x, position.z);
		}
	}
}
