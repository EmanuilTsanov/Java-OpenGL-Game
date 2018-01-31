package opengl.java.fonts;

public class Character
{
	private int id;
	private int x, y;
	private int width, height;
	private float scrWidth, scrHeight;
	private float xOffset, yOffset;
	private float xAdvance;

	public Character(int id, int x, int y, int width, int height, float xOffset, float yOffset, float xAdvance)
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.xAdvance = xAdvance;
	}

	public int getID()
	{
		return id;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public float getScrWidth()
	{
		return scrWidth;
	}

	public float getScrHeight()
	{
		return scrHeight;
	}

	public float getXOffset()
	{
		return xOffset;
	}

	public float getYOffset()
	{
		return yOffset;
	}

	public float getXAdvance()
	{
		return xAdvance;
	}

	public Character getCopy()
	{
		return new Character(id, x, y, width, height, xOffset, yOffset, xAdvance);
	}

	public Character setFontSize(float v)
	{
		scrWidth = (float) width * v;
		scrHeight = (float) height * v;
		xOffset *= v;
		yOffset *= v;
		xAdvance *= v;
		return this;
	}
}
