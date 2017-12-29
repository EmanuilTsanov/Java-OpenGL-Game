package opengl.java.view;

import org.lwjgl.util.vector.Vector3f;

public class Camera
{

	private Vector3f position;

	private float pitch;
	private float yaw;
	private float roll;
	private boolean locked;

	public Camera(Vector3f position, float pitch, float yaw, float roll)
	{
		this.position = position;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public void increasePosition(float x, float y, float z)
	{
		this.position.x += x;
		this.position.y += y;
		this.position.z += z;
	}

	public float getPitch()
	{
		return (float) Math.toRadians(pitch);
	}

	public float getYaw()
	{
		return (float) Math.toRadians(yaw);
	}

	public float getRoll()
	{
		return (float) Math.toRadians(roll);
	}

	public void update()
	{
	}

	public void move(Vector3f position, Vector3f rotation)
	{
		this.position = position;
		if (!locked)
		{
			this.pitch = rotation.x;
			this.yaw = rotation.y;
			this.roll = rotation.z;
		}
	}
}
