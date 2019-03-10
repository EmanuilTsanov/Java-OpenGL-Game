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
import opengl.java.lighting.LightMaster;
import opengl.java.shader.EntityShader;
import opengl.java.shader.TerrainShader;
import opengl.java.terrain.Terrain;
import opengl.java.view.Camera;
import opengl.java.window.FPSCounter;

public class MainRenderer
{
	private EntityShader entityShader;
	private TerrainShader terrainShader;

	private EntityRenderer entityRenderer = new EntityRenderer();
	private TerrainRenderer terrainRenderer = new TerrainRenderer();
	private TextRenderer textRenderer = new TextRenderer();
	private LightMaster master = new LightMaster();

	private Terrain terrain = new Terrain(0, 0, "grass");

	public Inventory inv = new Inventory();

	private static final float R = 0.5f, G = 0.5f, B = 0.5f;

	public MainRenderer()
	{
		setupShaders();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		fillWithEntities();
		Camera.setPosition(500, 50, 500);
		Camera.setRotation(40, 0, 0);
	}

	private void setupShaders()
	{
		entityShader = new EntityShader();
		terrainShader = new TerrainShader();

		entityShader.start();
		entityShader.loadProjectionMatrix();
		entityShader.stop();
		terrainShader.start();
		terrainShader.loadProjectionMatrix();
		terrainShader.stop();
	}

	public void fillWithEntities()
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
		for (int i = 0; i < 1500; i++)
		{
			Entity e = new Entity(EntityBase.SNOWMAN);
			float x = rand.nextFloat() * terrain.getSize();
			float z = rand.nextFloat() * terrain.getSize();
			e.setPosition(new Vector3f(x, 0, z));
		}
		for (int i = 0; i < 1500; i++)
		{
			Entity e = new Entity(EntityBase.BENCH);
			float x = rand.nextFloat() * terrain.getSize();
			float z = rand.nextFloat() * terrain.getSize();
			e.setPosition(new Vector3f(x, 0, z));
		}
		for (int i = 0; i < 1500; i++)
		{
			Entity e = new Entity(EntityBase.TABLE);
			float x = rand.nextFloat() * terrain.getSize();
			float z = rand.nextFloat() * terrain.getSize();
			e.setPosition(new Vector3f(x, 0, z));
		}
	}

	private void prepareScreen()
	{
		GL11.glClearColor(R, G, B, 0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL13.glActiveTexture(GL13.GL_TEXTURE5);
	}

	public void update()
	{
		MouseMaster.update();
		KeyboardMaster.update();
		inv.update();
	}

	public void render()
	{
		prepareScreen();

		terrainShader.start();
		terrainShader.loadSkyColor(R, G, B);
		terrainShader.loadLights(LightMaster.lights);
		terrainShader.loadViewMatrix();
		terrainRenderer.render(terrain, terrainShader);
		terrainShader.stop();
		for (int i = 0; i < LightMaster.lights.size(); i++)
		{
			Entity.getEntities().get(EntityBase.PINE_TREE).get(i).setPosition(LightMaster.lights.get(i).getPosition());
		}
		entityShader.start();
		entityShader.loadSkyColor(R, G, B);
		entityShader.loadLights(LightMaster.lights);
		entityShader.loadViewMatrix();
		entityRenderer.render(entityShader);
		entityShader.stop();

		textRenderer.render(FPSCounter.getMesh());
		inv.render();
	}
}
