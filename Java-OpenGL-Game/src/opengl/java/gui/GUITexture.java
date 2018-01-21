package opengl.java.gui;

import opengl.java.loader.ModelLoader;
import opengl.java.management.FileManager;
import opengl.java.model.RawModel;
import opengl.java.texture.ModelTexture;
import opengl.java.window.Window;

public class GUITexture extends GUIComponent
{
	private RawModel imgModel;
	private ModelTexture image;

	public GUITexture(int x, int y, int width, int height, String imageName)
	{
		super(x, y, width, height);
		imgModel = loadModel();
		this.image = FileManager.loadTexture(imageName);
	}

	private RawModel loadModel()
	{
		float x1 = toOpenGLWidth(x);
		float y1 = toOpenGLHeight(y);
		float width1 = toOpenGLWidth(x + width);
		float height1 = toOpenGLHeight(y + height);
		float[] vertices = { x1, y1, 0.0f, width1, y1, 0.0f, width1, height1, 0.0f, x1, height1, 0.0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		float[] normals = { 0 };
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };
		return ModelLoader.getInstance().loadModel(vertices, indices, textureCoords, normals);
	}

	public float toOpenGLWidth(int x)
	{
		return (float) (Window.getInstance().getWidth() / 2 - 400) * (-1) / Window.getInstance().getWidth() / 2;
	}

	public float toOpenGLHeight(int x)
	{
		return (float) (Window.getInstance().getHeight() / 2 - 400) * (-1) / Window.getInstance().getWidth() / 2;
	}

	@Override
	public void render()
	{
		
	}

}
