package opengl.java.terrain;

import java.util.ArrayList;

import opengl.java.management.FileManager;
import opengl.java.texture.BaseTexture;

public class TerrainManager
{
	private ArrayList<Chunk> chunks = new ArrayList<Chunk>();

	private BaseTexture texture;

	public static int size;

	public TerrainManager(int size)
	{
		TerrainManager.size = size;
		texture = FileManager.loadTexture("snowT");
		generateChunks(size);
	}

	private void generateChunks(int size)
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
