package opengl.java.maths;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.view.Camera;

public class Maths
{
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 200;

	private static float FOV = 100;

	private static Matrix4f projectionMatrix = null;

	public static Matrix4f createTransformationMatrix(Vector3f position, Vector3f rotation, float scale)
	{
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(position, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		return matrix;
	}

	public static Matrix4f getProjectionMatrix()
	{
		if (projectionMatrix == null)
			createProjectMat();
		return projectionMatrix;
	}

	private static void createProjectMat()
	{
		projectionMatrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_len = FAR_PLANE - NEAR_PLANE;
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_len);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_len);
		projectionMatrix.m33 = 0;
	}

	public static Matrix4f createViewMatrix()
	{
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(Camera.getRotation().x), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(Camera.getRotation().y), new Vector3f(0, 1, 0), matrix, matrix);
		Vector3f camPos = Camera.getPosition();
		Vector3f negativeCamPos = new Vector3f(-camPos.x, -camPos.y, -camPos.z);
		Matrix4f.translate(negativeCamPos, matrix, matrix);
		return matrix;
	}

	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos)
	{
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

	public static float toOpenGLWidth(float value)
	{
		float halfWidth = Display.getWidth() / 2;
		float result = (halfWidth - value) / -halfWidth;
		return result;
	}

	public static float toOpenGLHeight(float value)
	{
		float halfHeight = Display.getHeight() / 2;
		float result = (halfHeight - value) / halfHeight;
		return result;
	}

	public static float getImageValue(float value, float dimSize)
	{
		float pixel_size = 1f / dimSize;
		return (float) value * pixel_size;
	}
	
	public static float getNearPlane()
	{
		return NEAR_PLANE;
	}

	public static float getFarPlane()
	{
		return FAR_PLANE;
	}

	public static float getFOV()
	{
		return FOV;
	}
}
