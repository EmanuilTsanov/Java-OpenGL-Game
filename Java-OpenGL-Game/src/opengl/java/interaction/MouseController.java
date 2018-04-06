package opengl.java.interaction;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.loader.MapLoader;
import opengl.java.management.EntityManager;
import opengl.java.render.GameRenderer;
import opengl.java.terrain.Terrain;
import opengl.java.terrain.TerrainGenerator;
import opengl.java.view.Camera;

public class MouseController
{
	private Entity entityHolder;
	private boolean RMBdown;
	Camera cam = Camera.getInstance();

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
		while (Mouse.next())
		{
			if (Mouse.getEventButtonState())
			{
				if (Mouse.getEventButton() == LEFT_MOUSE_BUTTON)
				{
					if (entityHolder == null)
					{
						Entity e = Entity.getEntityByColor(GameRenderer.getInstance().pickColor(mouseX, mouseY));
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
			if (RMBdown)
			{
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
		}
		if (entityHolder != null)
		{
			Vector3f vec = picker.getMapPosition();
			Vector2f vec1 = Terrain.getInstance().getCellPosition(vec.x + entityHolder.getAdditionalXArea(), vec.z + entityHolder.getAdditionalZArea());
			entityHolder.setPosition(new Vector3f((vec1.x + entityHolder.positionX()) * TerrainGenerator.getQuadSize(), 0f, (vec1.y + entityHolder.positionY()) * TerrainGenerator.getQuadSize()));
		}
		while (Keyboard.next())
			if (Keyboard.getEventKeyState())
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_R)
				{
					if (entityHolder != null)
						entityHolder.rotate(0, 90, 0);
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_1)
				{
					entityHolder = Entity.bench.getCopy();
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_2)
				{
					entityHolder = Entity.campfire.getCopy();
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_3)
				{
					entityHolder = Entity.christmasTree.getCopy();
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_4)
				{
					entityHolder = Entity.grass.getCopy();
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_5)
				{
					entityHolder = Entity.hut.getCopy();
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_6)
				{
					entityHolder = Entity.mushroom.getCopy();
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_7)
				{
					entityHolder = Entity.mushroom1.getCopy();
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_8)
				{
					entityHolder = Entity.pineTree.getCopy();
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_9)
				{
					entityHolder = Entity.rock.getCopy();
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_0)
				{
					entityHolder = Entity.snowman.getCopy();
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_T)
				{
					entityHolder = Entity.table.getCopy();
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_S)
				{
					MapLoader.saveMap("new_map", EntityManager.getInstance().getEntityHashMap());
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_D)
				{
					if (entityHolder != null)
						entityHolder = null;
				}
			}
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
