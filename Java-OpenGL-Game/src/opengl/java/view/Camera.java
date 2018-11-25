package opengl.java.view;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.entity.Player;
import opengl.java.render.MainRenderer;
import opengl.java.terrain.Terrain;
import opengl.java.window.Window;

public class Camera
{
	private Vector3f position;
	private Vector3f rotation;

	public static final int FIRST_PERSON = 0;
	public static final int THIRD_PERSON = 1;
	public static final int SCOPE = 2;
	
	private float zoom;

	private int mode;

	private static float distanceFromPlayer = 20f;

	// private static int zoom;
	// private static final float maxZoom = 100f;
	// private static final float minZoom = 0f;

	private static Camera singleton = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));

	public Camera(Vector3f position, Vector3f rotation)
	{
		this.position = position;
		this.rotation = rotation;
		mode = FIRST_PERSON;
	}

	public static Camera getInstance()
	{
		return singleton;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public Vector3f getRotation()
	{
		return rotation;
	}

	public double getDistToLookPoint()
	{
		return position.y / Math.sin(Math.toRadians(90) - rotation.x);
	}

	public void update(Player player, Terrain terrain)
	{
		if (mode == THIRD_PERSON)
		{
			float x = distanceFromPlayer * (float) Math.sin(Math.toRadians(player.getRotation().y));
			float z = distanceFromPlayer * (float) Math.cos(Math.toRadians(player.getRotation().y));
			this.setPosition(player.getPosition().x - x,
					player.getPosition().y + 30,
					player.getPosition().z - z);
			rotation.y = 180 - player.getRotation().y;
		}
		else if (mode == FIRST_PERSON)
		{
			this.setPosition(player.getPosition().x, player.getPosition().y + 2.5f, player.getPosition().z);
			rotation.y = 180 - player.getRotation().y;
			rotation.x -= (Mouse.getY() - Window.getHeight() / 2) * 0.1f;
			if (rotation.x > 90)
				rotation.x = 90;
			if (rotation.x < -90)
				rotation.x = -90;
		}
		else if (mode == SCOPE)
		{
			this.setPosition(player.getPosition().x, player.getPosition().y + 2.5f, player.getPosition().z);
			rotation.y = 180 - player.getRotation().y;
			rotation.x -= (Mouse.getY() - Window.getHeight() / 2) / Maths.getDefaultFOV() * zoom/10;
			if (rotation.x > 90)
				rotation.x = 90;
			if (rotation.x < -90)
				rotation.x = -90;
		}

	}
	
	public void setZoom(float zoom) {
		this.zoom = zoom;
		Maths.setFOV(zoom);
		Maths.deleteProjectionMatrix();
		MainRenderer.loadShaders();
	}

	public Camera setMode(int mode)
	{
		this.mode = mode;
		return this;
	}
	
	public float getZoom() {
		return zoom;
	}

	public int getMode()
	{
		return mode;
	}

	public void move(float x, float y, float z)
	{
		this.position.x += x;
		this.position.y += y;
		this.position.z += z;
	}

	public void setPosition(float x, float y, float z)
	{
		this.position = new Vector3f(x, y, z);
	}

	public void rotate(float x, float y, float z)
	{
		rotation.x += x;
		rotation.y += y;
		rotation.z += z;
	}

	public void setRotation(float x, float y, float z)
	{
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}
}
