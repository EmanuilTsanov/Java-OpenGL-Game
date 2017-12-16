package opengl.java.terrain;

import java.util.ArrayList;

import opengl.java.collision.CollisionMap;
import opengl.java.management.FileManager;
import opengl.java.texture.BaseTexture;

public class ChunkMap
{
	private ArrayList<Chunk> chunks = new ArrayList<Chunk>();

	private BaseTexture texture;

	private int size;

	private CollisionMap colMap;

	public ChunkMap(int size)
	{
		this.size = size;
		texture = FileManager.loadTexture("snowT");
		fillArray(size);
		colMap = new CollisionMap(size);
	}

	private void fillArray(int size)
	{
		float startP = -(float) size / 2f;
		for (int y = 0; y < size; y++)
		{
			for (int x = 0; x < size; x++)
			{
				Chunk chunk = new Chunk(startP + x, startP + y);
				chunks.add(chunk);
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

	public int getSize()
	{
		return size;
	}
}
