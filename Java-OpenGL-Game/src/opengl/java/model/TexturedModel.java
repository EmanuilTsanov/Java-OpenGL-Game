package opengl.java.model;

import java.util.HashMap;

import opengl.java.management.SRCLoader;
import opengl.java.texture.ModelTexture;

public class TexturedModel
{
	private int id;

	private RawModel model;
	private ModelTexture texture;

	private static HashMap<Integer, TexturedModel> models = new HashMap<Integer, TexturedModel>();

	public static final TexturedModel PINE_TREE = new TexturedModel(0, SRCLoader.loadModel("treePine"),
			SRCLoader.loadTexture("treePine"));
	public static final TexturedModel BENCH = new TexturedModel(1, SRCLoader.loadModel("bench"),
			SRCLoader.loadTexture("bench"));
	public static final TexturedModel TABLE = new TexturedModel(2, SRCLoader.loadModel("table"),
			SRCLoader.loadTexture("table"));
	public static final TexturedModel PLATE = new TexturedModel(3, SRCLoader.loadModel("plate"),
			SRCLoader.loadTexture("plate"));
	public static final TexturedModel ROCK = new TexturedModel(4, SRCLoader.loadModel("rock"),
			SRCLoader.loadTexture("rock"));
	public static final TexturedModel CAMPFIRE = new TexturedModel(5, SRCLoader.loadModel("campfire"),
			SRCLoader.loadTexture("campfire"));
	public static final TexturedModel MUSHROOM1 = new TexturedModel(6, SRCLoader.loadModel("mushroom"),
			SRCLoader.loadTexture("mushroom"));
	public static final TexturedModel MUSHROOM2 = new TexturedModel(7, SRCLoader.loadModel("mushroom"),
			SRCLoader.loadTexture("mushrooma"));
	public static final TexturedModel GRASS = new TexturedModel(8, SRCLoader.loadModel("grass"),
			SRCLoader.loadTexture("grass"));
	public static final TexturedModel CHRISTMAS_TREE = new TexturedModel(9, SRCLoader.loadModel("christmasTree"),
			SRCLoader.loadTexture("christmasTree"));
	public static final TexturedModel SNOWMAN = new TexturedModel(10, SRCLoader.loadModel("snowman"),
			SRCLoader.loadTexture("snowman"));
	public static final TexturedModel HUT = new TexturedModel(11, SRCLoader.loadModel("hut"),
			SRCLoader.loadTexture("hut"));
	public static final TexturedModel CAR = new TexturedModel(12, SRCLoader.loadModel("untitled"),
			SRCLoader.loadTexture("grass"));
	public static final TexturedModel PLAYER = new TexturedModel(13, SRCLoader.loadModel("player"),
			SRCLoader.loadTexture("grass"));

	public TexturedModel(int id, RawModel model, ModelTexture texture)
	{
		this.id = id;
		this.model = model;
		this.texture = texture;
		models.put(id, this);
	}

	public int getID()
	{
		return id;
	}

	public RawModel getRawModel()
	{
		return model;
	}

	public ModelTexture getTexture()
	{
		return texture;
	}

	public static TexturedModel getTexturedModel(int id)
	{
		return models.get(id);
	}

}
