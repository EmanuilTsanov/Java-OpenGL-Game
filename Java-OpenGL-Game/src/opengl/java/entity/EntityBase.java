package opengl.java.entity;

import opengl.java.management.SRCLoader;
import opengl.java.model.RawModel;
import opengl.java.texture.ModelTexture;

public class EntityBase
{
	private RawModel model;
	private ModelTexture texture;

	protected boolean backfaceCulling;

	public static final EntityBase PINE_TREE = new EntityBase("pineTree", "pineTree");
	public static final EntityBase GRASS = new EntityBase("grass", "grass").setBackfaceCulling(false);
	public static final EntityBase MUSHROOM = new EntityBase("mushroom", "mushroom");

	public EntityBase(String model, String texture)
	{
		this.model = SRCLoader.getModel(model);
		this.texture = SRCLoader.getTexture(texture);
		backfaceCulling = true;
	}

	public RawModel getModel()
	{
		return model;
	}

	public ModelTexture getTexture()
	{
		return texture;
	}

	public EntityBase setBackfaceCulling(boolean b)
	{
		backfaceCulling = b;
		return this;
	}

	public boolean isCullingAvailable()
	{
		return backfaceCulling;
	}
}
