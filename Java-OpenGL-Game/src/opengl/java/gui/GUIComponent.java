package opengl.java.gui;

import opengl.java.loader.ModelLoader;
import opengl.java.model.Model;
import opengl.java.texture.ModelTexture;
import opengl.java.window.Window;

public abstract class GUIComponent
{
	protected int x, y;
	protected int width, height;
	protected Model model;
	protected ModelTexture image;

	protected GUIComponent(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	protected Model createCanvas()
	{
		float x1 = Window.getInstance().toOpenGLWidth(x);
		float y1 = Window.getInstance().toOpenGLHeight(y);
		float width1 = Window.getInstance().toOpenGLWidth(x + width);
		float height1 = Window.getInstance().toOpenGLHeight(y + height);
		float[] vertices = { x1, y1, 0.0f, width1, y1, 0.0f, width1, height1, 0.0f, x1, height1, 0.0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		float[] normals = { 0 };
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };
		return ModelLoader.getInstance().loadModel(vertices, indices, textureCoords, normals);
	}

	public abstract void render();

	public abstract void update();
}
