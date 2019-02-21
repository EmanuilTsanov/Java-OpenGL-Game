package opengl.java.entity;

import opengl.java.management.Assets;
import opengl.java.model.RawModel;
import opengl.java.texture.ModelTexture;

public class EntityBase
{
	private RawModel model;
	private ModelTexture texture;
	
	private float shineDamper = 1;
	private float reflectivity = 0;
	
	private boolean hasTransparency;
	private boolean hasFakeLighting;

	public static final EntityBase PINE_TREE = new EntityBase("pineTree", "pineTree");
	public static final EntityBase GRASS = new EntityBase("grass", "tallgrass").setTransparency(true).setFakeLighting(true);
	public static final EntityBase MUSHROOM = new EntityBase("mushroom", "mushroom");

	public EntityBase(String model, String texture)
	{
		this.model = Assets.getModel(model);
		this.texture = Assets.getTexture(texture);
	}

	public RawModel getModel()
	{
		return model;
	}

	public ModelTexture getTexture()
	{
		return texture;
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
