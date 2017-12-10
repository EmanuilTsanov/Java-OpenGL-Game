package opengl.java.collision;

import java.util.ArrayList;

public class CollisionModel
{
	private ArrayList<CollisionBox> boxes;
	private float width;
	private float height;
	private float depth;

	public CollisionModel(ArrayList<CollisionBox> boxes)
	{
		this.boxes = boxes;
		float xMin = 0, xMax = 0, yMin = 0, yMax = 0, zMin = 0, zMax = 0;
		for (int i = 0; i < boxes.size(); i++)
		{
			CollisionBox box = boxes.get(i);
			if (box.getX() < xMin)
			{
				xMin = box.getX();
			}
			if (box.getX() + box.getSizeX() > xMax)
			{
				xMax = box.getX() + box.getSizeX();
			}
			if (box.getY() < yMin)
			{
				yMin = box.getY();
			}
			if (box.getY() + box.getSizeY() > yMax)
			{
				yMax = box.getY() + box.getSizeY();
			}
			if (box.getZ() < zMin)
			{
				zMin = box.getZ();
			}
			if (box.getZ() + box.getSizeZ() > zMax)
			{
				zMax = box.getZ() + box.getSizeZ();
			}
		}
		width = (xMax - xMin);
		height = (yMax - yMin);
		depth = (zMax - zMin);
	}

	public ArrayList<CollisionBox> getBoxes()
	{
		return boxes;
	}

	public float getWidth(float scale)
	{
		return width * scale;
	}

	public float getHeight(float scale)
	{
		return height * scale;
	}

	public float getDepth(float scale)
	{
		return depth * scale;
	}
}