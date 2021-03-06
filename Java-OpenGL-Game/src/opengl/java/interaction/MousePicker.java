package opengl.java.interaction;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import opengl.java.maths.Maths;
import opengl.java.view.Camera;

public class MousePicker
{
	private static Vector3f worldRay;

	private static Matrix4f projectionMatrix = Maths.getProjectionMatrix();
	private static Matrix4f viewMatrix = Maths.createViewMatrix();

	private static Vector2f mousePosition = new Vector2f(0, 0);
	private static Vector3f terrainPosition = new Vector3f(0, 0, 0);

	private static final int RANGE = 600;
	private static final int LOOPS = 200;

	public static Vector3f getWorldRay()
	{
		return worldRay;
	}

	public static Vector3f getMapPosition()
	{
		Vector2f mousePos = new Vector2f(Mouse.getX(), Mouse.getY());
		if (mousePos.x != mousePosition.x || mousePos.y != mousePosition.y)
		{
			mousePosition.set(mousePos.x, mousePos.y);
			terrainPosition = searchRay(0, 0, RANGE, worldRay);
		}
		return terrainPosition;
	}

	public static void update()
	{
		viewMatrix = Maths.createViewMatrix();
		worldRay = calculateMouseRay();
	}

	private static Vector3f calculateMouseRay()
	{
		float mouseX = Mouse.getX();
		float mouseY = Mouse.getY();
		Vector2f normalizedCoords = normalizeDiviceCoords(mouseX, mouseY);
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);

		Vector4f eyeSpace = toEyeSpace(clipCoords);
		Vector3f worldSpace = toWorldSpace(eyeSpace);
		return worldSpace;
	}

	private static Vector2f normalizeDiviceCoords(float mouseX, float mouseY)
	{
		float x = (2f * mouseX) / Display.getWidth() - 1f;
		float y = (2f * mouseY) / Display.getHeight() - 1f;
		return new Vector2f(x, y);
	}

	private static Vector4f toEyeSpace(Vector4f clipCoords)
	{
		Matrix4f invertedMatrix = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invertedMatrix, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}

	private static Vector3f toWorldSpace(Vector4f eyeSpace)
	{
		Matrix4f invertedMatrix = Matrix4f.invert(viewMatrix, null);
		Vector4f worldRay = Matrix4f.transform(invertedMatrix, eyeSpace, null);
		Vector3f mouseRay = new Vector3f(worldRay.x, worldRay.y, worldRay.z);
		mouseRay.normalise();
		return mouseRay;
	}

	private static Vector3f getPointOnVector(Vector3f ray, float distance)
	{
		Vector3f camPosition = Camera.getPosition();
		Vector3f start = new Vector3f(camPosition.x, camPosition.y, camPosition.z);
		Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
		return Vector3f.add(start, scaledRay, null);
	}

	private static Vector3f searchRay(int loops, float start, float end, Vector3f ray)
	{
		float middle = start + (end - start) / 2f;
		if (loops >= LOOPS)
		{
			Vector3f terrainLoc = getPointOnVector(ray, middle);
			return new Vector3f(terrainLoc.x, terrainLoc.y, terrainLoc.z);
		}
		if (terrainIntersection(start, middle, ray))
			return searchRay(loops + 1, start, middle, ray);
		else
			return searchRay(loops + 1, middle, end, ray);
	}

	private static boolean terrainIntersection(float start, float end, Vector3f ray)
	{
		Vector3f sPoint = getPointOnVector(ray, start);
		Vector3f ePoint = getPointOnVector(ray, end);
		if (sPoint.getY() > 0 && ePoint.getY() < 0)
			return true;
		else
			return false;
	}
}
