package opengl.java.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;

public class MapLoader
{
	public static HashMap<Integer, HashMap<Integer, Entity>> loadMap(String file)
	{
		HashMap<Integer, HashMap<Integer, Entity>> entities = new HashMap<Integer, HashMap<Integer, Entity>>();
		try (BufferedReader stream = new BufferedReader(new FileReader(new File(file))))
		{
			String line;
			while ((line = stream.readLine()) != null)
			{
				if (line.startsWith("e "))
				{
					String[] tokens = line.split("//s+");
					String[] posTokens = tokens[2].split(",");
					String[] rotTokens = tokens[3].split(",");
					int srcID = Integer.parseInt(tokens[1]);
					Vector3f position = new Vector3f(Integer.parseInt(posTokens[0]), Integer.parseInt(posTokens[1]), Integer.parseInt(posTokens[2]));
					Vector3f rotation = new Vector3f(Float.parseFloat(rotTokens[0]), Float.parseFloat(rotTokens[1]), Float.parseFloat(rotTokens[2]));
					float scale = Float.parseFloat(tokens[4]);
					Entity entity = new Entity(srcID).setPosition(position).setRotationInRadians(rotation).setScale(scale);
					addEntity(entities, entity);
				}
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return entities;
	}

	private static void addEntity(HashMap<Integer, HashMap<Integer, Entity>> entities, Entity e)
	{
		if (entities.get(e.getSrcID()) == null)
		{
			HashMap<Integer, Entity> innerMap = new HashMap<Integer, Entity>();
			innerMap.put(e.getID(), e);
			entities.put(e.getSrcID(), innerMap);
		}
		else
		{
			entities.get(e.getSrcID()).put(e.getID(), e);
		}
	}
}
