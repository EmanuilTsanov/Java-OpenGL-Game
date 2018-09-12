package opengl.java.view;

import org.lwjgl.util.vector.Vector3f;

public class Camera
{
	private Vector3f position;

	private float xRotation;
	private float yRotation;
	private float zRotation;

	private static Camera singleton = new Camera(new Vector3f(0, 50, 0), 45f, 180f, 0f);

	public Camera(Vector3f position, float xRot, float yRot, float zRot)
	{
		this.position = position;
		this.xRotation = (float) Math.toRadians(xRot);
		this.yRotation = (float) Math.toRadians(yRot);
		this.zRotation = (float) Math.toRadians(zRot);
	}

	public static Camera getInstance()
	{
		return singleton;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public float getXRotation()
	{
		return xRotation;
	}

	public float getYRotation()
	{
		return yRotation;
	}

	public float getZRotation()
	{
		return zRotation;
	}

	public double getDistToLookPoint()
	{
		return position.y / Math.sin(Math.toRadians(90) - xRotation);
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

	public void rotate(Vector3f rotation)
	{
		this.xRotation = (float) Math.toRadians(rotation.x);
		this.yRotation = (float) Math.toRadians(rotation.y);
		this.zRotation = (float) Math.toRadians(rotation.z);
	}

	public void rotateBy(float x, float y, float z)
	{
		this.xRotation += (float) Math.toRadians(x);
		this.yRotation += (float) Math.toRadians(y);
		this.zRotation += (float) Math.toRadians(z);
	}
}
