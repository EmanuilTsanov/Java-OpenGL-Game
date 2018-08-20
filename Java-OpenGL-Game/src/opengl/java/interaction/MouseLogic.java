package opengl.java.interaction;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import opengl.java.entity.Entity;
import opengl.java.management.EntityManager;
import opengl.java.model.TexturedModel;
import opengl.java.view.Camera;

public class MouseLogic
{
	private boolean RMBdown;
	private Camera cam = Camera.getInstance();

	private int cursorStartX, cursorStartY;
	private final float zoomLimit = 15;
	private float currentZoom;

	private MousePicker picker = MousePicker.getInstance();

	private Entity itemHolder = new Entity(-1);

	private boolean shouldRenderHolder = false;

	private static final int LEFT_MOUSE_BUTTON = 0;
	private static final int RIGHT_MOUSE_BUTTON = 1;

	private static MouseLogic singleton = new MouseLogic();

	public static MouseLogic getInstance()
	{
		return singleton;
	}

	public void update()
	{
		picker.update();
		while (Mouse.next())
		{
			int mouseX = Mouse.getX(), mouseY = Mouse.getY();
			if (Mouse.getEventButtonState())
			{
				if (Mouse.getEventButton() == LEFT_MOUSE_BUTTON)
				{
					if (shouldRenderHolder)
					{
						EntityManager.getInstance().addEntity(itemHolder);
						shouldRenderHolder = false;
					}
				}
				if (Mouse.getEventButton() == RIGHT_MOUSE_BUTTON)
				{
					RMBdown = true;
					cursorStartX = mouseX;
					cursorStartY = mouseY;
				}
			}
			else
			{
				if (Mouse.getEventButton() == RIGHT_MOUSE_BUTTON)
				{
					RMBdown = false;
				}
			}
			int a = Mouse.getDWheel();
			if (a > 0)
			{
				if (currentZoom < zoomLimit)
				{
					double speed = 0.5f;
					cam.moveBy((float) (speed * Math.sin(cam.getYRotation())),
							(float) -(speed * Math.sin(90 - cam.getXRotation())),
							(float) -(speed * Math.cos(cam.getYRotation())));
					cam.rotateBy(-0.5f, 0, 0);
					currentZoom += speed;
					if (currentZoom > zoomLimit)
						currentZoom = zoomLimit;
				}
			}
			if (a < 0)
			{
				if (currentZoom > 0)
				{
					double speed = 0.5f;
					cam.moveBy((float) -(speed * Math.sin(cam.getYRotation())),
							(float) (speed * Math.sin(90 - cam.getXRotation())),
							(float) (speed * Math.cos(cam.getYRotation())));
					cam.rotateBy(0.5f, 0, 0);
					currentZoom -= speed;
					if (currentZoom < 0)
						currentZoom = 0;
				}
			}
			if (RMBdown)
			{
				float distanceX = (cursorStartX - mouseX) * 0.1f / (currentZoom / 5 + 1);
				float distanceY = (cursorStartY - mouseY) * 0.1f / (currentZoom / 5 + 1);
				cursorStartX = mouseX;
				cursorStartY = mouseY;
				float camYaw = cam.getYRotation();
				cam.getDistToLookPoint();
				float camYawH = cam.getYRotation() + (float) Math.toRadians(90);
				float dx = (float) Math.cos(camYaw) * distanceX;
				float dz = (float) Math.sin(camYaw) * distanceX;
				float dx1 = (float) Math.cos(camYawH) * distanceY;
				float dz1 = (float) Math.sin(camYawH) * distanceY;
				cam.moveBy(dx - dx1, 0, dz - dz1);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_1))
		{
			shouldRenderHolder = true;
			itemHolder.setAsset(TexturedModel.BENCH.getID());
		}
	}

	public boolean shouldRenderHolder()
	{
		return shouldRenderHolder;
	}

	public Entity getHolder()
	{
		return itemHolder;
	}
}
