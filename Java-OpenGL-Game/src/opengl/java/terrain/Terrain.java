package opengl.java.terrain;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;

import opengl.java.management.FileManager;
import opengl.java.model.RawModel;
import opengl.java.texture.BaseTexture;

public class Terrain
{
	private Vector2f position;

	private RawModel model;

	private BaseTexture texture;

	private HashMap<String, TerrainCell> cells;

	private static Terrain singleton = new Terrain();

	public Terrain()
	{
		this.position = new Vector2f(0, 0);
		this.model = TerrainGenerator.generateTerrain();
		texture = FileManager.loadTexture("grassT");
		generateCells();
	}

	public static Terrain getInstance()
	{
		return singleton;
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

	public TerrainCell getCell(int x, int y)
	{
		return cells.get(formKey(x, y));
	}

	public Vector2f getCellPos(float x, float y)
	{
		Vector2f vec = new Vector2f((int) (x / TerrainGenerator.getQuadSize()), (int) (y / TerrainGenerator.getQuadSize()));
		return vec;
	}

	public Vector2f getPosition()
	{
		return position;
	}

	public RawModel getModel()
	{
		return model;
	}

	public BaseTexture getTexture()
	{
		return texture;
	}
}
