package opengl.java.interaction;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.management.EntityManager;
import opengl.java.model.TexturedModel;
import opengl.java.render.MainRenderer;
import opengl.java.terrain.Terrain;
import opengl.java.view.Camera;

public class MouseLogic
{
	private boolean RMBdown;
	private Camera cam = Camera.getInstance();

	private int cursorStartX, cursorStartY;

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

	public void update(Terrain terrain)
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
						EntityManager.addEntity(itemHolder);
						shouldRenderHolder = false;
					}
					else
					{
						Vector3f color = MainRenderer.pickColor(Mouse.getX(), Mouse.getY());
						Entity e = Entity.getEntityByColor(color);
						if (e != null)
						{
							itemHolder.setAsset(e.getAsset());
							Vector3f pos = e.getPosition();
							itemHolder.setPosition(new Vector3f(pos.x, terrain.getHeightOfTerrain(pos.x, pos.z), pos.z))
									.setRotation(e.getRotation());
							EntityManager.removeEntity(e);
							shouldRenderHolder = true;
						}
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
				float distanceX = (cursorStartX - mouseX) * 0.5f;
				float distanceY = (cursorStartY - mouseY) * 0.5f;
				cursorStartX = mouseX;
				cursorStartY = mouseY;
				float camYaw = (float) Math.toRadians(cam.getRotation().y);
				float camYawH = (float) Math.toRadians(cam.getRotation().y + 90);
				float dx = (float) Math.cos(camYaw) * distanceX;
				float dz = (float) Math.sin(camYaw) * distanceX;
				float dx1 = (float) Math.cos(camYawH) * distanceY;
				float dz1 = (float) Math.sin(camYawH) * distanceY;
				cam.move(dx - dx1, 0, dz - dz1);
			}
		}
		while (Keyboard.next())
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_1))
			{
				shouldRenderHolder = true;
				itemHolder.setAsset(TexturedModel.BENCH.getID());
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_2))
			{
				shouldRenderHolder = true;
				itemHolder.setAsset(TexturedModel.CAMPFIRE.getID());
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_3))
			{
				shouldRenderHolder = true;
				itemHolder.setAsset(TexturedModel.ROCK.getID());
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_4))
			{
				shouldRenderHolder = true;
				itemHolder.setAsset(TexturedModel.CHRISTMAS_TREE.getID());
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_5))
			{
				shouldRenderHolder = true;
				itemHolder.setAsset(TexturedModel.GRASS.getID());
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_6))
			{
				shouldRenderHolder = true;
				itemHolder.setAsset(TexturedModel.TABLE.getID());
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_7))
			{
				shouldRenderHolder = true;
				itemHolder.setAsset(TexturedModel.MUSHROOM1.getID());
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_8))
			{
				shouldRenderHolder = true;
				itemHolder.setAsset(TexturedModel.MUSHROOM2.getID());
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_9))
			{
				shouldRenderHolder = true;
				itemHolder.setAsset(TexturedModel.PINE_TREE.getID());
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_0))
			{
				shouldRenderHolder = true;
				itemHolder.setAsset(TexturedModel.PLATE.getID());
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_R))
			{
				itemHolder.rotate(0, 90, 0);
			}
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
