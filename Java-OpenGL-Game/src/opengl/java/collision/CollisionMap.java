package opengl.java.collision;

import java.util.HashMap;

import opengl.java.calculations.Maths;

public class CollisionMap
{
	private HashMap<Integer, CollisionCell> colMap;

	public CollisionMap(int width, int height)
	{
		fillCollisionMap(width, height);
	}

	private void fillCollisionMap(int width, int height)
	{
		for (int i = 0; i < width * height; i++)
		{
			colMap.put(Maths.concatenateInts(width, height), new CollisionCell());
		}
	}
}
