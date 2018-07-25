package opengl.java.gui;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.loader.ModelLoader;
import opengl.java.model.Model;
import opengl.java.shader.GUIShader;

public abstract class GUIComponent
{
	protected int x, y;
	protected int width, height;

	protected Model model;
	protected Vector3f color = new Vector3f(0, 0, 0);

	protected GUIComponent(int width, int height)
	{
		this.width = width;
		this.height = height;
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
		return this;
	}

	public GUIComponent setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	public GUIComponent setColor(int r, int g, int b)
	{
		float a = 1f / 255f;
		this.color = new Vector3f(a * r, a * g, a * b);
		return this;
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
