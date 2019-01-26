package opengl.java.entity;

import opengl.java.management.SRCLoader;
import opengl.java.model.RawModel;
import opengl.java.texture.ModelTexture;

public class EntityBase
{
	private RawModel model;
	private ModelTexture texture;

	public static final EntityBase PINE_TREE = new EntityBase("pineTree", "pineTree");
	public static final EntityBase GRASS = new EntityBase("grass", "grass");
	public static final EntityBase MUSHROOM = new EntityBase("mushroom", "mushroom");

	public EntityBase(String model, String texture)
	{
		this.model = SRCLoader.getModel(model);
		this.texture = SRCLoader.getTexture(texture);
	}

	public RawModel getModel()
	{
		return model;
	}

	public ModelTexture getTexture()
	{
		return texture;
	}
}
