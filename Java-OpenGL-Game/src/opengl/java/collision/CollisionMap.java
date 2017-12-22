package opengl.java.collision;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;

public class CollisionMap {
	private HashMap<String, CollisionCell> colMap;
	private Vector2f selectedCells = new Vector2f(0, 0);

	public CollisionMap(int size) {
		colMap = new HashMap<String, CollisionCell>();
		fillCollisionMap(size - 1);
	}

	private void fillCollisionMap(int size) {
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				colMap.put(x + "/" + y, new CollisionCell(new Vector2f(x, y)));
			}
		}
	}
}
