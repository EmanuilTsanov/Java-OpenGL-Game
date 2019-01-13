package opengl.java.gui;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.loader.ModelLoader;
import opengl.java.maths.Maths;
import opengl.java.model.RawModel;
import opengl.java.shader.GUIShader;

public abstract class GUIComponent
{
	protected float x, y;

	protected float width, height;

	protected Vector3f color;

	protected RawModel model;

	private static ArrayList<GUIComponent> clickableComponents = new ArrayList<GUIComponent>();

	private static ArrayList<GUIComponent> components = new ArrayList<GUIComponent>();

	public GUIComponent(float x, float y, float width, float height, GUIComponent parent)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		model = createMesh(width, height);
		color = new Vector3f(1f, 1f, 1f);
		components.add(this);
	}

	protected RawModel createMesh(float width, float height)
	{
		float[] vertices = { -1, 1, 0, -1, Maths.toOpenGLHeight(height), 0, Maths.toOpenGLWidth(width), Maths.toOpenGLHeight(height), 0, Maths.toOpenGLWidth(width), 1, 0 };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };
		float[] normals = { 0 };
		return ModelLoader.loadModel(vertices, indices, textureCoords, normals);
	}

	public void setColor(float r, float g, float b)
	{
		color = Maths.normalizeColor(new Vector3f(r, g, b));
	}

	public void moveByX(float distance)
	{
		x += distance;
	}

	public void moveByY(float distance)
	{
		y += distance;
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

	public static ArrayList<GUIComponent> getAllComponents()
	{
		return components;
	}

	public static ArrayList<GUIComponent> getClickableComponents()
	{
		return clickableComponents;
	}

	public abstract void update();

	public abstract void render(GUIShader shader);
}