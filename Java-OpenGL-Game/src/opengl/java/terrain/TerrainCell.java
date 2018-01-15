package opengl.java.terrain;

public class TerrainCell
{
	private boolean occupied;

	public boolean isOccupied()
	{
		return occupied;
	}

	public void setOccupied(boolean state)
	{
		this.occupied = state;
	}
}
