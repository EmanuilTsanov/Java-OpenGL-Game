package opengl.java.collision;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;

public class CollisionMap {
	private HashMap<String, CollisionCell> colMap;

	public CollisionMap(int size) {
		colMap = new HashMap<String, CollisionCell>();
		fillCollisionMap(size);
	}

	private void fillCollisionMap(int size) {
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				colMap.put(x + "/" + y, new CollisionCell(new Vector2f(x, y)));
			}
		}
	}
}
