package opengl.java.collision;

import org.lwjgl.util.vector.Vector2f;

import opengl.java.terrain.ChunkGenerator;

public class CollisionCell
{
	private boolean occupied;
	private Vector2f position;
	private int size;

	public CollisionCell(Vector2f position)
	{
		this.position = position;
		this.size = ChunkGenerator.getQuadSize();
	}

	public void setState(boolean occupied)
	{
		this.occupied = occupied;
	}

	public boolean isOccupied()
	{
		return occupied;
	}

	public Vector2f getPosition()
	{
		return position;
	}

	public int getSize()
	{
		return size;
	}
}
