package opengl.java.interaction;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.management.EntityManager;
import opengl.java.model.TexturedModel;
import opengl.java.render.GameRenderer;
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
						EntityManager.addEntity(itemHolder);
						shouldRenderHolder = false;
					}
					else
					{
						Vector3f color = GameRenderer.pickColor(Mouse.getX(), Mouse.getY());
						Entity e = Entity.getEntityByColor(color);
						if (e != null)
						{
							itemHolder.setAsset(e.getAsset());
							itemHolder.setPosition(e.getPosition()).setRotationInRadians(e.getRotation());
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
				itemHolder.increaseRotation(0, 90, 0);
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
