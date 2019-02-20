package opengl.java.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.entity.EntityBase;
import opengl.java.gui.Inventory;
import opengl.java.interaction.KeyboardMaster;
import opengl.java.interaction.MouseMaster;
import opengl.java.terrain.Terrain;
import opengl.java.view.Camera;
import opengl.java.window.FPSCounter;

public class MainRenderer
{
	private static EntityRenderer entityRenderer = new EntityRenderer();
	private static TerrainRenderer terrainRenderer = new TerrainRenderer();
	private static TextRenderer textRenderer = new TextRenderer();

	private static Terrain terrain = new Terrain(0, 0, "grass");

	public static Inventory inv = new Inventory();

	public static void initialize()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		fillWithEntities();
		Camera.setPosition(500, 50, 500);
		Camera.setRotation(40, 0, 0);
	}

	public static void fillWithEntities()
	{
		Random rand = new Random();
		for (int i = 0; i < 1500; i++)
		{
			Entity e = new Entity(EntityBase.PINE_TREE);
			float x = rand.nextFloat() * terrain.getSize();
			float z = rand.nextFloat() * terrain.getSize();
			e.setPosition(new Vector3f(x, 0, z));
		}
		 for (int i = 0; i < 10000; i++)
		 {
		 Entity e = new Entity(EntityBase.GRASS);
		 float x = rand.nextFloat() * terrain.getSize();
		 float z = rand.nextFloat() * terrain.getSize();
		 e.setPosition(new Vector3f(x, 0, z));
		 }
	}

	private static void prepareScreen(float r, float g, float b)
	{
		GL11.glClearColor(r, g, b, 0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL13.glActiveTexture(GL13.GL_TEXTURE5);
	}

	public static void update()
	{
		MouseMaster.update();
		KeyboardMaster.update();
		inv.update();
	}

	public static void render()
	{
		prepareScreen(0, 1, 1);
		terrainRenderer.render(terrain);
		entityRenderer.render();
		textRenderer.render(FPSCounter.getMesh());
		inv.render();
	}
}
