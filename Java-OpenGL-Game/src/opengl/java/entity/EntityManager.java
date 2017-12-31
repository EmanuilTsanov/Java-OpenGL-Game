package opengl.java.entity;

import java.util.HashMap;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.logger.Logger;
import opengl.java.terrain.TerrainGenerator;

public class EntityManager
{
	private static final int TRY_LIMIT = 50;

	private Random rand = new Random();

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
		addEntities(Entity.pineTree, 100, true);
		addEntities(Entity.bench, 100, true);
		addEntities(Entity.christmasTree, 100, true);
		addEntities(Entity.snowman, 100, true);
		addEntities(Entity.table, 10, true);
		addEntities(Entity.campfire, 25, true);
		addEntities(Entity.grass, 100, true);
	}

	public boolean addEntity(Entity entity)
	{
		Entity e = entity.getCopy();
		if (entities.get(entity.getId()) == null)
		{
			HashMap<Integer, Entity> batch = new HashMap<Integer, Entity>();
			batch.put(e.getUniqueID(), e);
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
		int addedEntityCount = 0;
		for (int i = 0; i < count; i++)
		{
			Entity e = entity.getCopy();

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
					addedEntityCount++;
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
		Logger.log(addedEntityCount + " entities of type '" + entity.getName() + "' were added successfully!");
	}

	public void removeEntity(Entity e)
	{
		HashMap<Integer, Entity> ptr = entities.get(e.getId());
		ptr.remove(e.getUniqueID());
		if(ptr.isEmpty()) {
			ptr = null;
		}
	}

	public HashMap<Integer, HashMap<Integer, Entity>> getEntityHashMap()
	{
		return entities;
	}
}
