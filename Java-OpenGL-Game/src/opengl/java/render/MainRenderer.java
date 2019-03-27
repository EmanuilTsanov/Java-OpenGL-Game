package opengl.java.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.entity.EntityBase;
import opengl.java.interaction.KeyboardMaster;
import opengl.java.interaction.MouseMaster;
import opengl.java.lighting.Light;
import opengl.java.lighting.Lights;
import opengl.java.loader.ImageLoader;
import opengl.java.loader.ModelLoader;
import opengl.java.particles.ParticleManager;
import opengl.java.particles.ParticleSystem;
import opengl.java.particles.ParticleTexture;
import opengl.java.shader.EntityShader;
import opengl.java.shader.TerrainShader;
import opengl.java.shadows.ShadowMapMasterRenderer;
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
	private ModelLoader loader;
	private ParticleTexture texture = new ParticleTexture(ImageLoader.loadTexture("star2"), 4);
	private ParticleSystem sys = new ParticleSystem(texture, 60, 15, 0.1f, 1.6f);

	private ShadowMapMasterRenderer shadowRenderer;

	private Terrain terrain = new Terrain(0, 0, "grass");

	public static final float RED = 0.5f, GREEN = 0.5f, BLUE = 0.5f;

	public MainRenderer()
	{
		setupShaders();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		fillWithEntities();
		Camera.setPosition(500, 50, 500);
		Camera.setRotation(40, 0, 0);
		loader = new ModelLoader();
		ParticleManager.initialize(loader);
	}

	private void setupShaders()
	{
		entityShader = new EntityShader();
		terrainShader = new TerrainShader();
		shadowRenderer = new ShadowMapMasterRenderer();

		entityShader.start();
		entityShader.loadProjectionMatrix();
		entityShader.stop();
		terrainShader.start();
		terrainShader.loadProjectionMatrix();
		terrainShader.connectTextureUnits();
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
	}

	private void prepareScreen()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(RED, GREEN, BLUE, 0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL13.glActiveTexture(GL13.GL_TEXTURE5);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, shadowRenderer.getShadowMap());
	}

	public void update()
	{
		MouseMaster.update();
		KeyboardMaster.update();
		ParticleManager.update();
		sys.generateParticles(new Vector3f(500, 0, 500));
	}

	public static void enableCulling()
	{
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public static void disableCulling()
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	public void renderShadowMap(HashMap<EntityBase, ArrayList<Entity>> entities, Light sun)
	{
		shadowRenderer.render(entities, sun);
	}

	public void render()
	{
		renderShadowMap(Entity.getEntities(), Light.SUN);
		prepareScreen();
		terrainShader.start();
		terrainShader.loadSkyColor(RED, GREEN, BLUE);
		terrainShader.loadLights(Lights.lights);
		terrainShader.loadViewMatrix();
		terrainRenderer.render(terrain, terrainShader, shadowRenderer.getToShadowMapSpaceMatrix());
		terrainShader.stop();
		for (int i = 0; i < Lights.lights.size(); i++)
		{
			Entity.getEntities().get(EntityBase.PINE_TREE).get(i).setPosition(Lights.lights.get(i).getPosition());
		}
		entityShader.start();
		entityShader.loadSkyColor(RED, GREEN, BLUE);
		entityShader.loadLights(Lights.lights);
		entityShader.loadViewMatrix();
		entityRenderer.render(entityShader);
		entityShader.stop();
		ParticleManager.renderParticles();
		textRenderer.render(FPSCounter.getMesh());
	}

	public void destroy()
	{

	}
}
