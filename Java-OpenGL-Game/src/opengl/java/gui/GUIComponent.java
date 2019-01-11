package opengl.java.gui;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.loader.ModelLoader;
import opengl.java.maths.Maths;
import opengl.java.model.RawModel;
import opengl.java.shader.GUIShader;

public abstract class GUIComponent
{
	protected int x, y;
	
	protected int renderX, renderY;

	protected int width, height;

	protected Vector3f color;

	protected RawModel model;
	
	protected GUIComponent parent;

	public GUIComponent(int x, int y, int width, int height, GUIComponent parent)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.parent = parent;
		model = createMesh(width, height);
		color = new Vector3f(1f, 1f, 1f);
	}

	public RawModel createMesh(int width, int height)
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

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
	
	public int getRenderX() {
		return renderX;
	}
	public int getRenderY() {
		return renderY;
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