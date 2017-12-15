package opengl.java.terrain;

import java.util.ArrayList;

import opengl.java.collision.CollisionMap;
import opengl.java.management.FileManager;
import opengl.java.texture.BaseTexture;

public class ChunkMap
{
	private ArrayList<Chunk> chunks = new ArrayList<Chunk>();

	private BaseTexture texture;

	private static int size;

	private CollisionMap colMap;

	public ChunkMap(int size) {
		ChunkMap.size = size;
		texture = FileManager.loadTexture("snowT");
		fillArray(size);
		colMap = new CollisionMap(size);
	}

	private void fillArray(int size)
	{
		int s = size / 2;
		for (int y = -s; y < s; y++)
		{
			for (int x = -s; x < s; x++)
			{
				chunks.add(new Chunk(x, y));
			}
		}
	}

	public ArrayList<Chunk> getChunkArray()
	{
		return chunks;
	}

	public BaseTexture getTexture()
	{
		return texture;
	}
}
