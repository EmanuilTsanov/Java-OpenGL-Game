package opengl.java.controls;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.entity.EntityManager;
import opengl.java.render.GameRenderer;
import opengl.java.terrain.Terrain;
import opengl.java.terrain.TerrainGenerator;
import opengl.java.view.Camera;
import opengl.java.window.Window;

public class MouseController
{
	private Vector2f pickLocation = new Vector2f(0, 0);

	private Entity entityHolder;

	private float angle;

	private MousePicker picker = MousePicker.getInstance();

	private static MouseController singleton = new MouseController();

	public void update()
	{
		picker.update();
		if (Mouse.next())
		{
			if (Mouse.getEventButton() == 0)
			{
				if (Mouse.getEventButtonState())
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
				else
				{
					System.out.println("Left button released");
				}
			}
			if (Mouse.getEventButton() == 1)
			{
				if (Mouse.getEventButtonState())
				{
					Mouse.setCursorPosition(Window.WIDTH / 2, Window.HEIGHT / 2);
					pickLocation.x = Mouse.getX();
					pickLocation.y = Mouse.getY();
					Mouse.setGrabbed(true);
				}
				else
				{
					Mouse.setGrabbed(false);
				}
			}
			if (Mouse.getEventButton() == 2)
			{
				if (Mouse.getEventButtonState())
				{
					if (entityHolder != null)
					{
						entityHolder.rotate(0, 90, 0);
						Vector2f a = entityHolder.getArea();
						Vector2f b = new Vector2f(a.y, a.x);
						entityHolder.setArea(b);
					}
				}
			}
		}
		if (Mouse.isButtonDown(1) && Mouse.isGrabbed())
		{
			Camera cam = Camera.getInstance();
			float distanceX = (pickLocation.x - Mouse.getX()) * 0.1f;
			float distanceY = (pickLocation.y - Mouse.getY()) * 0.1f;
			float camYaw = (float) Math.toRadians(cam.getYaw());
			float dx = (float) Math.cos(camYaw) * distanceX;
			float dz = (float) Math.sin(camYaw) * distanceX;
			float dx1 = (float) Math.cos(camYaw + 90) * -distanceY;
			float dz1 = (float) Math.sin(camYaw + 90) * -distanceY;
			cam.increasePosition(dx, 0, dz);
			cam.increasePosition(dx1, 0, dz1);
			Mouse.setCursorPosition(Window.WIDTH / 2, Window.HEIGHT / 2);
			pickLocation.x = Window.WIDTH / 2;
			pickLocation.y = Window.HEIGHT / 2;
		}
		if (Mouse.isButtonDown(2))
		{
			if (entityHolder == null)
			{
				float radius = (float) Camera.getInstance().getLookDistance();
				float dx = (float) (radius * Math.sin(Math.toRadians(angle))) * 0.1f;
				float dz = (float) (radius * Math.cos(Math.toRadians(angle))) * 0.1f;
				Camera.getInstance().increasePosition(-dx, 0, -dz);
				angle += 1f;
			}
		}
		if (entityHolder != null)
		{
			Vector3f vec = picker.getMapPosition();
			Vector2f vec1 = Terrain.getInstance().getCellPos(vec.x + entityHolder.getAdditionalXArea(), vec.z + entityHolder.getAdditionalZArea());
			entityHolder.setPosition(new Vector3f((vec1.x + entityHolder.positionX()) * TerrainGenerator.getQuadSize(), 0f, (vec1.y + entityHolder.positionY()) * TerrainGenerator.getQuadSize()));
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
