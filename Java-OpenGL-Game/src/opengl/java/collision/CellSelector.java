package opengl.java.collision;

import org.lwjgl.util.vector.Vector2f;

import opengl.java.loader.ModelLoader;
import opengl.java.model.RawModel;
import opengl.java.terrain.TerrainGenerator;

public class CellSelector
{

	private static RawModel model;
	static float[] vertices = { 0.0f, 0.0f, TerrainGenerator.getQuadSize(), 0.0f, 0.0f, 0.0f, TerrainGenerator.getQuadSize(), 0.0f, 0.0f, TerrainGenerator.getQuadSize(), 0.0f,
			TerrainGenerator.getQuadSize() };
	static int[] indices = { 0, 1, 3, 3, 1, 2 };
	static float[] texCoords = { 0 };
	static float[] normals = { 0 };

	private Vector2f highlightedCell;

	public CellSelector()
	{
		loadModel();
	}

	public static void loadModel()
	{
		ModelLoader loader = new ModelLoader();
		model = loader.loadModel(vertices, indices, texCoords, normals);
	}

	public RawModel getModel()
	{
		return model;
	}

	public void highlight(CollisionCell cell)
	{
		if (cell == null)
		{
			System.out.println("null");
		}
		else
			highlightedCell = cell.getPosition();
	}

	public Vector2f getHighlightedCell()
	{
		return highlightedCell;
	}
}
