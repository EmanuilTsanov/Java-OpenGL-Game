package opengl.java.render;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.fonts.GUIText;
import opengl.java.gui.Inventory;
import opengl.java.interaction.KeyboardMaster;
import opengl.java.interaction.MouseMaster;
import opengl.java.lighting.Light;
import opengl.java.management.EntityManager;
import opengl.java.management.SRCLoader;
import opengl.java.maths.Maths;
import opengl.java.model.RawModel;
import opengl.java.model.TexturedModel;
import opengl.java.shader.FontShader;
import opengl.java.shader.MainShader;
import opengl.java.shader.OffscreenShader;
import opengl.java.shader.TerrainShader;
import opengl.java.shadows.ShadowMapMasterRenderer;
import opengl.java.terrain.Terrain;
import opengl.java.terrain.TerrainTexture;
import opengl.java.terrain.TerrainTexturepack;
import opengl.java.texture.ModelTexture;
import opengl.java.view.Camera;
import opengl.java.window.FPSCounter;

public class MainRenderer
{
	private static int framebufferID;
	private static int colorTextureID;
	private static int renderBufferID;

	private static MainShader mainShader;
	private static TerrainShader terrainShader;
	private static OffscreenShader offscreenShader;
	private static FontShader fontShader;

	private static Camera camera = Camera.getInstance();

	private static TerrainTexture backgroundTexture = new TerrainTexture(SRCLoader.loadTexture("grass").getID());
	private static TerrainTexture rTexture = new TerrainTexture(SRCLoader.loadTexture("dirt").getID());
	private static TerrainTexture gTexture = new TerrainTexture(SRCLoader.loadTexture("path").getID());
	private static TerrainTexture bTexture = new TerrainTexture(SRCLoader.loadTexture("rocks").getID());

	private static TerrainTexturepack texturepack = new TerrainTexturepack(backgroundTexture, rTexture, gTexture, bTexture);

	private static TerrainTexture blendMap = new TerrainTexture(SRCLoader.loadTexture("blendMap").getID());

	private static Terrain terrain = new Terrain(0, 0, texturepack, blendMap, "heightmap");

	private static List<Terrain> terrains = new ArrayList<Terrain>();

	private static TerrainRenderer renderer;

	private static Light sun = new Light(new Vector3f(1000000, 1500000, -1000000), new Vector3f(1.0f, 1.0f, 1.0f));

	private static HashMap<Integer, HashMap<Integer, Entity>> entityArray = EntityManager.getEntityHashMap();

	private static ShadowMapMasterRenderer smmr = new ShadowMapMasterRenderer(camera);

	public static Inventory inv = new Inventory();

	public static float x1 = 10, speed = 0.001f;

	public static void initialize()
	{
		MouseMaster.initialize();
		enableCulling();
		initShaders();
		renderer = new TerrainRenderer(terrainShader);
		processTerrain(terrain);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		bindBuffers(Display.getWidth(), Display.getHeight());
		Random rand = new Random();
		for (int i = 0; i < 1500; i++)
		{
			Entity e = Entity.pineTree.getCopy();
			float x = rand.nextFloat() * terrain.getSize();
			float z = rand.nextFloat() * terrain.getSize();
			e.setPosition(new Vector3f(x, terrain.getHeightOfTerrain(x, z), z));
			EntityManager.addEntity(e);
		}
		for (int i = 0; i < 10000; i++)
		{
			Entity e = Entity.grass.getCopy();
			float x = rand.nextFloat() * terrain.getSize();
			float z = rand.nextFloat() * terrain.getSize();
			e.setPosition(new Vector3f(x, terrain.getHeightOfTerrain(x, z), z));
			EntityManager.addEntity(e);
		}
	}

	public static void processTerrain(Terrain terrain)
	{
		terrains.add(terrain);
	}

	private static void initShaders()
	{
		fontShader = new FontShader();
		mainShader = new MainShader();
		terrainShader = new TerrainShader();
		offscreenShader = new OffscreenShader();
		loadShaders();
	}

	public static void loadShaders()
	{
		fontShader.start();
		fontShader.loadColor(new Vector3f(0, 0, 0));
		fontShader.stop();
		mainShader.start();
		mainShader.loadProjectionMatrix();
		mainShader.stop();
		terrainShader.start();
		terrainShader.loadProjectionMatrix();
		terrainShader.loadShadowMap();
		terrainShader.loadShadowDistance();
		terrainShader.loadMapSize(ShadowMapMasterRenderer.SHADOW_MAP_SIZE);
		terrainShader.stop();
		offscreenShader.start();
		offscreenShader.loadProjectionMatrix();
		offscreenShader.stop();
	}

