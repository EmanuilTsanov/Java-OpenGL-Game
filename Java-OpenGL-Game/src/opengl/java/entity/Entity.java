package opengl.java.entity;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.management.EntityManager;
import opengl.java.model.TexturedModel;

public class Entity
{
	protected int uniqueID;
	protected int assetID;

	protected Vector3f position = new Vector3f(0, 0, 0);
	protected Vector3f rotation = new Vector3f(0, 0, 0);

	protected float scale = 1;

	private static HashMap<Integer, Integer> assetList = new HashMap<Integer, Integer>();

	private Vector3f color;

	public static Entity pineTree = new Entity(TexturedModel.PINE_TREE.getID());
	public static Entity bench = new Entity(TexturedModel.BENCH.getID());
	public static Entity table = new Entity(TexturedModel.TABLE.getID());
	public static Entity plate = new Entity(TexturedModel.PLATE.getID());
	public static Entity rock = new Entity(TexturedModel.ROCK.getID());
	public static Entity campfire = new Entity(TexturedModel.CAMPFIRE.getID());
	public static Entity mushroom = new Entity(TexturedModel.MUSHROOM1.getID());
	public static Entity mushroom1 = new Entity(TexturedModel.MUSHROOM2.getID());
	public static Entity grass = new Entity(TexturedModel.GRASS.getID());
	public static Entity christmasTree = new Entity(TexturedModel.CHRISTMAS_TREE.getID());
	public static Entity snowman = new Entity(TexturedModel.SNOWMAN.getID());
	public static Entity hut = new Entity(TexturedModel.HUT.getID());

	public Entity(int asset)
	{
		this.assetID = asset;
	}

	public Entity setup()
	{
		this.uniqueID = Maths.getNextUniqueID();
		assetList.put(uniqueID, assetID);
		color = Maths.getNextUniqueColor(uniqueID);
		return this;
	}

	public Entity move(float x, float y, float z)
	{
		this.position.x += x;
		this.position.y += y;
		this.position.z += z;
		return this;
	}

	public Entity setPosition(Vector3f position)
	{
		this.position = position;
		return this;
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

	public Entity setScale(float scale)
	{
		this.scale = scale;
		return this;
	}

	public int getID()
	{
		return uniqueID;
	}

	public int getAsset()
	{
		return assetID;
	}

	public void setAsset(int asset)
	{
		this.assetID = asset;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public Vector3f getColor()
	{
		return color;
	}

	public Vector3f getRotation()
	{
		return rotation;
	}

	public float getScale()
	{
		return scale;
	}

	public Entity getCopy()
	{
		return new Entity(assetID).setPosition(position).setRotation(rotation).setScale(scale).setup();
	}

	public static Entity getEntityByColor(Vector3f color)
	{
		int uniqueID = Maths.getUniqueIDByColor(color.x + "/" + color.y + "/" + color.z);
		if (uniqueID == -1)
			return null;
		Entity entity = EntityManager.getEntityHashMap().get(assetList.get(uniqueID)).get(uniqueID);
		return entity;
	}
}
