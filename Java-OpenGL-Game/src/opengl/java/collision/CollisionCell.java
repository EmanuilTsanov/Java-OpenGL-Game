package opengl.java.collision;

import org.lwjgl.util.vector.Vector2f;

public class CollisionCell
{
	private boolean occupied;
	private Vector2f position;

	public CollisionCell(Vector2f position) {
		this.position = position;
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
}
