package opengl.java.interaction;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.render.GameRenderer;
import opengl.java.terrain.Terrain;
import opengl.java.terrain.TerrainGenerator;
import opengl.java.view.Camera;
import opengl.java.window.Window;

public class MouseController
{
	private float angle;

	private Entity entityHolder;

	private Vector2f cursorStartLocation = new Vector2f(0, 0);

	private MousePicker picker = MousePicker.getInstance();

	private static final int LEFT_MOUSE_BUTTON = 0;
	private static final int RIGHT_MOUSE_BUTTON = 1;
//	private static final int MIDDLE_MOUSE_BUTTON = 2;

	private static MouseController singleton = new MouseController();

	private boolean getEventButton(int button)
	{
		return Mouse.getEventButton() == button;
	}

	public void update()
	{
		if (Mouse.next())
		{
			if (Mouse.getEventButtonState())
			{
				if (getEventButton(LEFT_MOUSE_BUTTON))
				{
					cursorStartLocation.x = Mouse.getX();
					cursorStartLocation.y = Mouse.getY();
					Mouse.setGrabbed(true);
				}

				if (getEventButton(RIGHT_MOUSE_BUTTON))
				{
				}
			}
			else
			{
				if (getEventButton(LEFT_MOUSE_BUTTON))
				{
					Mouse.setGrabbed(false);
				}

				if (getEventButton(RIGHT_MOUSE_BUTTON))
				{
				}
			}
			if (Mouse.isButtonDown(LEFT_MOUSE_BUTTON) && Mouse.isGrabbed())
			{
				Camera cam = Camera.getInstance();
				float distanceX = (cursorStartLocation.x - Mouse.getX()) * 0.1f;
				float distanceY = (cursorStartLocation.y - Mouse.getY()) * 0.1f;
				float camYaw = cam.getYaw();
				float camYawH = cam.getYaw() + 90;
				float dx = (float) Math.cos(camYaw) * distanceX;
				float dz = (float) Math.sin(camYaw) * distanceX;
				float dx1 = (float) Math.cos(camYawH) * distanceY;
				float dz1 = (float) Math.sin(camYawH) * distanceY;
				cam.moveBy(dx - dx1, 0, dz - dz1);
				Mouse.setCursorPosition(Window.getWidth()/2, Window.getHeight()/2);
				cursorStartLocation.x = Mouse.getX();
				cursorStartLocation.y = Mouse.getY();
			}
			if (Mouse.isButtonDown(RIGHT_MOUSE_BUTTON))
			{
				
			}
		}
		if (entityHolder != null)
		{
			Vector3f vec = picker.getMapPosition();
			Vector2f vec1 = Terrain.getInstance().getCellPosition(vec.x + entityHolder.getAdditionalXArea(), vec.z + entityHolder.getAdditionalZArea());
			entityHolder.setPosition(new Vector3f((vec1.x + entityHolder.positionX()) * TerrainGenerator.getQuadSize(), 0f, (vec1.y + entityHolder.positionY()) * TerrainGenerator.getQuadSize()));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F1))
		{
			GameRenderer.getInstance().takeScreenshot();
		}
	}

	public void render()
	{
		if (entityHolder != null)
			GameRenderer.getInstance().renderEntity(entityHolder);
	}

	public static MouseController getInstance()
	{
		return singleton;
	}

	public Entity getEntityHolder()
	{
		return entityHolder;
	}
}
