package opengl.java.view;

import org.lwjgl.util.vector.Vector3f;

public class Camera
{
	private static Vector3f position;
	private static Vector3f rotation;

	public static void initialize(Vector3f position, Vector3f rotation)
	{
		Camera.position = position;
		Camera.rotation = rotation;
	}

	public static Vector3f getPosition()
	{
		return position;
	}

	public static Vector3f getRotation()
	{
		return rotation;
	}

	public static float getDistance()
	{
		return position.y / (float) Math.sin(Math.toRadians(rotation.x));
	}

	public static void move(float x, float y, float z)
	{
		position.x += x;
		position.y += y;
		position.z += z;
	}

	public static void setPosition(float x, float y, float z)
	{
		position = new Vector3f(x, y, z);
	}

	public static void rotate(float x, float y, float z)
	{
		rotation.x += x;
		rotation.y += y;
		rotation.z += z;
	}

	public static void setRotation(float x, float y, float z)
	{
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}
}
