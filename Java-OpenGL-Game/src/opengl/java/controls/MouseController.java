package opengl.java.controls;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.MousePicker;
import opengl.java.entity.Entity;
import opengl.java.render.GameRenderer;
import opengl.java.view.Camera;
import opengl.java.window.Window;

public class MouseController
{
	private Camera cam;
	private MousePicker mouseP;
	private GameRenderer renderer;

	private Vector2f pickLocation = new Vector2f(0, 0);

	private Entity entityHolder;

	boolean picked;

	public MouseController(GameRenderer renderer, Camera cam, MousePicker mouse)
	{
		this.cam = cam;
		this.mouseP = mouse;
		this.renderer = renderer;
	}

	public void update()
	{
		if (Mouse.isButtonDown(0))
		{
			Vector3f color = renderer.pickColor(Mouse.getX(), Mouse.getY());
			entityHolder = Entity.getEntityByColor(color).getFullCopy(false);
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
			float distanceX = (pickLocation.x - Mouse.getX()) * 0.1f;
			float distanceY = (pickLocation.y - Mouse.getY()) * 0.1f;
			float camYaw = cam.getYaw();
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
		}
	}
}
