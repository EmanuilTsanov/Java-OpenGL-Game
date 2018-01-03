package opengl.java.entity;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.management.FileManager;
import opengl.java.material.Material;
import opengl.java.model.RawModel;
import opengl.java.terrain.TerrainGenerator;
import opengl.java.texture.BaseTexture;

public class Entity
{
	protected int id;
	protected int uniqueID;

	protected String name;

	protected String model;
	protected String texture;

	protected Material material = Material.defaultMaterial;

	private static int nextEntityID = 0;

	protected Vector3f position = new Vector3f(0, 0, 0);
	protected Vector3f rotation = new Vector3f(0, 0, 0);

	protected float scale = 1;

	protected Vector2f areaRequired = new Vector2f(0, 0);

	private static HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();
	private static HashMap<Integer, RawModel> models = new HashMap<Integer, RawModel>();
	private static HashMap<Integer, BaseTexture> textures = new HashMap<Integer, BaseTexture>();

	private Vector3f color;

	private static Vector3f globalColor = new Vector3f(0, 0, 0);

	private static HashMap<String, Integer> colorArray = new HashMap<String, Integer>();

	public static Entity pineTree = new Entity(0, "Pine Tree", new Vector2f(2, 2)).setModel("treePine").setTexture("treePine");
	public static Entity bench = new Entity(1, "Bench", new Vector2f(2, 1)).setModel("bench").setTexture("bench");
	public static Entity table = new Entity(2, "Table", new Vector2f(2, 1)).setModel("table").setTexture("table");
	public static Entity plate = new Entity(3, "Plate", new Vector2f(1, 1)).setModel("plate").setTexture("plate");
	public static Entity rock = new Entity(4, "Rock", new Vector2f(1, 1)).setModel("rock").setTexture("rock");
	public static Entity campfire = new Entity(5, "Campfire", new Vector2f(1, 1)).setModel("campfire").setTexture("campfire");
	public static Entity mushroom = new Entity(6, "Mushroom", new Vector2f(1, 1)).setModel("mushroom").setTexture("mushroom");
	public static Entity mushroom1 = new Entity(7, "Brown Mushroom", new Vector2f(1, 1)).setModel("mushroom").setTexture("mushroom1");
	public static Entity grass = new Entity(8, "Grass", new Vector2f(1, 1)).setModel("grass").setTexture("grass");
	public static Entity christmasTree = new Entity(9, "Christmas Tree", new Vector2f(40, 40)).setModel("christmas_tree").setTexture("christmas_tree");
	public static Entity snowman = new Entity(10, "Snowman", new Vector2f(2, 2)).setModel("snowman").setTexture("snowman");

	public Entity(int id, String name, Vector2f areaRequired)
	{
		this.id = id;
		this.name = name;
		this.areaRequired = areaRequired;
	}

	public Entity setup()
	{
		this.uniqueID = nextEntityID++;
		color = manageColor(globalColor);
		colorArray.put(color.x + "" + color.y + "" + color.z, uniqueID);
		entities.put(uniqueID, this);
		return this;
	}

	/**
	 * Sets the display name for this entity.
	 */
	public Entity setName(String name)
	{
		this.name = name;
		return this;
	}

	/**
	 * Sets the model file name to be loaded from the assets later on.
	 */
	public Entity setModel(String val)
	{
		RawModel model = FileManager.loadRawModel(val);
		models.put(id, model);
		return this;
	}

	/**
	 * Sets the texture file name to be loaded from the assets later on.
	 */
	public Entity setTexture(String val)
	{
		BaseTexture texture = FileManager.loadTexture(val);
		textures.put(id, texture);
		return this;
	}

	public Entity increasePosition(float x, float y, float z)
	{
		this.position.x += x;
		this.position.y += y;
		this.position.z += z;
		return this;
	}

	/**
	 * Sets the ingame position for this entity.
	 * 
	 * @param position
	 *            - the position represented by a three dimentional vector
	 */
	public Entity setPosition(Vector3f position)
	{
		this.position = position;
		return this;
	}

	/**
	 * Sets the ingame position for this entity.
	 */
	public Entity setPosition(float x, float y, float z)
	{
		position = new Vector3f(x, y, z);
		return this;
	}

	/**
	 * Sets the ingame rotation for this entity.
	 * 
	 * @param rotation
	 *            - the rotation in radians represented by a three dimentional
	 *            vector
	 */
	public Entity setRotationInRadians(Vector3f radiansVec)
	{
		this.rotation = new Vector3f(radiansVec.x, radiansVec.y, radiansVec.z);
		return this;
	}

	/**
	 * Sets the ingame rotation for this entity.
	 * 
	 * @param rotation
	 *            - the rotation in radians represented by a three dimentional
	 *            vector
	 */
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

	/**
	 * Sets the ingame scale for this entity.
	 */
	public Entity setScale(float scale)
	{
		this.scale = scale;
		return this;
	}

	/**
	 * Specifies the required area on the map for this entity.
	 * 
	 * @param area
	 *            - width and height in terrain blocks
	 */

	public void setArea(Vector2f area)
	{
		this.areaRequired = area;
	}

	public Entity setMaterial(Material mat)
	{
		this.material = mat;
		return this;
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

	public Material getMaterial()
	{
		return material;
	}

	public int getId()
	{
		return id;
	}

	public int getUniqueID()
	{
		return uniqueID;
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
	
	public float getAdditionalXArea() {
		return areaRequired.x % 2 == 0 ? TerrainGenerator.getQuadSize()/4f : 0f;
	}
	
	public float getAdditionalZArea() {
		return areaRequired.y % 2 == 0 ? TerrainGenerator.getQuadSize()/4f : 0f;
	}

	public Entity getCopy()
	{
		return new Entity(id, name, areaRequired).setPosition(position).setRotationInRadians(rotation).setScale(scale).setMaterial(material).setup();
	}

	public static RawModel getModel(int id)
	{
		return models.get(id);
	}

	public static BaseTexture getTexture(int id)
	{
		return textures.get(id);
	}

	public static Entity getEntityByColor(Vector3f color)
	{
		Entity entity = entities.get(colorArray.get(color.x + "" + color.y + "" + color.z));
		return entity;
	}
}
