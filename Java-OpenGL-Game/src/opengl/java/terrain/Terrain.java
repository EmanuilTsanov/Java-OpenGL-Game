package opengl.java.terrain;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;

import opengl.java.collision.CollisionCell;
import opengl.java.management.FileManager;
import opengl.java.model.RawModel;
import opengl.java.texture.BaseTexture;

public class Terrain
{
	private Vector2f position;

	public RawModel model;
	private HashMap<String, CollisionCell> colMap;

	private BaseTexture texture;

	public Terrain()
	{
		this.position = new Vector2f(0, 0);
		this.model = TerrainGenerator.generateTerrain();
		colMap = new HashMap<String, CollisionCell>();
		fillCollisionMap(TerrainGenerator.getVertexSize()-1);
		texture = FileManager.loadTexture("snowT");
	}

	public RawModel getModel()
	{
		return model;
	}

	public Vector2f getPosition()
	{
		return position;
	}

	private void fillCollisionMap(int size)
	{
		for (int y = 0; y < size; y++)
		{
			for (int x = 0; x < size; x++)
			{
				colMap.put(toColCoords(x, y), new CollisionCell(new Vector2f(x, y)));
			}
		}
	}

	public CollisionCell getColCell(int x, int y)
	{
		return colMap.get(toColCoords(x, y));
	}

	public String toColCoords(int x, int y)
	{
		return x + "/" + y;
	}

	public BaseTexture getTexture()
	{
		return texture;
	}
}
