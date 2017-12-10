package opengl.java.collision;

import java.util.HashMap;

public class CollisionMaster
{
	private HashMap<String, CollisionCell> mapArray;

	public CollisionMaster()
	{
		mapArray = new HashMap<String, CollisionCell>();
		fillMap();
	}
	
	public void fillMap() {
	}

	public void setCellState(int x, int y, boolean state)
	{
		mapArray.get(getPosAsString(x, y)).setState(state);
	}

	public boolean isCellOccupied(int x, int y)
	{
		return mapArray.get(getPosAsString(x, y)).isOccupied();
	}

	public String getPosAsString(int x, int y)
	{
		return x + "" + y;
	}
}
