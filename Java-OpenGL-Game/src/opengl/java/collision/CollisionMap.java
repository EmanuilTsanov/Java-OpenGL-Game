package opengl.java.collision;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;

import opengl.java.calculations.Maths;

public class CollisionMap
{
	private HashMap<Integer, CollisionCell> colMap;

	public CollisionMap(int size)
	{
		colMap = new HashMap<Integer, CollisionCell>();
		fillCollisionMap(size);
	}

	private void fillCollisionMap(int size)
	{
		for (int y = 0; y < size; y++)
		{
			for (int x = 0; x < size; x++)
			{
				colMap.put(Maths.concatenateInts(x, y), new CollisionCell(new Vector2f(x, y)));
			}
		}
		for (Map.Entry<Integer, CollisionCell> e : colMap.entrySet())
		{
			System.out.println(e.getKey());
		}
	}
}
