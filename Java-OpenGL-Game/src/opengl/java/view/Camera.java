package opengl.java.view;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.render.MainRenderer;
import opengl.java.window.Window;

public class Camera
{
	private Vector3f position;
	private float pitch;
	private float yaw;
	private float roll;

	private float angle;

	private boolean mouseBtn0;
	Vector2f lPos;
	Vector2f dist;

	boolean picked;

	private boolean locked;

	private Vector2f pickLocation = new Vector2f(0, 0);

	private Entity entityHolder;

	public Camera(Vector3f position, float pitch, float yaw, float roll)
	{
		this.position = position;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
		lPos = getLookPosition();
		dist = new Vector2f(this.position.x - lPos.x, this.position.y - lPos.y);
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public float getPitch()
	{
		return (float) Math.toRadians(pitch);
	}

	public float getYaw()
	{
		return (float) Math.toRadians(yaw);
	}

	public float getRoll()
	{
		return (float) Math.toRadians(roll);
	}

	public void control(MainRenderer renderer)
	{
		if (Mouse.isButtonDown(1))
		{
			if (entityHolder == null)
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
				float dx = (float) Math.cos(Math.toRadians(yaw)) * distanceX;
				float dz = (float) Math.sin(Math.toRadians(yaw)) * distanceX;
				float dx1 = (float) Math.cos(Math.toRadians(yaw + 90)) * -distanceY;
				float dz1 = (float) Math.sin(Math.toRadians(yaw + 90)) * -distanceY;
				position.x += dx += dx1;
				position.z += dz += dz1;
				Mouse.setCursorPosition(Window.WIDTH / 2, Window.HEIGHT / 2);
				pickLocation.x = Mouse.getX();
				pickLocation.y = Mouse.getY();
			}
			else
			{
				entityHolder.increaseRotation(0, 1, 0);
			}
		}
		else if (Mouse.isButtonDown(2))
		{
			if (!picked)
			{
				pickLocation.x = Mouse.getX();
				picked = true;
			}
			System.out.println(dist);
			float dx = (float) Math.cos(Math.toRadians(angle)) * dist.length();
			float dz = (float) Math.sin(Math.toRadians(angle)) * dist.length();
			position.x += dx;
			position.z += dz;
			angle+=1f;

		}
		else
		{
			picked = false;
			Mouse.setGrabbed(false);
		}
		if (Mouse.isButtonDown(0) && !mouseBtn0)
		{
			if (entityHolder == null)
			{
				Vector3f vec = renderer.pickColor(Mouse.getX(), Mouse.getY());
				Entity entity = Entity.getEntityByColor(vec);
				if (entity != null)
				{
					entityHolder = entity;
					//TODO
					List<Entity> ents = MainRenderer.entities.get(entity.getId());
					for (int i = 0; i < ents.size(); i++)
					{
						if (ents.get(i).getUniqueID() == entity.getUniqueID())
						{
							ents.remove(i);
							break;
						}
					}
				}
			}
			else
			{
				if(!renderer.getTerrain().isOccupied(entityHolder)) {
					renderer.getTerrain().setOccupied(entityHolder);
				MainRenderer.entities.get(entityHolder.getId()).add(entityHolder.getFullCopy(false));
				entityHolder = null;
				}
			}
		}
		mouseBtn0 = Mouse.isButtonDown(0);
	}

	public Entity getEntityHolder()
	{
		return entityHolder;
	}

	public void move(Vector3f position, Vector3f rotation)
	{
		this.position = position;
		if (!locked)
		{
			this.pitch = rotation.x;
			this.yaw = rotation.y;
			this.roll = rotation.z;
		}
	}

	public Vector2f getLookPosition()
	{
		float beta = 90 - pitch;
		float radius = 2 * (float) (position.y / Math.sin(Math.toRadians(beta)) * Math.sin(Math.toRadians(pitch)));
		float dx = (float) Math.cos(Math.toRadians(yaw + 270)) * radius;
		float dz = (float) Math.sin(Math.toRadians(yaw + 270)) * radius;
		return new Vector2f(position.x + dx, position.z + dz);
	}
}
