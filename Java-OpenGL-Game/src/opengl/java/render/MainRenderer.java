package opengl.java.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.entity.EntityBase;
import opengl.java.fonts.GUIText;
import opengl.java.gui.Inventory;
import opengl.java.interaction.KeyboardMaster;
import opengl.java.interaction.MouseMaster;
import opengl.java.shader.FontShader;
import opengl.java.shader.OffscreenShader;
import opengl.java.terrain.Terrain;
import opengl.java.view.Camera;
import opengl.java.window.FPSCounter;

public class MainRenderer
{
	private static FontShader fontShader = new FontShader();
	private static OffscreenShader offscreenShader = new OffscreenShader();

	private static Terrain terrain = new Terrain(0, 0, "grass");

	private static EntityRenderer entityRenderer = new EntityRenderer();
	private static TerrainRenderer terrainRenderer = new TerrainRenderer();

	public static Inventory inv = new Inventory();

	public static void initialize()
	{
		initShaders();
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
//		for (int i = 0; i < 10000; i++)
//		{
//			Entity e = new Entity(EntityBase.GRASS);
//			float x = rand.nextFloat() * terrain.getSize();
//			float z = rand.nextFloat() * terrain.getSize();
//			e.setPosition(new Vector3f(x, 0, z));
//		}
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
		fontShader.start();
		fontShader.loadColor(new Vector3f(1, 1, 0));
		renderText(FPSCounter.getMesh());
		fontShader.stop();
		inv.render();
	}
}
