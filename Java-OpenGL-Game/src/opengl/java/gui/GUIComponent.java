package opengl.java.gui;

import opengl.java.calculations.Maths;
import opengl.java.loader.ModelLoader;
import opengl.java.model.Model;
import opengl.java.shader.GUIShader;
import opengl.java.texture.ModelTexture;

public abstract class GUIComponent
{
	protected float x, y;
	protected float width, height;

	protected Model model;
	protected ModelTexture image;
	protected GUIComponent parent;

	protected GUIComponent(int width, int height)
	{
		this.width = Maths.normalizeByWidth(width);
		this.height = Maths.normalizeByHeight(height);
	}

	public GUIComponent create()
	{
		if (parent != null)
		{
			if (width < parent.getWidth() && height < parent.getHeight())
			{
				if (x < parent.getX())
				{
					x = parent.getX();
					System.out.println("1A GUI component wants to be displayed outside of it's parent. Changing properties.");
				}
				if (y > parent.getY())
				{
					y = parent.getY();
					System.out.println("CHILD: " + y);
					System.out.println("PARENT: " + parent.getY());
					System.out.println("2A GUI component wants to be displayed outside of it's parent. Changing properties.");
				}
				if (x + width > parent.getX() + parent.getWidth())
				{
					x = parent.getX() + parent.getWidth() - width;
					System.out.println("3A GUI component wants to be displayed outside of it's parent. Changing properties.");
				}
				if (y + height < parent.getY() + parent.getHeight())
				{
					y = parent.getY() + parent.getHeight() - height;
					System.out.println("4A GUI component wants to be displayed outside of it's parent. Changing properties.");
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
		float width1 = width * 2;
		float height1 = height * 2;
		float[] vertices = { 0, 0, 0, 0, -height1, 0, width1, -height1, 0, width1, 0, 0 };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		float[] normals = { 0 };
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };
		model = ModelLoader.getInstance().loadModel(vertices, indices, textureCoords, normals);
		return this;
	}

	public GUIComponent setParent(GUIComponent parent)
	{
		this.parent = parent;
		return this;
	}

	public GUIComponent setPosition(int x, int y)
	{
		this.x = Maths.toOpenGLWidth(x);
		this.y = Maths.toOpenGLHeight(y);
		return this;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public float getWidth()
	{
		return width;
	}

	public float getHeight()
	{
		return height;
	}

	public abstract void update();

	public abstract void render(GUIShader shader);
}
