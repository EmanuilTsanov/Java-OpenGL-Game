package opengl.java.terrain;

import java.util.HashMap;

import opengl.java.management.FileManager;
import opengl.java.texture.BaseTexture;

public class ChunkMap
{
	private HashMap<String, Chunk> chunks = new HashMap<String, Chunk>();

	private BaseTexture texture;

	private int size;

	public ChunkMap(int size)
	{
		this.size = size;
		texture = FileManager.loadTexture("snowT");
		fillArray(size);
	}

	private void fillArray(int size)
	{
		float startP = -(float) size / 2f;
		for (int y = 0; y < size; y++)
		{
			for (int x = 0; x < size; x++)
			{
				Chunk chunk = new Chunk(startP + x, startP + y);
				chunks.put(x + "/" + y, chunk);
			}
		}
	}

	public Chunk getChunkByPos(float x, float y)
	{
		int chunkSize = ChunkGenerator.getVertexSize() * ChunkGenerator.getQuadSize();
		float xArr = x / chunkSize;
		float yArr = y / chunkSize;
		System.out.println(xArr + " / " + yArr + "        " + x + " / " + y);
		return chunks.get(xArr + "/" + yArr);
	}

	public HashMap<String, Chunk> getChunkArray()
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
