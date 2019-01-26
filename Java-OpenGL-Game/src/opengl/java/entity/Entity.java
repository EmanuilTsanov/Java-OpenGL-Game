package opengl.java.entity;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.maths.Maths;
import opengl.java.model.RawModel;
import opengl.java.texture.ModelTexture;

public class Entity
{
	protected int id;
	protected EntityBase base;

	protected Vector3f position = new Vector3f(0, 0, 0);
	protected Vector3f rotation = new Vector3f(0, 0, 0);

	protected float scale = 1;

	protected Vector3f color;

	private static HashMap<EntityBase, ArrayList<Entity>> entities = new HashMap<EntityBase, ArrayList<Entity>>();

	private static HashMap<String, Entity> colors = new HashMap<String, Entity>();

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
		color = Maths.getNextColor();
		colors.put(color.x + "-" + color.y + "-" + color.z, this);
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

	public Vector3f getColor()
	{
		return color;
	}

	public static HashMap<EntityBase, ArrayList<Entity>> getEntities()
	{
		return entities;
	}

	public RawModel getModel()
	{
		return base.getModel();
	}

	public ModelTexture getTexture()
	{
		return base.getTexture();
	}

	public Entity getEntityByColor(Vector3f color)
	{
		return colors.get(color.x + "-" + color.y + "-" + color.z);
	}
}
