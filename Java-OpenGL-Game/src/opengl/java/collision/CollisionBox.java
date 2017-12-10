package opengl.java.collision;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

public class CollisionBox
{

	private float x, y, z;

	private float size_x;
	private float size_y;
	private float size_z;
	
	private float x_max = 0;
	private float x_min = 0;
	private float y_max = 0;
	private float y_min = 0;
	private float z_max = 0;
	private float z_min = 0;

	private float height;

	private ArrayList<Vector3f> vectors = new ArrayList<Vector3f>();

	public CollisionBox()
	{
	}

	public void push(ArrayList<Vector3f> values)
	{
		this.vectors = values;
		calculate();
	}

	public void calculate()
	{
		boolean xDone = false;
		boolean yDone = false;
		boolean zDone = false;

		float x_temp = 0;
		float y_temp = 0;
		float z_temp = 0;
		for (int i = 0; i < vectors.size(); i++)
		{
			Vector3f v = vectors.get(i);
			if (i == 0)
			{
				x_temp = v.getX();
				y_temp = v.getY();
				z_temp = v.getZ();
			}
			else
			{
				if (!xDone && v.getX() != x_temp)
				{
					x_max = (v.getX() > x_temp ? v.getX() : x_temp);
					x_min = (v.getX() < x_temp ? v.getX() : x_temp);
					xDone = true;
				}
				if (!yDone && v.getY() != y_temp)
				{
					y_max = (v.getY() > y_temp ? v.getY() : y_temp);
					y_min = (v.getY() < y_temp ? v.getY() : y_temp);
					yDone = true;
				}
				if (!zDone && v.getZ() != z_temp)
				{
					z_max = (v.getZ() > z_temp ? v.getZ() : z_temp);
					z_min = (v.getZ() < z_temp ? v.getZ() : z_temp);
					
					zDone = true;
				}
			}
		}
		size_x = Math.abs(x_max - x_min);
		height = size_y = Math.abs(y_max - y_min);
		size_z = Math.abs(z_max - z_min);
		x = (x_max + x_min) / 2f;
		y = (y_max + y_min) / 2f;
		z = (z_max + z_min) / 2f;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public float getZ()
	{
		return z;
	}

	public float getSizeX()
	{
		return size_x;
	}

	public float getSizeY()
	{
		return size_y;
	}

	public float getSizeZ()
	{
		return size_z;
	}

	public float getHeight()
	{
		return height;
	}

	public float getXMax()
	{
		return x_max;
	}

	public float getXMin()
	{
		return x_min;
	}

	public float getYMax()
	{
		return y_max;
	}

	public float getYMin()
	{
		return y_min;
	}

	public float getZMax()
	{
		return z_max;
	}

	public float getZMin()
	{
		return z_min;
	}
}
