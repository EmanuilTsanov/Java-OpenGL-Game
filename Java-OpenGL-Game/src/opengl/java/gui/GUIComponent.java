package opengl.java.gui;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.loader.ModelLoader;
import opengl.java.maths.Maths;
import opengl.java.model.RawModel;
import opengl.java.shader.GUIShader;

public abstract class GUIComponent
{
	protected int x, y;

	protected int width, height;

	protected Vector3f bgcolor;

	protected RawModel model;

	public GUIComponent()
	{
	}

	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		model = createMesh(width, height);
	}

	public void setBackgroundColor(Vector3f color)
	{
		this.bgcolor = color;
	}

	protected RawModel createMesh(float width, float height)
	{
		float[] vertices = { -1, 1, 0, -1, Maths.toOpenGLHeight(height), 0, Maths.toOpenGLWidth(width), Maths.toOpenGLHeight(height), 0, Maths.toOpenGLWidth(width), 1, 0 };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };
		float[] normals = { 0 };
		return ModelLoader.loadModel(vertices, indices, textureCoords, normals);
	}

	public abstract void update();

	public abstract void render(GUIShader shader);
}