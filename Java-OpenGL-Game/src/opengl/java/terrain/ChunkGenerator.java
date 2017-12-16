package opengl.java.terrain;

import org.lwjgl.util.vector.Vector2f;

import opengl.java.loader.ModelLoader;
import opengl.java.model.RawModel;

public class ChunkGenerator
{
	public static final int VERTEX_SIZE = 4;
	public static final int QUAD_SIZE = 20;

	public static RawModel generateChunk()
	{

		float[] vertices = new float[VERTEX_SIZE * VERTEX_SIZE * 3];
		int[] indices = new int[(VERTEX_SIZE - 1) * (VERTEX_SIZE - 1) * 6];
		float[] texCoords = new float[VERTEX_SIZE * VERTEX_SIZE * 2];

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
				texCoords[tPointer] = (float) j / (float) (VERTEX_SIZE - 1);
				texCoords[tPointer + 1] = (float) i / (float) (VERTEX_SIZE - 1);
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
		return loader.loadModel(vertices, indices, texCoords, normals);
	}

	private static float toWorldSpace(float coord)
	{
		return coord * VERTEX_SIZE * QUAD_SIZE - QUAD_SIZE * coord;
	}

	public static Vector2f getWorldPosition(float x, float y)
	{
		return new Vector2f(toWorldSpace(x), toWorldSpace(y));
	}

	public static int getQuadSize()
	{
		return QUAD_SIZE;
	}
}
