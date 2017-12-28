package opengl.java.management;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.EntityManager;
import opengl.java.lighting.Light;
import opengl.java.render.GameRenderer;
import opengl.java.terrain.Terrain;
import opengl.java.terrain.TerrainGenerator;
import opengl.java.view.Camera;

public class GameManager {

	private Light sun;

	private Camera camera;

	private Terrain terrain;

	// private MousePicker picker;

	private EntityManager manager;

	private GameRenderer renderer;

	public GameManager() {

		sun = new Light(new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(1.0f, 1.0f, 1.0f));
		Vector2f m = TerrainGenerator.getMidPoint();
		camera = new Camera(new Vector3f(m.x, 15, m.y), 35, 45, 45);
		terrain = new Terrain();
		// picker = new MousePicker(Maths.getProjectionMatrix(), camera);
		manager = new EntityManager();
		renderer = new GameRenderer(manager.loadEntities(), terrain, camera, sun);
	}

	public void update() {
		renderer.render();
		camera.update();
	}
}
