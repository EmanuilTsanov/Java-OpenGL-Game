package opengl.java.gui;

public abstract class GUIComponent
{
	protected int x, y;
	protected int width, height;

	public GUIComponent(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public abstract void render();
}
