package opengl.java.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.logger.Logger;
import opengl.java.terrain.TerrainGenerator;

public class EntityManager
{
	private static final int TRIES_TO_ADD_LIMIT = 50;

	private Random rand = new Random();

	public static Entity pineTree = new Entity(0, "Pine Tree", true).setModel("treePine").setTexture("treePine");
	public static Entity bench = new Entity(1, "Bench", true).setModel("bench").setTexture("bench");
	public static Entity table = new Entity(2, "Table", true).setModel("table").setTexture("table");
	public static Entity plate = new Entity(3, "Plate", true).setModel("plate").setTexture("plate");
	public static Entity rock = new Entity(4, "Rock", true).setModel("rock").setTexture("rock");
	public static Entity campfire = new Entity(5, "Campfire", true).setModel("campfire").setTexture("campfire");
	public static Entity mushroom = new Entity(6, "Mushroom", true).setModel("mushroom").setTexture("mushroom");
	public static Entity mushroom1 = new Entity(7, "Brown Mushroom", true).setModel("mushroom").setTexture("mushroom1");
	public static Entity grass = new Entity(8, "Grass", true).setModel("grass").setTexture("grass");
	public static Entity christmasTree = new Entity(9, "Christmas Tree", true).setModel("christmas_tree").setTexture("christmas_tree");
	public static Entity snowman = new Entity(10, "Snowman", true).setModel("snowman").setTexture("snowman");

	private HashMap<Integer, List<Entity>> entities;

	public EntityManager()
	{
		entities = new HashMap<Integer, List<Entity>>();
	}

	public HashMap<Integer, List<Entity>> loadEntities()
	{
		addEntities(pineTree, 100, true, 1f, 1f);
		addEntities(bench, 100, true, 1f, 1f);
		addEntities(christmasTree, 100, true, 1f, 1f);
		addEntities(snowman, 100, true, 1f, 1f);
		addEntities(table, 10, true, 1f, 1f);
		addEntities(campfire, 25, true, 1f, 1f);
		addEntities(grass, 100, true, 1f, 1f);

		return entities;
	}

	public boolean addEntity(Entity entity, boolean checkCollision)
	{
		//		if (checkCollision)
		//		{
		//			for (Map.Entry<Integer, List<Entity>> e : entities.entrySet())
		//			{
		//				for (int j = 0; j < e.getValue().size(); j++)
		//				{
		//					if (Collision.checkCollision(entity, e.getValue().get(j)))
		//					{
		//						return false;
		//					}
		//				}
		//			}
		//		}
		if (entities.get(entity.getId()) == null)
		{
			ArrayList<Entity> ents = new ArrayList<Entity>();
			ents.add(entity);
			entities.put(entity.getId(), ents);
			return true;
		}
		else
		{
			entities.get(entity.getId()).add(entity);
			return true;
		}
	}

	public void addEntities(Entity entity, int count, boolean randRot, float minScale, float maxScale)
	{
		int successE = 0;
		for (int i = 0; i < count; i++)
		{
			Entity e = entity.getFullCopy(false);

			float scale = rand.nextFloat() * (maxScale - minScale) + minScale;
			e.setPosition(TerrainGenerator.genRandTerrainPos(), 0, TerrainGenerator.genRandTerrainPos()).setScale(scale);

			if (randRot)
			{
				e.setRotationInRadians(new Vector3f(0, rand.nextFloat() * 180, 0));
			}
			inner: for (int j = 0; j < TRIES_TO_ADD_LIMIT; j++)
			{
				boolean a = addEntity(e, false);
				if (a)
				{
					successE++;
					break inner;
				}
				else
				{
					e.setPosition(TerrainGenerator.genRandTerrainPos(), 0, TerrainGenerator.genRandTerrainPos());
				}
				if (j == TRIES_TO_ADD_LIMIT - 1)
				{
					Logger.log("Try to add limit reached. Entity of type '" + entity.getName() + "' wasn't added on the map.");
				}
			}
		}
		Logger.log(successE + " entities of type '" + entity.getName() + "' were added successfully!");
	}
}
