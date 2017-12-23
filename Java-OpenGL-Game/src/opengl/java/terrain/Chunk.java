package opengl.java.terrain;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;

import opengl.java.collision.CollisionCell;
import opengl.java.model.RawModel;

public class Chunk {
	private Vector2f position;

	public RawModel model;
	private HashMap<String, CollisionCell> colMap;

	private Vector2f highlightedCell;

	public Chunk(float x, float y) {
		this.position = ChunkGenerator.getWorldPosition(x, y);
		this.model = ChunkGenerator.generateChunk();
		colMap = new HashMap<String, CollisionCell>();
		fillCollisionMap(ChunkGenerator.getVertexSize() - 1);
	}

	public RawModel getModel() {
		return model;
	}

	public Vector2f getPosition() {
		return position;
	}

	private void fillCollisionMap(int size) {
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				colMap.put(x + "/" + y, new CollisionCell(new Vector2f(x, y)));
			}
		}
	}
}
