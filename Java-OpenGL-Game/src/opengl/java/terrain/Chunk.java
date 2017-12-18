package opengl.java.terrain;

import org.lwjgl.util.vector.Vector2f;

import opengl.java.collision.CollisionMap;
import opengl.java.model.RawModel;

public class Chunk
{
	private Vector2f position;

	public RawModel model;

	public CollisionMap colMap;

	public Chunk(float x, float y)
	{
		this.position = ChunkGenerator.getWorldPosition(x, y);
		this.model = ChunkGenerator.generateChunk();
		colMap = new CollisionMap(ChunkGenerator.getVertexSize());
	}

	public RawModel getModel()
	{
		return model;
	}

	public Vector2f getPosition()
	{
		return position;
	}

	public CollisionMap getColMap()
	{
		return colMap;
	}
}
