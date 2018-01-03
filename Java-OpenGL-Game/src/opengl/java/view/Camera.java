package opengl.java.view;

import org.lwjgl.util.vector.Vector3f;

public class Camera
{
	private Vector3f position;

	private float pitch;
	private float yaw;
	private float roll;

	private boolean locked;

	private static Camera singleton = new Camera(new Vector3f(0, 20, 0), 35f, 45f, 0f);

	public Camera(Vector3f position, float pitch, float yaw, float roll)
	{
		this.position = position;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
	}

	public static Camera getInstance()
	{
		return singleton;
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
		return pitch;
	}

	public float getYaw()
	{
		return yaw;
	}

	public float getRoll()
	{
		return roll;
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

	public double getLookPos()
	{
		double botAngle = Math.toRadians(90 - pitch);
		double dist = position.y / Math.sin(botAngle) * Math.sin(Math.toRadians(pitch));
		return dist;
	}

	public double getLookDistance()
	{
		double botAngle = Math.toRadians(90 - pitch);
		double dist = position.y / Math.sin(botAngle) * Math.sin(Math.toRadians(pitch));
		return dist;
	}
}
