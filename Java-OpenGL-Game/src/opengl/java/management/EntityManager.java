package opengl.java.management;

import java.util.ArrayList;
import java.util.HashMap;

import opengl.java.entity.Entity;
import opengl.java.loader.MapLoader;

public class EntityManager
{
	private static HashMap<Integer, HashMap<Integer, Entity>> entities = new HashMap<Integer, HashMap<Integer, Entity>>();

	public EntityManager()
	{
		loadEntities();
	}

	public static void loadEntities()
	{
		ArrayList<Entity> entities1 = MapLoader.loadMap("new_map");
		for (int i = 0; i < entities1.size(); i++)
		{
			addEntity(entities1.get(i));
		}
	}

	public static boolean addEntity(Entity entity)
	{
		Entity e = entity.getCopy();
		if (entities.get(e.getAsset()) == null)
		{
			HashMap<Integer, Entity> batch = new HashMap<Integer, Entity>();
			batch.put(e.getID(), e);
			entities.put(e.getAsset(), batch);
			return true;
		}
		else
		{
			entities.get(e.getAsset()).put(e.getID(), e);
			return true;
		}
	}

	public static void removeEntity(Entity e)
	{
		HashMap<Integer, Entity> ptr = entities.get(e.getAsset());
		ptr.remove(e.getID());
		if (ptr.isEmpty())
		{
			ptr = null;
		}
	}

	public static HashMap<Integer, HashMap<Integer, Entity>> getEntityHashMap()
	{
		return entities;
	}
}
