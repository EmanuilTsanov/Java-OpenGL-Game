package opengl.java.terrain;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
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
		texture = FileManager.loadTexture("snowT");
		generateCells();
	}
	
	public static Terrain getInstance() {
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
		System.out.println(cells.size());
	}

	private String formKey(int x, int y)
	{
		return x + "/" + y;
	}

	public boolean isOccupied(Entity e)
	{
		Vector3f ePos = e.getPosition();
		int xArr = (int) ePos.getX() / TerrainGenerator.getQuadSize();
		int yArr = (int) ePos.getZ() / TerrainGenerator.getQuadSize();
		for (int y = 0; y < e.getArea().y; y++)
		{
			for (int x = 0; x < e.getArea().x; x++)
			{
				if(!cells.get(formKey(xArr+x, yArr+y)).isOccupied()) {
					return true;
				}
			}
		}
		return false;
	}

	public void setOccupied(Entity e)
	{
		Vector3f ePos = e.getPosition();
		int xArr = (int) ePos.getX() / TerrainGenerator.getQuadSize();
		int yArr = (int) ePos.getZ() / TerrainGenerator.getQuadSize();
		for (int y = 0; y < e.getArea().y; y++)
		{
			for (int x = 0; x < e.getArea().x; x++)
			{
				cells.get(formKey(xArr+x, yArr+y)).setOccupied(true);
			}
		}
	}

	public TerrainCell getCell(int x, int y)
	{
		return cells.get(formKey(x, y));
	}

	public Vector2f getCellPos(float x, float y)
	{
		Vector2f vec = new Vector2f(x / TerrainGenerator.getQuadSize(), y / TerrainGenerator.getQuadSize());
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
