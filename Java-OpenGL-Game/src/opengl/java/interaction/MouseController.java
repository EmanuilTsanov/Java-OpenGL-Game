package opengl.java.interaction;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.render.GameRenderer;
import opengl.java.terrain.Terrain;
import opengl.java.terrain.TerrainGenerator;
import opengl.java.view.Camera;

public class MouseController
{
	private Entity entityHolder;
	private boolean rightMBDown;

	private int cursorStartX, cursorStartY;

	private MousePicker picker = MousePicker.getInstance();

	private static final int LEFT_MOUSE_BUTTON = 0;
	private static final int RIGHT_MOUSE_BUTTON = 1;

	private static MouseController singleton = new MouseController();

	public static MouseController getInstance()
	{
		return singleton;
	}

	public void update()
	{
		int mouseX = Mouse.getX(), mouseY = Mouse.getY();
		if (Mouse.next())
		{
			if (Mouse.getEventButtonState())
			{
				if (getEventButton(LEFT_MOUSE_BUTTON))
				{
					GameRenderer.getInstance().pickColor(mouseX, mouseY);
				}

				if (getEventButton(RIGHT_MOUSE_BUTTON))
				{
					rightMBDown = true;
					cursorStartX = mouseX;
					cursorStartY = mouseY;
				}
			}
			else
			{
				if (getEventButton(LEFT_MOUSE_BUTTON))
				{
				}

				if (getEventButton(RIGHT_MOUSE_BUTTON))
				{
					rightMBDown = false;
				}
			}
			if (rightMBDown)
			{
				Camera cam = Camera.getInstance();
				float distanceX = (cursorStartX - mouseX) * 0.1f;
				float distanceY = (cursorStartY - mouseY) * 0.1f;
				cursorStartX = mouseX;
				cursorStartY = mouseY;
				float camYaw = cam.getYaw();
				float camYawH = cam.getYaw() + (float) Math.toRadians(90);
				float dx = (float) Math.cos(camYaw) * distanceX;
				float dz = (float) Math.sin(camYaw) * distanceX;
				float dx1 = (float) Math.cos(camYawH) * distanceY;
				float dz1 = (float) Math.sin(camYawH) * distanceY;
				cam.moveBy(dx - dx1, 0, dz - dz1);
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
	}

	private boolean getEventButton(int button)
	{
		return Mouse.getEventButton() == button;
	}

	public Entity getEntityHolder()
	{
		return entityHolder;
	}

	public void render()
	{
		if (entityHolder != null)
			GameRenderer.getInstance().renderEntity(entityHolder);
	}
}
