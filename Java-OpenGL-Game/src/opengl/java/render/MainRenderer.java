package opengl.java.render;

import java.nio.ByteBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.entity.EntityBase;
import opengl.java.entity.EntityRenderer;
import opengl.java.fonts.GUIText;
import opengl.java.gui.Inventory;
import opengl.java.interaction.KeyboardMaster;
import opengl.java.interaction.MouseMaster;
import opengl.java.management.SRCLoader;
import opengl.java.shader.FontShader;
import opengl.java.shader.OffscreenShader;
import opengl.java.shadows.ShadowMapMasterRenderer;
import opengl.java.terrain.Terrain;
import opengl.java.terrain.TerrainRenderer;
import opengl.java.terrain.TerrainTexture;
import opengl.java.terrain.TerrainTexturepack;
import opengl.java.view.Camera;
import opengl.java.window.FPSCounter;

public class MainRenderer
{
	private static OffscreenShader offscreenShader;
	private static FontShader fontShader;

	private static TerrainTexture backgroundTexture = new TerrainTexture(SRCLoader.getTexture("grassT").getID());
	private static TerrainTexture rTexture = new TerrainTexture(SRCLoader.getTexture("dirt").getID());
	private static TerrainTexture gTexture = new TerrainTexture(SRCLoader.getTexture("path").getID());
	private static TerrainTexture bTexture = new TerrainTexture(SRCLoader.getTexture("rocks").getID());

	private static TerrainTexturepack texturepack = new TerrainTexturepack(backgroundTexture, rTexture, gTexture, bTexture);
	private static TerrainTexture blendMap = new TerrainTexture(SRCLoader.getTexture("blendMap").getID());
	private static Terrain terrain = new Terrain(0, 0, texturepack, blendMap, "heightmap");

	private static ShadowMapMasterRenderer shadowRenderer = new ShadowMapMasterRenderer();

	public static Inventory inv = new Inventory();

	public static void initialize()
	{
		Camera.initialize(new Vector3f(500, 80, 500), new Vector3f(55, 0, 0));
		MouseMaster.initialize();
		TerrainRenderer.initialize();
		EntityRenderer.initialize();
		initShaders();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		fillWithEntities();
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
			e.setPosition(new Vector3f(x, terrain.getHeightOfTerrain(x, z), z));
		}
	}

	private static void initShaders()
	{
		fontShader = new FontShader();
		offscreenShader = new OffscreenShader();
		loadShaders();
	}

	public static void loadShaders()
	{
		fontShader.start();
		fontShader.loadColor(new Vector3f(0, 0, 0));
		fontShader.stop();
		offscreenShader.start();
		offscreenShader.loadProjectionMatrix();
		offscreenShader.stop();
	}
	
	private static void prepareScreen(float r, float g, float b)
	{
		GL11.glClearColor(r, g, b, 0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL13.glActiveTexture(GL13.GL_TEXTURE5);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, shadowRenderer.getShadowMap());
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
	public static ByteBuffer readScreen(int x, int y, int width, int height)
	{
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		GL11.glReadPixels(x, y, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		return buffer;
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
		TerrainRenderer.render(terrain);
		EntityRenderer.renderEntities();
		fontShader.start();
		fontShader.loadColor(new Vector3f(1, 1, 0));
		renderText(FPSCounter.getMesh());
		fontShader.stop();
		inv.render();
	}
}
