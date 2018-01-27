package opengl.java.terrain;

import java.util.Random;

import opengl.java.loader.ModelLoader;
import opengl.java.model.Model;

public class TerrainGenerator
{
	private static final int VERTEX_SIZE = 128;
	private static final int QUAD_SIZE = 2;

	private static Random rand = new Random();

	private static Model terrainMesh;

	private static Model genTerrainMesh()
	{
		int pointer;
		int tPointer;

		float[] vertices = new float[VERTEX_SIZE * VERTEX_SIZE * 3];
		int[] indices = new int[(VERTEX_SIZE - 1) * (VERTEX_SIZE - 1) * 6];
		float[] texCoords = new float[VERTEX_SIZE * VERTEX_SIZE * 2];

		for (int i = 0; i < VERTEX_SIZE; i++)
		{
			for (int j = 0; j < VERTEX_SIZE; j++)
			{
				pointer = (i * VERTEX_SIZE + j) * 3;
				vertices[pointer] = j * QUAD_SIZE;
				vertices[pointer + 1] = 0;
				vertices[pointer + 2] = i * QUAD_SIZE;
				tPointer = (i * VERTEX_SIZE + j) * 2;
				texCoords[tPointer] = (float) j / (float) (VERTEX_SIZE - 1);
				texCoords[tPointer + 1] = (float) i / (float) (VERTEX_SIZE - 1);
			}
		}
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
		float[] normals = { 0 };
		return ModelLoader.getInstance().loadModel(vertices, indices, texCoords, normals);
	}

	public static Model getTerrainMesh()
	{
		if (terrainMesh == null)
		{
			terrainMesh = genTerrainMesh();
		}
		return terrainMesh;
	}

	public static int getQuadSize()
	{
		return QUAD_SIZE;
	}

	public static int getVertexSize()
	{
		return VERTEX_SIZE;
	}

	public static int getFullSize()
	{
		return VERTEX_SIZE * QUAD_SIZE;
	}

	public static float genRandTerrainPos()
	{
		float result = rand.nextFloat() * (TerrainGenerator.VERTEX_SIZE - 1) * TerrainGenerator.QUAD_SIZE;
		return result;
	}
}
