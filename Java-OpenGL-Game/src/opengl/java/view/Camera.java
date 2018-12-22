package opengl.java.view;

import org.lwjgl.util.vector.Vector3f;

public class Camera
{
	private Vector3f position;
	private Vector3f rotation;
	
	private static Camera singleton = new Camera(new Vector3f(500, 50f, 500), new Vector3f(45f, 0, 0));

	public Camera(Vector3f position, Vector3f rotation)
	{
		this.position = position;
		this.rotation = rotation;
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

	public float getDistToLookPoint()
	{
		return position.y / (float) Math.sin(Math.toRadians(rotation.x));
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

	public void setRotationX(float x)
	{
		rotation.x = x;
	}

	public void setRotationY(float y)
	{
		rotation.y = y;
	}

	public void setRotationZ(float z)
	{
		rotation.z = z;
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
