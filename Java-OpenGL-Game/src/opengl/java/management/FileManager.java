package opengl.java.management;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import opengl.java.files.FileSRC;
import opengl.java.loader.ModelLoader;
import opengl.java.logger.Logger;
import opengl.java.model.Model;
import opengl.java.render.GameRenderer;
import opengl.java.texture.ModelTexture;
import opengl.java.window.Window;

public class FileManager
{

	public static ArrayList<String> readTextFile(String path, String fileName, String extension)
	{
		ArrayList<String> lines = new ArrayList<String>();

		try (BufferedReader stream = new BufferedReader(new FileReader(new File(path + fileName + FileSRC.DOT + extension))))
		{
			String line;
			while ((line = stream.readLine()) != null)
			{
				lines.add(line);
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File '" + fileName + FileSRC.DOT + extension + "' + not found");
		}
		catch (Exception e)
		{
			System.out.println("Error reading file '" + fileName + FileSRC.DOT + extension + "'.\nInfoLog: ");
			e.printStackTrace();
		}
		return lines;
	}

	public static Model loadRawModel(String fileName)
	{
		ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
		ArrayList<Vector2f> texCoords = new ArrayList<Vector2f>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
		float[] verticesArr = null;
		float[] texturesArr = null;
		float[] normalsArr = null;
		int[] indicesArr = null;

		ArrayList<String> lines = readTextFile(FileSRC.MODELS_FOLDER, fileName, FileSRC.MODEL_EXTENSION);

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
		return ModelLoader.getInstance().loadModel(verticesArr, indicesArr, texturesArr, normalsArr);
	}

	public static void processFace(String[] vertexData, ArrayList<Integer> indices, ArrayList<Vector2f> texCoords, float[] texturesArr, ArrayList<Vector3f> normals, float[] normalsArr)
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

	public static ModelTexture loadTexture(String fileName)
	{
		Texture tex = null;
		try
		{
			tex = TextureLoader.getTexture("PNG", new FileInputStream(FileSRC.TEXTURES_FOLDER + fileName + "." + FileSRC.TEXTURE_EXTENSION));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0);
			if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic)
			{
				float amount = Math.min(4f, GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
			}
			else
			{

			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return new ModelTexture(tex.getTextureID());
	}

	public static ModelTexture loadTexture(String path, String fileName)
	{
		Texture tex = null;
		try
		{
			tex = org.newdawn.slick.opengl.TextureLoader.getTexture("png", new FileInputStream(path + fileName + "." + FileSRC.TEXTURE_EXTENSION));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return new ModelTexture(tex.getTextureID());
	}

	public static void saveScreenshot()
	{
		int width = Window.getWidth();
		int height = Window.getHeight();
		ByteBuffer buffer = GameRenderer.getInstance().readScreen(0, 0, width, height);
		File file = new File(Logger.getDate() + "_" + Logger.getFormattedTime() + FileSRC.DOT + FileSRC.TEXTURE_EXTENSION);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				int i = (x + (width * y)) * 4;
				int r = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int b = buffer.get(i + 2) & 0xFF;
				image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
			}
		}

		try
		{
			ImageIO.write(image, FileSRC.TEXTURE_EXTENSION, file);
			System.out.println("SCREENSHOT");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}