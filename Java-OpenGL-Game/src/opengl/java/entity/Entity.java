package opengl.java.entity;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.management.FileManager;
import opengl.java.material.Material;
import opengl.java.model.RawModel;
import opengl.java.texture.BaseTexture;

public class Entity
{
	protected int id;
	protected int uniqueID;

	protected String name;

	protected String model;
	protected String texture;

	protected int materialID = 0;

	private static int nextEntityID = 0;

	protected Vector3f position = new Vector3f(0, 0, 0);
	protected Vector3f rotation = new Vector3f(0, 0, 0);

	protected Vector2f areaRequired = new Vector2f(0, 0);

	protected float scale = 1;

	private static HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();
	private static HashMap<Integer, RawModel> models = new HashMap<Integer, RawModel>(); // Stores entity models (instead of having multiple copies of the same model, we use only one to render multiple entities)
	private static HashMap<Integer, BaseTexture> textures = new HashMap<Integer, BaseTexture>(); // Stores entity textures (instead of having multiple copies of the same texture, we use only one to render multiple entities)

	private Vector3f color;

	private static Vector3f globalColor = new Vector3f(0, 0, 0);

	private static HashMap<String, Integer> colorArray = new HashMap<String, Integer>();

	public Entity(int id, String name, Vector2f areaRequired, boolean staticEntity)
	{
		this.id = id;
		this.name = name;
		this.areaRequired = areaRequired;
		if (!staticEntity)
		{
			this.uniqueID = nextEntityID++;
			color = manageColor(globalColor);
			colorArray.put(color.x + "" + color.y + "" + color.z, uniqueID);
			entities.put(uniqueID, this);
		}
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

	public Material getMaterial()
	{
		return Material.getMaterial(materialID);
	}

	public Entity setMaterial(Material material)
	{
		materialID = material.getID();
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
	public Entity getCopy(boolean staticEntity)
	{
		return new Entity(id, name, areaRequired, staticEntity).setPosition(position).setRotationInRadians(rotation).setScale(scale);
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
