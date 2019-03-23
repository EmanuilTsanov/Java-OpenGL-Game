package opengl.java.terrain;

import opengl.java.loader.ImageLoader;
import opengl.java.loader.ModelLoader;
import opengl.java.model.RawModel;

public class Terrain
{
	private static final float SIZE = 1024;
	private static final int VERTEX_COUNT = 128;

	private float x, z;
	private RawModel model;
	private int texture;

	public Terrain(int gridX, int gridZ, String texture)
	{
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.model = generateTerrain();
		this.texture = ImageLoader.loadTexture(texture);
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
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
		int vertexPointer = 0;
		for (int i = 0; i < VERTEX_COUNT; i++)
		{
			for (int j = 0; j < VERTEX_COUNT; j++)
			{
				vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer * 3 + 1] = 0;
				vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
				normals[vertexPointer * 3] = 0;
				normals[vertexPointer * 3 + 1] = 1;
				normals[vertexPointer * 3 + 2] = 0;
				textureCoords[vertexPointer * 2] = (float) j / ((float) VERTEX_COUNT - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for (int gz = 0; gz < VERTEX_COUNT - 1; gz++)
		{
			for (int gx = 0; gx < VERTEX_COUNT - 1; gx++)
			{
				int topLeft = (gz * VERTEX_COUNT) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
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
