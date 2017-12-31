package opengl.java.controls;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.entity.EntityManager;
import opengl.java.render.GameRenderer;
import opengl.java.view.Camera;
import opengl.java.window.Window;

public class MouseController
{
	private Vector2f pickLocation = new Vector2f(0, 0);

	private Entity entityHolder;

	private boolean picked;
	
	private boolean mouseButton0=true;

	private MousePicker picker = MousePicker.getInstance();

	private static MouseController singleton = new MouseController();

	public void update()
	{
		picker.update();
		if (Mouse.isButtonDown(0) && mouseButton0)
		{
			if (entityHolder == null)
			{
				Vector3f color = GameRenderer.getInstance().pickColor(Mouse.getX(), Mouse.getY());
				Entity e = Entity.getEntityByColor(color);
				entityHolder = e.getCopy();
				EntityManager.getInstance().removeEntity(e);
				mouseButton0 = false;
			}
			else
			{EntityManager.getInstance().addEntity(entityHolder);
			entityHolder = null;
			}
		}
		else if (Mouse.isButtonDown(1))
		{
			if (!picked)
			{
				pickLocation.x = Mouse.getX();
				pickLocation.y = Mouse.getY();
				picked = true;
			}
			Mouse.setGrabbed(true);
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
			pickLocation.x = Mouse.getX();
			pickLocation.y = Mouse.getY();
		}
		else
		{
			Mouse.setGrabbed(false);
			picked = false;
			mouseButton0 = true;
		}
		if(entityHolder!=null) {
			entityHolder.setPosition(picker.getMapPosition());
		}
	}
	
	public static MouseController getInstance() {
		return singleton;
	}
	
	public Entity getEntityHolder() {
		return entityHolder;
	}
}
