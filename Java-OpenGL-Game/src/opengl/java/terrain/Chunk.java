package opengl.java.terrain;

import org.lwjgl.util.vector.Vector2f;

import opengl.java.model.RawModel;

public class Chunk
{
	private Vector2f position;

	public RawModel model;

	public Chunk(int x, int y)
	{
		this.position = ChunkGenerator.getWorldPosition(x, y);
		this.model = ChunkGenerator.generateChunk();
	}

	public RawModel getModel()
	{
		return model;
	}

	public Vector2f getPosition()
	{
		return position;
	}
}