	private static void bindBuffers(int width, int height)
	{
		framebufferID = GL30.glGenFramebuffers();
		colorTextureID = GL11.glGenTextures();
		renderBufferID = GL30.glGenRenderbuffers();

		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebufferID);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, colorTextureID);

		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_INT, (java.nio.ByteBuffer) null);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, colorTextureID, 0);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, renderBufferID);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL14.GL_DEPTH_COMPONENT24, width, height);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, renderBufferID);
		unbindBuffers();
	}

	private static void unbindBuffers()
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}

	private static void prepareScreen(float r, float g, float b)
	{
		GL11.glClearColor(r, g, b, 0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL13.glActiveTexture(GL13.GL_TEXTURE5);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getShadowMapTexture());
	}

	private static void renderEntities()
	{
		for (Map.Entry<Integer, HashMap<Integer, Entity>> outer : entityArray.entrySet())
		{
			RawModel model = TexturedModel.getTexturedModel(outer.getKey()).getRawModel();
			ModelTexture texture = TexturedModel.getTexturedModel(outer.getKey()).getTexture();
			GL30.glBindVertexArray(model.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			if (texture.isTransparent())
				disableCulling();
			mainShader.loadTextureVariables(texture);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
			for (Map.Entry<Integer, Entity> inner : outer.getValue().entrySet())
			{
				Entity currentEntity = inner.getValue();
				if (currentEntity.getPosition().x - camera.getPosition().x > Maths.getFarPlane() || camera.getPosition().x - currentEntity.getPosition().x > Maths.getFarPlane()
						|| currentEntity.getPosition().z - camera.getPosition().z > Maths.getFarPlane()
						|| camera.getPosition().z - currentEntity.getPosition().z > Maths.getFarPlane())
					continue;
				{
					float dx = (float) (Maths.getFarPlane() * Math.sin(Math.toRadians(camera.getRotation().y - 90)));
					float dy = (float) (Maths.getFarPlane() * Math.cos(Math.toRadians(camera.getRotation().y - 90)));
					float dx1 = camera.getPosition().x + dx;
					float dy1 = camera.getPosition().z - dy;
					float dx2 = camera.getPosition().x - dx;
					float dy2 = camera.getPosition().z + dy;
					float d = (currentEntity.getPosition().x - dx1) * (dy2 - dy1) - (currentEntity.getPosition().z - dy1) * (dx2 - dx1);
					if (d < 0)
						continue;
				}
				mainShader.loadTransformationMatrix(currentEntity.getPosition(), currentEntity.getRotation(), currentEntity.getScale());
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			enableCulling();
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(2);
			GL30.glBindVertexArray(0);
		}
	}

	public static void renderEntity(Entity e)
	{
		mainShader.start();
		RawModel model = TexturedModel.getTexturedModel(e.getAsset()).getRawModel();
		ModelTexture texture = TexturedModel.getTexturedModel(e.getAsset()).getTexture();
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
		mainShader.loadTransformationMatrix(e.getPosition(), e.getRotation(), e.getScale());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
		mainShader.stop();
	}

	public static void renderText(GUIText t)
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL30.glBindVertexArray(t.getModel().getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, t.getTextureID());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, t.getModel().getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	private static void renderOffScreen()
	{
		for (Map.Entry<Integer, HashMap<Integer, Entity>> outer : entityArray.entrySet())
		{
			RawModel model = TexturedModel.getTexturedModel(outer.getKey()).getRawModel();
			GL30.glBindVertexArray(model.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			for (Map.Entry<Integer, Entity> inner : outer.getValue().entrySet())
			{
				Entity currentEntity = inner.getValue();
				offscreenShader.loadTransformationMatrix(currentEntity.getPosition(), currentEntity.getRotation(), currentEntity.getScale());
				offscreenShader.loadColor(currentEntity.getColor());
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			GL20.glDisableVertexAttribArray(0);
			GL30.glBindVertexArray(0);
		}
	}

	public static Vector3f pickColor(int x, int y)
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebufferID);
		offscreenShader.start();
		prepareScreen(1, 1, 1);
		offscreenShader.loadViewMatrix(camera);
		renderOffScreen();
		offscreenShader.stop();
		ByteBuffer buffer = readScreen(x, y, 1, 1);
		unbindBuffers();
		int r = buffer.get(0) & 0xFF;
		int g = buffer.get(1) & 0xFF;
		int b = buffer.get(2) & 0xFF;

		return new Vector3f(r, g, b);
	}

	public static void takeScreenshot()
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebufferID);
		mainShader.start();
		prepareScreen(0, 1, 1);
		renderEntities();
		mainShader.stop();
		terrainShader.start();
		terrainShader.loadToShadowMapSpace(smmr.getToShadowMapSpaceMatrix());
		terrainShader.stop();
		unbindBuffers();
		// SRCLoader.saveScreenshot();
	}

	public static ByteBuffer readScreen(int x, int y, int width, int height)
	{
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		GL11.glReadPixels(x, y, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		return buffer;
	}

	public static void renderShadowMap()
	{
		smmr.render(entityArray, sun);
	}

	public static int getShadowMapTexture()
	{
		return smmr.getShadowMap();
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

	public static void render()
	{
		MouseMaster.update(camera);
		KeyboardMaster.update();
		prepareScreen(0, 1, 1);
		mainShader.start();
		mainShader.loadLight(sun);
		mainShader.loadViewMatrix(camera);
		renderEntities();
		mainShader.stop();
		terrainShader.start();
		terrainShader.loadViewMatrix(camera);
		terrainShader.loadLight(sun);
		renderer.render(terrains);
		terrainShader.stop();
		fontShader.start();
		fontShader.loadColor(new Vector3f(0, 0, 0));
		renderText(FPSCounter.getMesh());
		fontShader.stop();
		inv.update();
		inv.render();
	}
}
