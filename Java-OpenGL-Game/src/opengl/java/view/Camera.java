package opengl.java.view;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.terrain.TerrainGenerator;

public class Camera
{
	private Vector3f position;

	private float pitch;
	private float yaw;
	private float roll;

	private static Camera singleton = new Camera(new Vector3f(TerrainGenerator.getFullSize() / 2, 20, TerrainGenerator.getFullSize() / 2), 45f, 0f, 0f);

	public Camera(Vector3f position, float pitch, float yaw, float roll)
	{
		this.position = position;
		this.pitch = (float) Math.toRadians(pitch);
		this.yaw = (float) Math.toRadians(yaw);
		this.roll = (float) Math.toRadians(roll);
	}

	public static Camera getInstance()
	{
		return singleton;
	}

	public Vector3f getPosition()
	{
		return position;
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

	public double getDistToLookPoint()
	{
		double botAngle = Math.toRadians(90 - pitch);
		double dist = position.y / Math.sin(botAngle) * Math.sin(pitch);
		return dist;
	}

	public void move(Vector3f position)
	{
		this.position = position;
	}

	public void moveBy(float x, float y, float z)
	{
		this.position.x += x;
		this.position.y += y;
		this.position.z += z;
	}
}
