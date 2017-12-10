package opengl.java.terrain;

import opengl.java.loader.ModelLoader;
import opengl.java.model.RawModel;

public class Chunk
{
	private int x;
	private int z;
	public static final int VERTEX_SIZE = 64;
	public static final int QUAD_SIZE = 1;

	private float[] vertices = new float[VERTEX_SIZE * VERTEX_SIZE * 3];
	private int[] indices = new int[(VERTEX_SIZE - 1) * (VERTEX_SIZE - 1) * 6];
	private float[] tex_coords = new float[VERTEX_SIZE * VERTEX_SIZE * 2];

	public RawModel model;

	public Chunk(int x, int z)
	{
		this.x = x * VERTEX_SIZE * QUAD_SIZE - QUAD_SIZE * x;
		this.z = z * VERTEX_SIZE * QUAD_SIZE - QUAD_SIZE * z;
		model = generateChunk();
	}

	private RawModel generateChunk()
	{
		int pointer;
		int tPointer;
		/**
		 * Generating vertices.
		 */
		for (int i = 0; i < VERTEX_SIZE; i++)
		{
			for (int j = 0; j < VERTEX_SIZE; j++)
			{
				pointer = (i * VERTEX_SIZE + j) * 3;
				vertices[pointer] = j * QUAD_SIZE;
				vertices[pointer + 1] = 0;
				vertices[pointer + 2] = i * QUAD_SIZE;
				tPointer = (i * VERTEX_SIZE + j) * 2;
				tex_coords[tPointer] = (float) j / (float) (VERTEX_SIZE - 1);
				tex_coords[tPointer + 1] = (float) i / (float) (VERTEX_SIZE - 1);
			}
		}
		/**
		 * Generating indices.
		 */
		for (int j = 0; j < (VERTEX_SIZE - 1); j++)
			for (int i = 0; i < (VERTEX_SIZE - 1); i++)
			{
				pointer = (j * (VERTEX_SIZE - 1) + i) * 6;
				indices[pointer] = j * VERTEX_SIZE + i;
				indices[pointer + 1] = j * VERTEX_SIZE + i + VERTEX_SIZE;
				indices[pointer + 2] = j * VERTEX_SIZE + i + 1;
				indices[pointer + 3] = j * VERTEX_SIZE + i + 1;
				indices[pointer + 4] = j * VERTEX_SIZE + i + VERTEX_SIZE;
				indices[pointer + 5] = j * VERTEX_SIZE + i + 1 + VERTEX_SIZE;
			}
		ModelLoader loader = new ModelLoader();
		float[] normals = { 0 };
		return loader.loadModel(vertices, indices, tex_coords, normals);
	}

	public RawModel getModel()
	{
		return model;
	}

	public int getX()
	{
		return x;
	}

	public int getZ()
	{
		return z;
	}
}
