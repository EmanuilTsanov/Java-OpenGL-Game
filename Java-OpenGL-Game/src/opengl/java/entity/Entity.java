package opengl.java.entity;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.management.SRCLoader;
import opengl.java.model.Model;
import opengl.java.terrain.TerrainGenerator;
import opengl.java.texture.ModelTexture;
import opengl.src.Resources;

public class Entity
{
	protected int id;
	protected int srcID;

	protected String name;

	protected String model;
	protected String texture;

	protected Vector3f position = new Vector3f(0, 0, 0);
	protected Vector3f rotation = new Vector3f(0, 0, 0);
	protected Vector2f areaRequired = new Vector2f(1, 1);

	protected float scale = 1;

	private static int nextEntityID = 0;

	private static HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();

	private Vector3f color;
	private static Vector3f globalColor = new Vector3f(0, 0, 0);
	private static HashMap<String, Integer> colorArray = new HashMap<String, Integer>();

	public static Entity pineTree = new Entity(0).setModel("treePine").setTexture("treePine");
	public static Entity bench = new Entity(1).setModel("bench").setTexture("bench");
	public static Entity table = new Entity(2).setModel("table").setTexture("table");
	public static Entity plate = new Entity(3).setModel("plate").setTexture("plate");
	public static Entity rock = new Entity(4).setModel("rock").setTexture("rock");
	public static Entity campfire = new Entity(5).setModel("campfire").setTexture("campfire");
	public static Entity mushroom = new Entity(6).setModel("mushroom").setTexture("mushroom");
	public static Entity mushroom1 = new Entity(7).setModel("mushroom").setTexture("mushroom1");
	public static Entity grass = new Entity(8).setModel("grass").setTexture("grass");
	public static Entity christmasTree = new Entity(9).setModel("christmas_tree").setTexture("christmas_tree");
	public static Entity snowman = new Entity(10).setModel("snowman").setTexture("snowman");
	public static Entity hut = new Entity(11).setModel("hut").setTexture("hut");

	public Entity(int srcID)
	{
		this.srcID = srcID;
		entities.put(srcID, this);
	}

	public Entity setup()
	{
		this.id = nextEntityID++;
		color = manageColor(globalColor);
		colorArray.put(color.x + "/" + color.y + "/" + color.z, id);
		entities.put(id, this);
		return this;
	}

	public Entity setName(String name)
	{
		this.name = name;
		return this;
	}

	public Entity setModel(String val)
	{
		Model model = SRCLoader.loadRawModel(val);
		Resources.addModel(srcID, model);
		return this;
	}

	public Entity setTexture(String val)
	{
		ModelTexture texture = SRCLoader.loadTexture(val);
		Resources.addTexture(srcID, texture);
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
		this.rotation = new Vector3f((float) Math.toRadians(degreesVec.x), (float) Math.toRadians(degreesVec.y), (float) Math.toRadians(degreesVec.z));
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

	public void setArea(Vector2f area)
	{
		this.areaRequired = area;
	}

	private Vector3f manageColor(Vector3f color)
	{
		int r = (int) color.x;
		int g = (int) color.y;
		int b = (int) color.z;
		Vector3f col = new Vector3f(r, g, b);
		if (b < 255)
		{
			color.z++;
		}
		else if (b == 255 && g != 255)
		{
			color.y++;
			color.z = 0;
		}
		else if (g == 255 && b == 255)
		{
			color.x++;
			color.y = color.z = 0;
		}
		return col;
	}

	public int getId()
	{
		return id;
	}

	public int getSrcID()
	{
		return srcID;
	}

	public String getName()
	{
		return name;
	}

	public String getModel()
	{
		return model;
	}

	public String getTexture()
	{
		return texture;
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

	public Vector2f getArea()
	{
		return areaRequired;
	}

	public float getAdditionalXArea()
	{
		return areaRequired.x % 2 == 0 ? TerrainGenerator.getQuadSize() / 2f : 0f;
	}

	public float getAdditionalZArea()
	{
		return areaRequired.y % 2 == 0 ? TerrainGenerator.getQuadSize() / 2f : 0f;
	}

	public float positionX()
	{
		return areaRequired.x % 2 != 0 ? areaRequired.x / 2 : 0f;
	}

	public float positionY()
	{
		return areaRequired.y % 2 != 0 ? areaRequired.y / 2 : 0f;
	}

	public void rotate(float x, float y, float z)
	{
		rotation.x += Math.toRadians(x);
		rotation.y += Math.toRadians(y);
		rotation.z += Math.toRadians(z);
	}

	public Entity getCopy()
	{
		return new Entity(srcID).setPosition(position).setRotationInRadians(rotation).setScale(scale).setup();
	}

	public static Entity getEntityByColor(Vector3f color)
	{
		Entity entity = entities.get(colorArray.get(color.x + "/" + color.y + "/" + color.z));
		return entity;
	}
}
