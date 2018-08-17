package opengl.java.entity;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.model.TexturedModel;

public class Entity
{
	protected int uniqueID;
	protected int asset;

	protected Vector3f position = new Vector3f(0, 0, 0);
	protected Vector3f rotation = new Vector3f(0, 0, 0);

	protected float scale = 1;

	private static HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();

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
		System.out.println(EntityManager.getNextUniqueID());
		this.asset = asset;
		entities.put(uniqueID, this);
	}

	public Entity setup()
	{
		this.uniqueID = EntityManager.getNextUniqueID();
		color = EntityManager.getNextUniqueColor(uniqueID);
		entities.put(uniqueID, this);
		return this;
	}

	public Entity increasePosition(float x, float y, float z)
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

	public Entity setPosition(float x, float y, float z)
	{
		position = new Vector3f(x, y, z);
		return this;
	}

	public Entity setRotationInRadians(Vector3f radiansVec)
	{
		this.rotation = new Vector3f(radiansVec.x, radiansVec.y, radiansVec.z);
		return this;
	}

	public Entity setRotationInDegrees(Vector3f degreesVec)
	{
		this.rotation = new Vector3f((float) Math.toRadians(degreesVec.x), (float) Math.toRadians(degreesVec.y),
				(float) Math.toRadians(degreesVec.z));
		return this;
	}

	public void increaseRotation(float xRot, float yRot, float zRot)
	{
		this.rotation.x += Math.toRadians(xRot);
		this.rotation.y += Math.toRadians(yRot);
		this.rotation.z += Math.toRadians(zRot);
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
		return asset;
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

	public void rotate(float x, float y, float z)
	{
		rotation.x += Math.toRadians(x);
		rotation.y += Math.toRadians(y);
		rotation.z += Math.toRadians(z);
	}

	public Entity getCopy()
	{
		return new Entity(asset).setPosition(position).setRotationInRadians(rotation).setScale(scale).setup();
	}

	public static Entity getEntityByColor(Vector3f color)
	{
		int uniqueID = EntityManager.getUniqueIDByColor(color.x + "/" + color.y + "/" + color.z);
		if (uniqueID == -1)
			return null;
		Entity entity = entities.get(uniqueID);
		return entity;
	}
}
