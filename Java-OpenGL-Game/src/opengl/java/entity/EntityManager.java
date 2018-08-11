package opengl.java.entity;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;

public class EntityManager
{
	private static int nextUniqueID;

	private static Vector3f nextUniqueColor = new Vector3f(0, 0, 0);
	private static HashMap<String, Integer> colorArray = new HashMap<String, Integer>();

	public static int getNextUniqueID()
	{
		return EntityManager.nextUniqueID++;
	}

	public static Vector3f getNextUniqueColor(int uniqueID)
	{
		int r = (int) nextUniqueColor.x;
		int g = (int) nextUniqueColor.y;
		int b = (int) nextUniqueColor.z;
		Vector3f col = new Vector3f(r, g, b);
		if (b < 255)
		{
			nextUniqueColor.z++;
		}
		else if (g < 255)
		{
			nextUniqueColor.y++;
			nextUniqueColor.z = 0;
		}
		else if (r < 255)
		{
			nextUniqueColor.x++;
			nextUniqueColor.y = 0;
			nextUniqueColor.z = 0;
		}
		colorArray.put(col.x + "/" + col.y + "/" + col.z, uniqueID);
		return col;
	}
	
	public static int getUniqueIDByColor(String color) {
		if(colorArray.get(color) == null) {
			return -1;
		}
		return colorArray.get(color);
	}
}
