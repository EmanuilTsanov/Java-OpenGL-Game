package opengl.java.terrain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.loader.ModelLoader;
import opengl.java.model.RawModel;

public class Terrain
{
	private static final float SIZE = 1024;
	private static final float MAX_HEIGHT = 0;
	private static final float MAX_PIXEL_COLOR = 256 * 256 * 256;

	private float x, z;
	private RawModel model;
	private TerrainTexturepack texturepack;
	private TerrainTexture blendMap;
	
	private float[][]heights;

	public Terrain(int gridX, int gridZ, ModelLoader loader, TerrainTexturepack texturepack, TerrainTexture blendMap, String heightMap)
	{
		this.texturepack = texturepack;
		this.blendMap = blendMap;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.model = generateTerrain(loader, heightMap);
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

	public TerrainTexturepack getTexturepack()
	{
		return texturepack;
	}

	public TerrainTexture getBlendMap()
	{
		return blendMap;
	}

	public float getHeightOfTerrain(float worldX, float worldZ)
	{
		float terrainX = worldX - this.x;
		float terrainZ = worldZ - this.z;
		float gridSquareSize = SIZE / ((float) heights.length - 1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		if (gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0)
		{
			return 0;
		}
		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		float answer;
		if (xCoord <= (1 - zCoord))
		{
			answer = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(0, heights[gridX][gridZ + 1], 1),
					new Vector2f(xCoord, zCoord));
		}
		else
		{
			answer = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1, heights[gridX + 1][gridZ + 1], 1),
					new Vector3f(0, heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		}
		return answer;
	}

	private RawModel generateTerrain(ModelLoader loader, String heightMap)
	{
		BufferedImage image = null;

		try
		{
			image = ImageIO.read(new File("assets/textures/" + heightMap + ".png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		int vertexCount = image.getHeight();
		heights = new float[vertexCount][vertexCount];
		int count = vertexCount * vertexCount;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (vertexCount - 1) * (vertexCount - 1)];
		int vertexPointer = 0;
		for (int i = 0; i < vertexCount; i++)
		{
			for (int j = 0; j < vertexCount; j++)
			{
				vertices[vertexPointer * 3] = (float) j / ((float) vertexCount - 1) * SIZE;
				float height = getHeight(j, i, image);
				heights[j][i] = height;
				vertices[vertexPointer * 3 + 1] = height;
				vertices[vertexPointer * 3 + 2] = (float) i / ((float) vertexCount - 1) * SIZE;
				Vector3f normal = calculateNormal(j, i, image);
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
				textureCoords[vertexPointer * 2] = (float) j / ((float) vertexCount - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) vertexCount - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for (int gz = 0; gz < vertexCount - 1; gz++)
		{
			for (int gx = 0; gx < vertexCount - 1; gx++)
			{
				int topLeft = (gz * vertexCount) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * vertexCount) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadModel(vertices, indices, textureCoords, normals);
	}

	private Vector3f calculateNormal(int x, int z, BufferedImage image)
	{
		float heightL = getHeight(x - 1, z, image);
		float heightR = getHeight(x + 1, z, image);
		float heightD = getHeight(x, z - 1, image);
		float heightU = getHeight(x, z + 1, image);
		Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
		normal.normalise();
		return normal;
	}

	private float getHeight(int x, int y, BufferedImage image)
	{
		if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight())
			return 0;
		float height = image.getRGB(x, y);
		height += MAX_PIXEL_COLOR / 2f;
		height /= MAX_PIXEL_COLOR / 2f;
		height *= MAX_HEIGHT;
		return height;
	}
	
	public float getSize() {
		return SIZE;
	}
}
