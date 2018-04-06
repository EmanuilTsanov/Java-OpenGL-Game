package opengl.java.loader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.logger.Logger;

public class MapLoader
{
	public static ArrayList<Entity> loadMap(String file)
	{
		ArrayList<Entity> entities = new ArrayList<Entity>();
		try (BufferedReader stream = new BufferedReader(new FileReader(new File("assets/maps/" + file + ".map"))))
		{
			String line;
			while ((line = stream.readLine()) != null)
			{
				if (line.startsWith("e "))
				{
					String[] tokens = line.split("\\s+");
					int asset = Integer.parseInt(tokens[1]);
					Vector3f position = new Vector3f(0, 0, 0);
					Vector3f rotation = new Vector3f(0, 0, 0);
					for (int i = 2; i < tokens.length; i++)
					{
						if (tokens[i].startsWith("-p"))
						{
							String[] posTokens = tokens[i].split("-p")[1].split("<")[1].split(">")[0].split(",");
							position = new Vector3f(Float.parseFloat(posTokens[0]), Float.parseFloat(posTokens[1]), Float.parseFloat(posTokens[2]));
						}
						if (tokens[i].startsWith("-r"))
						{
							String[] rotTokens = tokens[i].split("-r")[1].split("<")[1].split(">")[0].split(",");
							rotation = new Vector3f(Float.parseFloat(rotTokens[0]), Float.parseFloat(rotTokens[1]), Float.parseFloat(rotTokens[2]));
						}
					}
					Entity e = new Entity(asset).setPosition(position).setRotationInDegrees(rotation).getCopy();
					entities.add(e);
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

	public static void saveMap(String mapName, HashMap<Integer, HashMap<Integer, Entity>> entities)
	{
		File file = new File("assets/maps/" + mapName + ".map");
		try
		{
			if (file.exists())
				file.delete();
			file.createNewFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
		{
			writer.write("#This file was created on " + Logger.getDate() + " " + Logger.getTime() + ".");
			for (Map.Entry<Integer, HashMap<Integer, Entity>> entityHash : entities.entrySet())
			{
				for (Map.Entry<Integer, Entity> inner : entityHash.getValue().entrySet())
				{
					Entity e = inner.getValue();
					writer.write(
							"\ne " + e.getAsset() + " -p<" + e.getPosition().x + "," + e.getPosition().y + "," + e.getPosition().z + "> -r<" + e.getRotation().x + "," + e.getRotation().y + "," + e.getRotation().z + ">");
				}
			}
			writer.close();
		}
		catch (Exception e)
		{

		}
	}

	public static void clearMapFile(String file)
	{
		try (BufferedReader stream = new BufferedReader(new FileReader(new File("assets/maps/" + file + ".map"))))
		{
			String line;
			while ((line = stream.readLine()) != null)
			{
				if (line.startsWith("e "))
				{

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
	}
}
