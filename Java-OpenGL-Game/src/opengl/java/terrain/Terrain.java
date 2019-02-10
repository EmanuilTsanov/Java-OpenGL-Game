package opengl.java.terrain;

import opengl.java.loader.ModelLoader;
import opengl.java.management.SRCLoader;
import opengl.java.model.RawModel;

public class Terrain
{
	private static final float SIZE = 1024;

	private float x, z;
	private RawModel model;
	private int texture;

	public Terrain(int gridX, int gridZ, String texture)
	{
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.model = generateTerrain();
		this.texture = SRCLoader.getTexture(texture).getID();
	}

	public float getX()
	{
		return x;
	}

	public float getZ()
	{
		return z;
	}

	public RawModel getModel()
	{
		return model;
	}

	private RawModel generateTerrain()
	{
		float[] vertices = { 0, 0, 0, 0, 0, SIZE, SIZE, 0, SIZE, SIZE, 0, 0 };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };
		float[] normals = { 0, 1, 0, 0, 1, 0 };
		return ModelLoader.loadModel(vertices, indices, textureCoords, normals);
	}

	public float getSize()
	{
		return SIZE;
	}

	public int getTexture()
	{
		return texture;
	}
}
