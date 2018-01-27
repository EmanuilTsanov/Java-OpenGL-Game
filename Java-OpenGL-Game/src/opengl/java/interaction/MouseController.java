package opengl.java.interaction;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.management.EntityManager;
import opengl.java.render.GameRenderer;
import opengl.java.terrain.Terrain;
import opengl.java.terrain.TerrainGenerator;
import opengl.java.view.Camera;
import opengl.java.window.Window;

public class MouseController
{
	private float angle;

	private Entity entityHolder;

	private Vector2f pickLocation = new Vector2f(0, 0);

	private MousePicker picker = MousePicker.getInstance();

	private static final int LEFT_MOUSE_BUTTON = 0;
	private static final int RIGHT_MOUSE_BUTTON = 1;
	private static final int MIDDLE_MOUSE_BUTTON = 2;

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
					directEntity();
				}

				if (getEventButton(RIGHT_MOUSE_BUTTON))
				{
					moveCursor();
					Mouse.setGrabbed(true);
				}
				if (getEventButton(MIDDLE_MOUSE_BUTTON))
				{
					rotateEntity();
				}
			}
			else
			{
				if (getEventButton(RIGHT_MOUSE_BUTTON))
				{
					Mouse.setGrabbed(false);
				}
			}
			if (Mouse.isButtonDown(1) && Mouse.isGrabbed())
			{
				Camera cam = Camera.getInstance();
				float distanceX = (pickLocation.x - Mouse.getX()) * 0.1f;
				float distanceY = (pickLocation.y - Mouse.getY()) * 0.1f;
				float camYaw = cam.getYaw();
				float camYawH = cam.getYaw() + 90;
				float dx = (float) Math.cos(camYaw) * distanceX;
				float dz = (float) Math.sin(camYaw) * distanceX;
				float dx1 = (float) Math.cos(camYawH) * distanceY;
				float dz1 = (float) Math.sin(camYawH) * distanceY;
				cam.moveBy(dx - dx1, 0, dz - dz1);
				moveCursor();
			}
			if (Mouse.isButtonDown(2))
			{
				if (entityHolder == null)
				{
					float radius = (float) Camera.getInstance().getDistToLookPoint();
					float dx = (float) (radius * Math.sin(Math.toRadians(angle))) * 0.1f;
					float dz = (float) (radius * Math.cos(Math.toRadians(angle))) * 0.1f;
					Camera.getInstance().moveBy(-dx, 0, -dz);
					angle += 1f;
				}
			}
		}
		if (entityHolder != null)
		{
			Vector3f vec = picker.getMapPosition();
			Vector2f vec1 = Terrain.getInstance().getCellPosition(vec.x + entityHolder.getAdditionalXArea(), vec.z + entityHolder.getAdditionalZArea());
			entityHolder.setPosition(new Vector3f((vec1.x + entityHolder.positionX()) * TerrainGenerator.getQuadSize(), 0f, (vec1.y + entityHolder.positionY()) * TerrainGenerator.getQuadSize()));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F1)) {
			GameRenderer.getInstance().takeScreenshot();
		}
	}

	public void directEntity()
	{
		if (entityHolder == null)
		{
			Vector3f color = GameRenderer.getInstance().pickColor(Mouse.getX(), Mouse.getY());
			Entity e = Entity.getEntityByColor(color);
			if (e != null)
			{
				entityHolder = e.getCopy();
				EntityManager.getInstance().removeEntity(e);
			}
		}
		else
		{
			EntityManager.getInstance().addEntity(entityHolder);
			entityHolder = null;
		}
	}

	public void rotateEntity()
	{
		if (entityHolder != null)
		{
			entityHolder.rotate(0, 90, 0);
			Vector2f a = entityHolder.getArea();
			Vector2f b = new Vector2f(a.y, a.x);
			entityHolder.setArea(b);
		}
	}

	public void moveCursor()
	{
		Mouse.setCursorPosition(Window.getInstance().getWidth() / 2, Window.getInstance().getHeight() / 2);
		pickLocation.x = Mouse.getX();
		pickLocation.y = Mouse.getY();
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
