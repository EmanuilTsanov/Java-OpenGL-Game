package opengl.java.gui;

import opengl.java.calculations.Maths;
import opengl.java.loader.ModelLoader;
import opengl.java.model.Model;
import opengl.java.shader.GUIShader;
import opengl.java.texture.ModelTexture;

public abstract class GUIComponent
{
	protected int x, y;
	protected int width, height;
	protected GUIComponent parent;

	protected Model model;
	protected ModelTexture image;

	protected GUIComponent(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	public GUIComponent create()
	{
		float width1 = Maths.normalizeByWidth(width) * 2;
		float height1 = Maths.normalizeByHeight(height) * 2;
		float[] vertices = { 0, 0, 0, 0, -height1, 0, width1, -height1, 0, width1, 0, 0 };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		float[] normals = { 0 };
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };
		model = ModelLoader.getInstance().loadModel(vertices, indices, textureCoords, normals);
		if(parent != null) {
			x+= parent.getX();
			y+= parent.getY();
		}
		return this;
	}

	public GUIComponent setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
		return this;
	}
	
	public GUIComponent setParent(GUIComponent g) {
		this.parent = g;
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
