package opengl.java.view;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.window.Window;

public class Camera {
	
	private Vector3f position;
	
	private float pitch;
	private float yaw;
	private float roll;
	
	boolean picked;

	private boolean locked;

	private Vector2f pickLocation = new Vector2f(0, 0);

	public Camera(Vector3f position, float pitch, float yaw, float roll) {
		this.position = position;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return (float) Math.toRadians(pitch);
	}

	public float getYaw() {
		return (float) Math.toRadians(yaw);
	}

	public float getRoll() {
		return (float) Math.toRadians(roll);
	}

	public void update() {
		control();
	}

	public void control() {
		if (Mouse.isButtonDown(1)) {
			if (!picked) {
				pickLocation.x = Mouse.getX();
				pickLocation.y = Mouse.getY();
				picked = true;
			}
			Mouse.setGrabbed(true);
			float distanceX = (pickLocation.x - Mouse.getX()) * 0.1f;
			float distanceY = (pickLocation.y - Mouse.getY()) * 0.1f;
			float dx = (float) Math.cos(Math.toRadians(yaw)) * distanceX;
			float dz = (float) Math.sin(Math.toRadians(yaw)) * distanceX;
			float dx1 = (float) Math.cos(Math.toRadians(yaw + 90)) * -distanceY;
			float dz1 = (float) Math.sin(Math.toRadians(yaw + 90)) * -distanceY;
			position.x += dx += dx1;
			position.z += dz += dz1;
			Mouse.setCursorPosition(Window.WIDTH / 2, Window.HEIGHT / 2);
			pickLocation.x = Mouse.getX();
			pickLocation.y = Mouse.getY();
		} else {
			Mouse.setGrabbed(false);
			picked = false;
		}

	}

	public void move(Vector3f position, Vector3f rotation) {
		this.position = position;
		if (!locked) {
			this.pitch = rotation.x;
			this.yaw = rotation.y;
			this.roll = rotation.z;
		}
	}
}
