package opengl.java.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import opengl.java.entity.Entity;

public class MapLoader
{
	public static HashMap<Integer, HashMap<Integer, Entity>> loadMap(String file)
	{
		try (BufferedReader stream = new BufferedReader(new FileReader(new File(file))))
		{
			while (stream.readLine() != null) 
			{
				
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
