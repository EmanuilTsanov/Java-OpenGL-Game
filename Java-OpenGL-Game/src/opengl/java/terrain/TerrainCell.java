package opengl.java.terrain;

public class TerrainCell
{
	private boolean occupied;

	public void setOccupied(boolean state)
	{
		occupied = state;
	}

	public boolean isOccupied()
	{
		return occupied;
	}
}
