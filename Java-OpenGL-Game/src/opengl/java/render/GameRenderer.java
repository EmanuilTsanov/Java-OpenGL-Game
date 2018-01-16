package opengl.java.render;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.fonts.GUIText;
import opengl.java.interaction.MouseController;
import opengl.java.lighting.Light;
import opengl.java.management.EntityManager;
import opengl.java.management.FileManager;
import opengl.java.management.LightManager;
import opengl.java.model.RawModel;
import opengl.java.shader.BasicShader;
import opengl.java.shader.ColorfulShader;
import opengl.java.shader.FontShader;
import opengl.java.shader.PickShader;
import opengl.java.shader.TerrainShader;
import opengl.java.terrain.Terrain;
import opengl.java.texture.BaseTexture;
import opengl.java.view.Camera;
import opengl.java.window.Window;

public class GameRenderer
{
	private int framebufferID;
	private int colorTextureID;
	private int renderBufferID;

	private BasicShader eShader;
	private TerrainShader tShader;
	private PickShader pickShader;
	private FontShader fontShader;
	private ColorfulShader cShader;

	private Camera camera = Camera.getInstance();
	private Terrain terrain = Terrain.getInstance();
	private HashMap<Integer, HashMap<Integer, Entity>> entityArray = EntityManager.getInstance().getEntityHashMap();
	private Light sun = LightManager.getInstance().getSun();

	private static GameRenderer singleton = new GameRenderer();

	public GameRenderer()
	{
		initShaders();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		bindBuffers(Window.getInstance().getWidth(), Window.getInstance().getHeight());
	}

	private void initShaders()
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
		tShader.stop();
		pickShader.start();
		pickShader.loadProjectionMatrix();
		pickShader.stop();
		cShader.start();
		cShader.loadProjectionMatrix();
		cShader.stop();
	}

	public void bindBuffers(int width, int height)
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

	public void unbindBuffers()
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}

	public static GameRenderer getInstance()
	{
		return singleton;
	}

	public void prepareScreen(float r, float g, float b)
	{
		GL11.glClearColor(r, g, b, 0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void renderEntities()
	{
		for (Map.Entry<Integer, HashMap<Integer, Entity>> outer : entityArray.entrySet())
		{
			RawModel model = Entity.getModel(outer.getKey());
			BaseTexture texture = Entity.getTexture(outer.getKey());
			GL30.glBindVertexArray(model.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
			for (Map.Entry<Integer, Entity> inner : outer.getValue().entrySet())
			{
				Entity currentEntity = inner.getValue();
				eShader.loadTransformationMatrix(currentEntity.getPosition(), currentEntity.getRotation(), currentEntity.getScale());
				eShader.loadMaterialValues(currentEntity.getMaterial());
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(2);
			GL30.glBindVertexArray(0);
		}
	}

	public void renderEntity(Entity e)
	{
		RawModel model = Entity.getModel(e.getId());
		BaseTexture texture = Entity.getTexture(e.getId());
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
		eShader.loadTransformationMatrix(e.getPosition(), e.getRotation(), e.getScale());
		eShader.loadMaterialValues(e.getMaterial());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	/**
	 * Renders the terrain.
	 */
	public void renderTerrain()
	{
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

	/**
	 * Renders a text GUI.
	 * 
	 * @param t
	 *            - the GUI, containing the fonts, the font size and the text
	 *            itself.
	 */
	public void renderText(GUIText t)
	{
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
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
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	public void renderOffScreen()
	{
		for (Map.Entry<Integer, HashMap<Integer, Entity>> outer : entityArray.entrySet())
		{
			RawModel model = Entity.getModel(outer.getKey());
			GL30.glBindVertexArray(model.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			for (Map.Entry<Integer, Entity> inner : outer.getValue().entrySet())
			{
				Entity currentEntity = inner.getValue();
				pickShader.loadTransformationMatrix(currentEntity.getPosition(), currentEntity.getRotation(), currentEntity.getScale());
				pickShader.loadColor(currentEntity.getColor());
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			GL20.glDisableVertexAttribArray(0);
			GL30.glBindVertexArray(0);
		}
	}

	public Vector3f pickColor(int x, int y)
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

	public void takeScreenshot()
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebufferID);
		eShader.start();
		prepareScreen(0, 1, 1);
		renderEntities();
		eShader.stop();
		tShader.start();
		renderTerrain();
		tShader.stop();
		unbindBuffers();
		FileManager.saveScreenshot();
	}

	public ByteBuffer readScreen(int x, int y, int width, int height)
	{
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		GL11.glReadPixels(x, y, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		return buffer;
	}

	/**
	 * Renders everything.
	 */
	public void render()
	{
		// GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
		prepareScreen(0, 1, 1);
		eShader.start();
		eShader.loadLight(sun);
		eShader.loadViewMatrix(camera);
		renderEntities();
		MouseController.getInstance().render();
		eShader.stop();
		tShader.start();
		tShader.loadViewMatrix(camera);
		renderTerrain();
		tShader.stop();
		// cShader.start();
		// cShader.loadColor(new Vector3f(1.0f, 0.0f, 0.0f));
		// cShader.stop();
		// s.start();
		// fr.render(g);
		// s.stop();
	}
}
