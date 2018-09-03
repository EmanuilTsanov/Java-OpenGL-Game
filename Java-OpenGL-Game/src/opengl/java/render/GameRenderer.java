package opengl.java.render;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.fonts.GUIText;
import opengl.java.interaction.MouseLogic;
import opengl.java.interaction.MousePicker;
import opengl.java.lighting.Light;
import opengl.java.management.EntityManager;
import opengl.java.model.Model;
import opengl.java.model.TexturedModel;
import opengl.java.shader.BasicShader;
import opengl.java.shader.ColorfulShader;
import opengl.java.shader.FontShader;
import opengl.java.shader.PickShader;
import opengl.java.shader.TerrainShader;
import opengl.java.shadows.ShadowMapMasterRenderer;
import opengl.java.terrain.Terrain;
import opengl.java.texture.ModelTexture;
import opengl.java.view.Camera;
import opengl.java.window.FPSCounter;

public class GameRenderer
{
	private static int framebufferID;
	private static int colorTextureID;
	private static int renderBufferID;

	private static BasicShader eShader;
	private static TerrainShader tShader;
	private static PickShader pickShader;
	private static FontShader fontShader;
	private static ColorfulShader cShader;

	private static Camera camera = Camera.getInstance();
	private static Terrain terrain = Terrain.getInstance();
	private static Light sun = new Light(new Vector3f(1000000, 1500000, -1000000), new Vector3f(1.0f, 1.0f, 1.0f));

	private static HashMap<Integer, HashMap<Integer, Entity>> entityArray = EntityManager.getEntityHashMap();

	private static ShadowMapMasterRenderer smmr = new ShadowMapMasterRenderer(camera);

	static
	{
		enableCulling();
		initShaders();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		bindBuffers(Display.getWidth(), Display.getHeight());
	}

	private static void initShaders()
	{
		fontShader = new FontShader();
		eShader = new BasicShader();
		tShader = new TerrainShader();
		pickShader = new PickShader();
		cShader = new ColorfulShader();
		fontShader.start();
		fontShader.loadColor(new Vector3f(0, 0, 0));
		fontShader.stop();
		eShader.start();
		eShader.loadProjectionMatrix();
		eShader.stop();
		tShader.start();
		tShader.loadProjectionMatrix();
		tShader.loadShadowMap();
		tShader.loadShadowDistance();
		tShader.loadMapSize(ShadowMapMasterRenderer.SHADOW_MAP_SIZE);
		tShader.stop();
		pickShader.start();
		pickShader.loadProjectionMatrix();
		pickShader.stop();
		cShader.start();
		cShader.loadProjectionMatrix();
		cShader.stop();
	}

	private static void bindBuffers(int width, int height)
	{
		framebufferID = GL30.glGenFramebuffers();
		colorTextureID = GL11.glGenTextures();
		renderBufferID = GL30.glGenRenderbuffers();

		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebufferID);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, colorTextureID);

		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_INT,
				(java.nio.ByteBuffer) null);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, colorTextureID,
				0);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, renderBufferID);
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL14.GL_DEPTH_COMPONENT24, width, height);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER,
				renderBufferID);
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
			Model model = TexturedModel.getTexturedModel(outer.getKey()).getModel();
			ModelTexture texture = TexturedModel.getTexturedModel(outer.getKey()).getTexture();
			GL30.glBindVertexArray(model.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			if (texture.isTransparent())
				disableCulling();
			eShader.loadTextureVariables(texture);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
			for (Map.Entry<Integer, Entity> inner : outer.getValue().entrySet())
			{
				Entity currentEntity = inner.getValue();
				eShader.loadTransformationMatrix(currentEntity.getPosition(), currentEntity.getRotation(),
						currentEntity.getScale());
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
		Model model = TexturedModel.getTexturedModel(e.getAsset()).getModel();
		ModelTexture texture = TexturedModel.getTexturedModel(e.getAsset()).getTexture();
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
		eShader.loadTransformationMatrix(e.getPosition(), e.getRotation(), e.getScale());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	private static void renderTerrain(Matrix4f toShadowSpace)
	{
		tShader.loadToShadowMapSpace(toShadowSpace);
		GL30.glBindVertexArray(terrain.getMesh().getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getTexture().getID());
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		Vector2f chPos = terrain.getPosition();
		tShader.loadTransformationMatrix(new Vector3f(chPos.x, 0, chPos.y), new Vector3f(0f, 0f, 0f), 1f);
		GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
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
			Model model = TexturedModel.getTexturedModel(outer.getKey()).getModel();
			GL30.glBindVertexArray(model.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			for (Map.Entry<Integer, Entity> inner : outer.getValue().entrySet())
			{
				Entity currentEntity = inner.getValue();
				pickShader.loadTransformationMatrix(currentEntity.getPosition(), currentEntity.getRotation(),
						currentEntity.getScale());
				pickShader.loadColor(currentEntity.getColor());
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			GL20.glDisableVertexAttribArray(0);
			GL30.glBindVertexArray(0);
		}
	}

	public static Vector3f pickColor(int x, int y)
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebufferID);
		pickShader.start();
		prepareScreen(1, 1, 1);
		pickShader.loadViewMatrix(camera);
		renderOffScreen();
		pickShader.stop();
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
		eShader.start();
		prepareScreen(0, 1, 1);
		renderEntities();
		eShader.stop();
		tShader.start();
		renderTerrain(smmr.getToShadowMapSpaceMatrix());
		tShader.stop();
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

	/**
	 * Renders everything.
	 */
	public static void render()
	{
		renderShadowMap();
		prepareScreen(0, 1, 1);
		eShader.start();
		eShader.loadLight(sun);
		eShader.loadViewMatrix(camera);
		renderEntities();
		if (MouseLogic.getInstance().shouldRenderHolder())
		{
			Entity e = MouseLogic.getInstance().getHolder();
			Vector3f v = MousePicker.getInstance().getMapPosition();
			Vector2f v1 = Terrain.getInstance().getCellPosition(v.x, v.z);
			e.setPosition(v1.x, 0, v1.y);
			renderEntity(e);
		}
		eShader.stop();
		tShader.start();
		tShader.loadViewMatrix(camera);
		renderTerrain(smmr.getToShadowMapSpaceMatrix());
		tShader.stop();
		fontShader.start();
		fontShader.loadColor(new Vector3f(0, 0, 0));
		renderText(FPSCounter.getMesh());
		fontShader.stop();
	}
}
