package opengl.java.gui;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.loader.ModelLoader;
import opengl.java.model.Model;
import opengl.java.shader.GUIShader;

public abstract class GUIComponent
{
	protected int x, y;
	protected int xAdv, yAdv;
	protected int width, height;
	protected GUIComponent parent;
	protected HashMap<Integer, GUIComponent> children;

	protected Model model;
	protected Vector3f color = new Vector3f(0, 0, 0);

	protected GUIComponent(int width, int height)
	{
		this.width = width;
		this.height = height;
		children = new HashMap<Integer, GUIComponent>();
	}

	public GUIComponent create()
	{
		float width1 = Maths.normalizeByWidth(width * 2);
		float height1 = Maths.normalizeByHeight(height * 2);
		float[] vertices = { 0, 0, 0, 0, -height1, 0, width1, -height1, 0, width1, 0, 0 };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		float[] normals = { 0 };
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };
		model = ModelLoader.getInstance().loadModel(vertices, indices, textureCoords, normals);
		if (parent != null)
		{
			x = xAdv + parent.getX();
			y = yAdv + parent.getY();
		}
		else
		{
			x = xAdv;
			y = yAdv;
		}
		return this;
	}

	public GUIComponent setPosition(int x, int y)
	{
		this.xAdv = x;
		this.yAdv = y;
		return this;
	}

	public GUIComponent setParent(int pos, GUIComponent parent)
	{
		this.parent = parent;
		this.parent.addChild(pos, this);
		return this;
	}

	protected void addChild(int pos, GUIComponent child)
	{
		children.put(pos, child);
	}

	public GUIComponent setColor(int r, int g, int b)
	{
		float a = 1f / 255f;
		this.color = new Vector3f(a * r, a * g, a * b);
		return this;
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

	public abstract void render(GUIShader shader);
}
