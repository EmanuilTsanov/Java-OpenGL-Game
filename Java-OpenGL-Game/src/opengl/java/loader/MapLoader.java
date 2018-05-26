package opengl.java.loader;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;

public class MapLoader
{
	public static ArrayList<Entity> loadMap(String file)
	{
		ArrayList<Entity> entities = new ArrayList<Entity>();
		try (DataInputStream stream = new DataInputStream(new FileInputStream("assets/maps/" + file + ".map")))
		{
			while (!(stream.available()==0))
			{
				Entity e = new Entity(stream.readInt()).setPosition(new Vector3f(stream.readFloat(), stream.readFloat(), stream.readFloat()))
						.setRotationInRadians(new Vector3f(stream.readFloat(), stream.readFloat(), stream.readFloat())).getCopy();
				entities.add(e);
			}
		}
		catch (

		FileNotFoundException e)
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
		try (DataOutputStream stream = new DataOutputStream(new FileOutputStream(file)))
		{
			for (Map.Entry<Integer, HashMap<Integer, Entity>> entityHash : entities.entrySet())
			{
				for (Map.Entry<Integer, Entity> inner : entityHash.getValue().entrySet())
				{
					Entity e = inner.getValue();
					stream.writeInt(e.getAsset());
					stream.writeFloat(e.getPosition().x);
					stream.writeFloat(e.getPosition().y);
					stream.writeFloat(e.getPosition().z);
					stream.writeFloat(e.getRotation().x);
					stream.writeFloat(e.getRotation().y);
					stream.writeFloat(e.getRotation().z);
					System.out.println(e.getAsset());
				}
			}
			stream.close();
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
