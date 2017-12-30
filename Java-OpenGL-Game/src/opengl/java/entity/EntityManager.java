package opengl.java.entity;

import java.util.HashMap;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.logger.Logger;
import opengl.java.terrain.TerrainGenerator;

public class EntityManager
{
	public static Entity pineTree = new Entity(0, "Pine Tree", new Vector2f(2, 2), true).setModel("treePine").setTexture("treePine");
	public static Entity bench = new Entity(1, "Bench", new Vector2f(2, 1), true).setModel("bench").setTexture("bench");
	public static Entity table = new Entity(2, "Table", new Vector2f(2, 1), true).setModel("table").setTexture("table");
	public static Entity plate = new Entity(3, "Plate", new Vector2f(1, 1), true).setModel("plate").setTexture("plate");
	public static Entity rock = new Entity(4, "Rock", new Vector2f(1, 1), true).setModel("rock").setTexture("rock");
	public static Entity campfire = new Entity(5, "Campfire", new Vector2f(1, 1), true).setModel("campfire").setTexture("campfire");
	public static Entity mushroom = new Entity(6, "Mushroom", new Vector2f(1, 1), true).setModel("mushroom").setTexture("mushroom");
	public static Entity mushroom1 = new Entity(7, "Brown Mushroom", new Vector2f(1, 1), true).setModel("mushroom").setTexture("mushroom1");
	public static Entity grass = new Entity(8, "Grass", new Vector2f(1, 1), true).setModel("grass").setTexture("grass");
	public static Entity christmasTree = new Entity(9, "Christmas Tree", new Vector2f(2, 2), true).setModel("christmas_tree").setTexture("christmas_tree");
	public static Entity snowman = new Entity(10, "Snowman", new Vector2f(2, 2), true).setModel("snowman").setTexture("snowman");

	private static final int TRY_LIMIT = 50;

	private Random rand = new Random();
	
	private HashMap<Integer, HashMap<Integer, Entity>> entities;

	public EntityManager()
	{
		entities = new HashMap<Integer, HashMap<Integer, Entity>>();
	}

	public HashMap<Integer, HashMap<Integer, Entity>> loadEntities()
	{
		addEntities(pineTree, 100, true);
		addEntities(bench, 100, true);
		addEntities(christmasTree, 100, true);
		addEntities(snowman, 100, true);
		addEntities(table, 10, true);
		addEntities(campfire, 25, true);
		addEntities(grass, 100, true);

		return entities;
	}

	public boolean addEntity(Entity entity)
	{
		if (entities.get(entity.getId()) == null)
		{
			HashMap<Integer, Entity> batch = new HashMap<Integer, Entity>();
			batch.put(entity.getUniqueID(), entity);
			entities.put(entity.getId(), batch);
			return true;
		}
		else
		{
			entities.get(entity.getId()).put(entity.getUniqueID(), entity);
			return true;
		}
	}

	public void addEntities(Entity entity, int count, boolean randRot)
	{
		int successE = 0;
		for (int i = 0; i < count; i++)
		{
			Entity e = entity.getCopy(false);

			e.setPosition(TerrainGenerator.genRandTerrainPos(), 0, TerrainGenerator.genRandTerrainPos());

			if (randRot)
			{
				e.setRotationInRadians(new Vector3f(0, rand.nextFloat() * 180, 0));
			}
			inner: for (int j = 0; j < TRY_LIMIT; j++)
			{
				boolean a = addEntity(e);
				if (a)
				{
					successE++;
					break inner;
				}
				else
				{
					e.setPosition(TerrainGenerator.genRandTerrainPos(), 0, TerrainGenerator.genRandTerrainPos());
				}
				if (j == TRY_LIMIT - 1)
				{
					Logger.log("Try to add limit reached. Entity of type '" + entity.getName() + "' wasn't added on the map.");
				}
			}
		}
		Logger.log(successE + " entities of type '" + entity.getName() + "' were added successfully!");
	}
}
