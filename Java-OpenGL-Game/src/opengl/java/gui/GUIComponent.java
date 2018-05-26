package opengl.java.gui;

import opengl.java.calculations.Maths;
import opengl.java.loader.ModelLoader;
import opengl.java.model.Model;
import opengl.java.texture.ModelTexture;

public abstract class GUIComponent
{
	protected int x, y;
	protected int width, height;

	protected Model model;
	protected ModelTexture image;
	protected GUIComponent parent;

	protected GUIComponent(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void create()
	{
		if (parent != null)
		{
			x += parent.getX();
			y += parent.getY();
			if (width < parent.getWidth() && height < parent.getHeight())
			{
				if (x < parent.getX())
				{
					x = parent.getX();
					System.out.println("A GUI component wants to be displayed outside of it's parent. Changing properties.");
				}
				if (y < parent.getY())
				{
					y = parent.getY();
					System.out.println("A GUI component wants to be displayed outside of it's parent. Changing properties.");
				}
				if (x + width > parent.getX() + parent.getWidth())
				{
					x = parent.getX() + parent.getWidth() - width;
					System.out.println("A GUI component wants to be displayed outside of it's parent. Changing properties.");
				}
				if (y + height > parent.getY() + parent.getHeight())
				{
					y = parent.getY() + parent.getHeight() - height;
					System.out.println("A GUI component wants to be displayed outside of it's parent. Changing properties.");
				}
			}
			if (width >= parent.getWidth() || height >= parent.getHeight())
			{
				System.out.println("A GUI component is too big to be displayed within it's parent. Changing properties.");
				x = parent.getX() + parent.getWidth() / 10;
				y = parent.getY() + parent.getHeight() / 10;
				width = parent.getWidth() / 2;
				height = parent.getHeight() / 2;
			}
		}
		float x1 = Maths.toOpenGLWidth(x);
		float y1 = Maths.toOpenGLHeight(y);
		float width1 = Maths.toOpenGLWidth(x + width);
		float height1 = Maths.toOpenGLHeight(y + height);
		float[] vertices = { x1, height1, 0.0f, width1, height1, 0.0f, width1, y1, 0.0f, x1, y1, 0.0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		float[] normals = { 0 };
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };
		model = ModelLoader.getInstance().loadModel(vertices, indices, textureCoords, normals);
	}

	public void setParent(GUIComponent parent)
	{
		this.parent = parent;
	}

	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
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

	public abstract void update();

	public abstract void render();
}
