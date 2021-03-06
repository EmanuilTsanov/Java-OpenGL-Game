package opengl.java.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.model.RawModel;

public class OBJLoader
{
	private static HashMap<String, RawModel> models = new HashMap<String, RawModel>();

	public static RawModel loadModel(String fileName)
	{
		ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
		ArrayList<Vector2f> texCoords = new ArrayList<Vector2f>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
		float[] verticesArr = null;
		float[] texturesArr = null;
		float[] normalsArr = null;
		int[] indicesArr = null;

		ArrayList<String> lines = readTextFile("assets/models/", fileName, ".obj");

		int currentLine = 0;
		String line;
		while (true)
		{
			line = lines.get(currentLine);
			if (line.startsWith("v "))
			{
				String[] tokens = line.split("\\s+");
				float x = Float.parseFloat(tokens[1]);
				float y = Float.parseFloat(tokens[2]);
				float z = Float.parseFloat(tokens[3]);
				Vector3f vector = new Vector3f(x, y, z);
				vertices.add(vector);
			}
			else if (line.startsWith("vt "))
			{
				String[] tokens = line.split("\\s+");
				Vector2f textureCoords = new Vector2f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
				texCoords.add(textureCoords);
			}
			else if (line.startsWith("vn "))
			{
				String[] tokens = line.split("\\s+");
				Vector3f normal = new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]));
				normals.add(normal);
			}
			else if (line.startsWith("f "))
			{
				texturesArr = new float[vertices.size() * 2];
				normalsArr = new float[vertices.size() * 3];
				break;
			}
			currentLine++;
		}
		while (line != null)
		{
			if (!line.startsWith("f "))
			{
				line = currentLine == lines.size() - 1 ? null : lines.get(++currentLine);
				continue;
			}
			String[] tokens = line.split("\\s+");

			String[] vertex1 = tokens[1].split("/");
			String[] vertex2 = tokens[2].split("/");
			String[] vertex3 = tokens[3].split("/");

			processFace(vertex1, indices, texCoords, texturesArr, normals, normalsArr);
			processFace(vertex2, indices, texCoords, texturesArr, normals, normalsArr);
			processFace(vertex3, indices, texCoords, texturesArr, normals, normalsArr);
			line = currentLine == lines.size() - 1 ? null : lines.get(++currentLine);
		}
		verticesArr = new float[vertices.size() * 3];
		indicesArr = new int[indices.size()];

		int vertexPointer = 0;
		for (Vector3f vertex : vertices)
		{
			verticesArr[vertexPointer++] = vertex.x;
			verticesArr[vertexPointer++] = vertex.y;
			verticesArr[vertexPointer++] = vertex.z;
		}
		for (int i = 0; i < indices.size(); i++)
		{
			indicesArr[i] = indices.get(i);
		}
		RawModel model = ModelLoader.loadToVAO(verticesArr, indicesArr, texturesArr, normalsArr);
		models.put(fileName, model);
		return model;
	}

	private static void processFace(String[] vertexData, ArrayList<Integer> indices, ArrayList<Vector2f> texCoords, float[] texturesArr, ArrayList<Vector3f> normals,
			float[] normalsArr)
	{
		int vertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(vertexPointer);
		Vector2f texture = texCoords.get(Integer.parseInt(vertexData[1]) - 1);
		texturesArr[vertexPointer * 2] = texture.x;
		texturesArr[vertexPointer * 2 + 1] = 1 - texture.y;
		Vector3f normal = normals.get(Integer.parseInt(vertexData[2]) - 1);
		normalsArr[vertexPointer * 3] = normal.x;
		normalsArr[vertexPointer * 3 + 1] = normal.y;
		normalsArr[vertexPointer * 3 + 2] = normal.z;
	}

	private static ArrayList<String> readTextFile(String path, String fileName, String extension)
	{
		ArrayList<String> lines = new ArrayList<String>();

		try (BufferedReader stream = new BufferedReader(new FileReader(new File(new StringBuilder().append(path).append(fileName).append(extension).toString()))))
		{
			String line;
			while ((line = stream.readLine()) != null)
			{
				lines.add(line);
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File '" + fileName + extension + "' was not found.");
		}
		catch (Exception e)
		{
			System.out.println("Error reading file '" + fileName + extension + "'.\nInfoLog: ");
			e.printStackTrace();
		}
		return lines;
	}
}
