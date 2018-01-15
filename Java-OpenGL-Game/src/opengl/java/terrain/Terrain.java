package opengl.java.terrain;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;

import opengl.java.management.FileManager;
import opengl.java.model.RawModel;
import opengl.java.texture.BaseTexture;

public class Terrain
{
	private Vector2f position;

	private RawModel terrainMesh;
	private BaseTexture terrainTexture;

	private HashMap<String, TerrainCell> cells;

	private static Terrain singleton = new Terrain();

	public Terrain()
	{
		this.position = new Vector2f(0, 0);
		this.terrainMesh = TerrainGenerator.getTerrainMesh();
		terrainTexture = FileManager.loadTexture("grassT");
		generateCells();
	}

	private void generateCells()
	{
		cells = new HashMap<String, TerrainCell>();
		int s = TerrainGenerator.getVertexSize() / TerrainGenerator.getQuadSize();
		for (int y = 0; y < s; y++)
		{
			for (int x = 0; x < s; x++)
			{
				TerrainCell cell = new TerrainCell();
				cells.put(formKey(x, y), cell);
			}
		}
	}

	private String formKey(int x, int y)
	{
		return x + "/" + y;
	}

	public static Terrain getInstance()
	{
		return singleton;
	}

	public Vector2f getCellPosition(float x, float y)
	{
		Vector2f vec = new Vector2f((int) (x / TerrainGenerator.getQuadSize()), (int) (y / TerrainGenerator.getQuadSize()));
		return vec;
	}

	public Vector2f getPosition()
	{
		return position;
	}

	public RawModel getMesh()
	{
		return terrainMesh;
	}

	public BaseTexture getTexture()
	{
		return terrainTexture;
	}
}
