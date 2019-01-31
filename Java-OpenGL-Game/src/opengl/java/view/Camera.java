package opengl.java.view;

import org.lwjgl.util.vector.Vector3f;

public class Camera
{
	private Vector3f position;
	private Vector3f rotation;

	public Camera(Vector3f position, Vector3f rotation)
	{
		this.position = position;
		this.rotation = rotation;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public Vector3f getRotation()
	{
		return rotation;
	}

	public float getDistance()
	{
		return position.y / (float) Math.sin(Math.toRadians(rotation.x));
	}

	public void move(float x, float y, float z)
	{
		position.x += x;
		position.y += y;
		position.z += z;
	}

	public void setPosition(float x, float y, float z)
	{
		position = new Vector3f(x, y, z);
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
