package opengl.java.entity;

import opengl.java.loader.ImageLoader;
import opengl.java.loader.OBJLoader;
import opengl.java.model.RawModel;

public class EntityBase
{
	private RawModel model;
	private int textureID;

	private float shineDamper = 1;
	private float reflectivity = 0;

	private boolean hasTransparency;
	private boolean hasFakeLighting;

	public static final EntityBase PINE_TREE = new EntityBase(OBJLoader.loadModel("pineTree"), ImageLoader.loadTexture("pineTree"));
	public static final EntityBase GRASS = new EntityBase(OBJLoader.loadModel("grass"), ImageLoader.loadTexture("tallgrass"));

	public EntityBase(RawModel model, int texture)
	{
		this.model = model;
		this.textureID = texture;
	}

	public RawModel getModel()
	{
		return model;
	}

	public int getTexture()
	{
		return textureID;
	}

	public float getShineDamper()
	{
		return shineDamper;
	}

	public EntityBase setShineDamper(float shineDamper)
	{
		this.shineDamper = shineDamper;
		return this;
	}

	public float getReflectivity()
	{
		return reflectivity;
	}

	public EntityBase setReflectivity(float reflectivity)
	{
		this.reflectivity = reflectivity;
		return this;
	}

	public boolean hasTransparency()
	{
		return hasTransparency;
	}

	public EntityBase setTransparency(boolean transparency)
	{
		this.hasTransparency = transparency;
		return this;
	}

	public boolean hasFakeLighting()
	{
		return hasFakeLighting;
	}

	public EntityBase setFakeLighting(boolean fakeLighting)
	{
		this.hasFakeLighting = fakeLighting;
		return this;
	}
}
