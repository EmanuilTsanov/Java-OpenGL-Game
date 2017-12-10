package opengl.java.collision;

public class CollisionCell
{
	private boolean occupied;

	public void setState(boolean occupied)
	{
		this.occupied = occupied;
	}

	public boolean isOccupied()
	{
		return occupied;
	}
}
