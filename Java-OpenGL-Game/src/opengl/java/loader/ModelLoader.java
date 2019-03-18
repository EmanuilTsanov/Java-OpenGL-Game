package opengl.java.loader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import opengl.java.model.RawModel;

public class ModelLoader
{
	private static List<Integer> vaoList = new ArrayList<Integer>();
	private static List<Integer> vboList = new ArrayList<Integer>();

	public static RawModel loadFonts(float[] vertices, float[] textureCoords)
	{
		int vaoID = createVAO();
		storeFloatsInVBO(0, 2, vertices);
		storeFloatsInVBO(1, 2, textureCoords);
		unbindVAO();
		return new RawModel(vaoID, vertices.length / 2);
	}

	public static RawModel loadModel(float[] vertices, int[] indices, float[] textureCoords, float[] normals)
	{
		int vaoID = createVAO();
		storeIntsInVBO(indices);
		storeFloatsInVBO(0, 3, vertices);
		storeFloatsInVBO(1, 2, textureCoords);
		storeFloatsInVBO(2, 3, normals);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}

	public static RawModel loadToVAO(float[] vertices, int dimensions)
	{
		int vaoID = createVAO();
		storeFloatsInVBO(0, dimensions, vertices);
		unbindVAO();
		return new RawModel(vaoID, vertices.length / dimensions);
	}

	private static int createVAO()
	{
		int vaoID = GL30.glGenVertexArrays();
		vaoList.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}

	private static void storeFloatsInVBO(int attribID, int attribSize, float[] data)
	{
		int vboID = GL15.glGenBuffers();
		vboList.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer floatBuffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attribID, attribSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	private static void storeIntsInVBO(int[] data)
	{
		int vboID = GL15.glGenBuffers();
		vboList.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer intBuffer = storeDataInIntBuffer(data);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, intBuffer, GL15.GL_STATIC_DRAW);
	}

	private static FloatBuffer storeDataInFloatBuffer(float[] data)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private static IntBuffer storeDataInIntBuffer(int[] data)
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public static void destroy()
	{
		for (int vao : vaoList)
			GL30.glDeleteVertexArrays(vao);
		for (int vbo : vboList)
			GL15.glDeleteBuffers(vbo);
	}

	private static void unbindVAO()
	{
		GL30.glBindVertexArray(0);
	}
}
