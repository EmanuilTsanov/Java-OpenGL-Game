package opengl.java.management;

import java.util.ArrayList;
import java.util.HashMap;

import opengl.java.entity.Entity;
import opengl.java.loader.MapLoader;

public class EntityManager
{
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
		ArrayList<Entity> entities1 = MapLoader.loadMap("new_map");
		for (int i = 0; i < entities1.size(); i++)
		{
			addEntity(entities1.get(i));
		}
	}

	public boolean addEntity(Entity entity)
	{
		Entity e = entity.getCopy();
		if (entities.get(entity.getAssetID()) == null)
		{
			HashMap<Integer, Entity> batch = new HashMap<Integer, Entity>();
			batch.put(e.getID(), e);
			entities.put(entity.getAssetID(), batch);
			return true;
		}
		else
		{
			entities.get(entity.getAssetID()).put(entity.getID(), entity);
			return true;
		}
	}

	public void removeEntity(Entity e)
	{
		HashMap<Integer, Entity> ptr = entities.get(e.getAssetID());
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
