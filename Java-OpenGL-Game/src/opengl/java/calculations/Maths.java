package opengl.java.calculations;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.view.Camera;

public class Maths {
	public static float FOV = 90;
	public static final float NEAR_PLANE = 0.1f;
	public static final float FAR_PLANE = 1000;

	private static int width = Display.getWidth();
	private static int height = Display.getWidth();
	private static float width_half = width / 2;
	private static float height_half = height / 2;
	private static float pixel_width = (1f / (float) width) * 2;
	private static float pixel_height = (1f / (float) height) * 2;

	private static Matrix4f projectionMatrix = null;

	public static Matrix4f createTransMat(Vector3f position, Vector3f rotation, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(position, matrix, matrix);
		Matrix4f.rotate(rotation.x, new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate(rotation.y, new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate(rotation.z, new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		return matrix;
	}

	private static void createProjectMat() {
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float x_scale = (float) (1f / Math.tan(Math.toRadians(FOV / 2f)));
		float y_scale = x_scale * aspectRatio;
		float frustum_len = FAR_PLANE - NEAR_PLANE;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_len);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * FAR_PLANE * NEAR_PLANE) / frustum_len);
		projectionMatrix.m33 = 0;
	}

	public static Matrix4f createViewMatrix() {
		Camera camera = Camera.getInstance();
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.rotate((float)Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), matrix, matrix);
		Vector3f camPos = camera.getPosition();
		Vector3f negativeCamPos = new Vector3f(-camPos.x, -camPos.y, -camPos.z);
		Matrix4f.translate(negativeCamPos, matrix, matrix);
		return matrix;
	}

	public static Matrix4f getProjectionMatrix() {
		if (projectionMatrix == null)
			createProjectMat();
		return projectionMatrix;
	}

	public static float getScreenValue(float value, int type) {
		if (type == 0) {
			if (value < width_half) {
				return (-(width_half - value - 1)) * pixel_width;
			} else if (value > width_half) {
				return (value - width_half - 1) * pixel_width;
			}
		} else if (type == 1) {
			if (value < height_half) {
				return (height_half - value + 1) * pixel_height;
			} else if (value > height_half) {
				return (-(value - height_half + 1)) * pixel_height;
			}
		}
		return 0;
	}

	public static float getImageValue(float value, float dimSize) {
		float pixel_size = 1f / dimSize;
		return (float) value * pixel_size;
	}

	public static Vector3f normalizeColor(Vector3f v) {
		float px = (1f / 255f);
		float x = px * v.x;
		float y = px * v.y;
		float z = px * v.z;
		return new Vector3f(x, y, z);
	}
}
