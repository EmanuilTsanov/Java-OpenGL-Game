package opengl.java.entity;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.model.RawModel;

public class Entity
{
	protected int id;
	protected EntityBase base;

	protected Vector3f position = new Vector3f(0, 0, 0);
	protected Vector3f rotation = new Vector3f(0, 0, 0);

	protected float scale = 1;

	private static HashMap<EntityBase, ArrayList<Entity>> entities = new HashMap<EntityBase, ArrayList<Entity>>();

	public Entity(EntityBase base)
	{
		this.base = base;
		if (entities.get(base) == null)
		{
			ArrayList<Entity> batch = new ArrayList<Entity>();
			entities.put(base, batch);
		}
		this.id = entities.get(base).size();
		entities.get(base).add(this);
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public Entity setPosition(Vector3f position)
	{
		this.position = position;
		return this;
	}

	public void move(float x, float y, float z)
	{
		this.position.x += x;
		this.position.y += y;
		this.position.z += z;
	}

	public Vector3f getRotation()
	{
		return rotation;
	}

	public Entity setRotation(Vector3f rotation)
	{
		this.rotation = rotation;
		return this;
	}

	public void rotate(float x, float y, float z)
	{
		this.rotation.x += x;
		this.rotation.y += y;
		this.rotation.z += z;
	}

	public float getScale()
	{
		return scale;
	}

	public Entity setScale(float scale)
	{
		this.scale = scale;
		return this;
	}

	public int getID()
	{
		return id;
	}

	public static HashMap<EntityBase, ArrayList<Entity>> getEntities()
	{
		return entities;
	}
}
