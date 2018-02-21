package opengl.java.management;

import java.util.HashMap;

import opengl.java.entity.Entity;
import opengl.java.logger.Logger;
import opengl.java.terrain.TerrainGenerator;

public class EntityManager
{
	private static final int TRY_LIMIT = 50;

	private HashMap<Integer, HashMap<Integer, Entity>> entities;

	private static EntityManager singleton = new EntityManager();

	public EntityManager()
	{
		entities = new HashMap<Integer, HashMap<Integer, Entity>>();
		loadEntities();
	}

	public static EntityManager getInstance()
	{
		return singleton;
	}

	public void loadEntities()
	{
		addEntities(Entity.pineTree, 1000);
		addEntities(Entity.bench, 100);
		addEntities(Entity.table, 10);
		addEntities(Entity.campfire, 25);
		addEntities(Entity.grass, 100);
		addEntities(Entity.mushroom, 100);
		addEntities(Entity.mushroom1, 100);
		addEntities(Entity.rock, 100);
		addEntities(Entity.hut, 1);
	}

	public boolean addEntity(Entity entity)
	{
		Entity e = entity.getCopy();
		if (entities.get(entity.getSrcID()) == null)
		{
			HashMap<Integer, Entity> batch = new HashMap<Integer, Entity>();
			batch.put(e.getID(), e);
			entities.put(entity.getSrcID(), batch);
			return true;
		}
		else
		{
			entities.get(entity.getSrcID()).put(entity.getID(), entity);
			return true;
		}
	}

	public void addEntities(Entity entity, int count)
	{
		int addedEntityCount = 0;
		for (int i = 0; i < count; i++)
		{
			Entity e = entity.getCopy();

			e.setPosition(TerrainGenerator.genRandTerrainPos(), 0, TerrainGenerator.genRandTerrainPos());

			inner: for (int j = 0; j < TRY_LIMIT; j++)
			{
				boolean a = addEntity(e);
				if (a)
				{
					addedEntityCount++;
					break inner;
				}
				else
				{
					e.setPosition(TerrainGenerator.genRandTerrainPos(), 0, TerrainGenerator.genRandTerrainPos());
				}
				if (j == TRY_LIMIT - 1)
				{
					Logger.log("Try to add limit reached. Entity wasn't added on the map.");
				}
			}
		}
		Logger.log(addedEntityCount + " entities were added successfully!");
	}

	public void removeEntity(Entity e)
	{
		HashMap<Integer, Entity> ptr = entities.get(e.getSrcID());
		ptr.remove(e.getID());
		if (ptr.isEmpty())
		{
			ptr = null;
		}
	}

	public HashMap<Integer, HashMap<Integer, Entity>> getEntityHashMap()
	{
		return entities;
	}
}
