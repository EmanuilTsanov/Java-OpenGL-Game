package opengl.java.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.calculations.MousePicker;
import opengl.java.entity.Entity;
import opengl.java.entity.EntityManager;
import opengl.java.fonts.GUIText;
import opengl.java.lighting.Light;
import opengl.java.model.RawModel;
import opengl.java.shader.BasicShader;
import opengl.java.shader.FontShader;
import opengl.java.shader.PickShader;
import opengl.java.shader.TerrainShader;
import opengl.java.terrain.Chunk;
import opengl.java.terrain.ChunkMap;
import opengl.java.texture.BaseTexture;
import opengl.java.view.Camera;
import opengl.java.window.Window;

public class MainRenderer
{
	private FontShader fontShader;
	private BasicShader basicShader;
	private TerrainShader terrainShader;
	private PickShader pickShader;
	private Light sun;
	private Camera camera;

	private MousePicker picker;

	private ChunkMap chunkMap;
	private EntityManager eManager;

	int framebufferID;
	int colorTextureID;
	int renderBufferID;

	public static HashMap<Integer, List<Entity>> entities;

	public MainRenderer()
	{
		initShaders();
		sun = new Light(new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(1.0f, 1.0f, 1.0f));
		camera = new Camera(new Vector3f(0, 20, 0), 35, 45, 45);
		picker = new MousePicker(Maths.getProjectionMatrix(), camera);
		chunkMap = new ChunkMap(5);
		eManager = new EntityManager(chunkMap.getSize());
		entities = eManager.loadEntities();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		bindBuffers(Window.WIDTH, Window.HEIGHT);
	}

	/**
	 * Initializes the shaders and loads them up with the necessary elements to
	 * make them work properly.
	 */
	public void initShaders()
	{
		// Initializing shaders
		fontShader = new FontShader();
		basicShader = new BasicShader();
		terrainShader = new TerrainShader();
		pickShader = new PickShader();

		// Loading up shaders
		fontShader.start();
		fontShader.loadColor(new Vector3f(0, 0, 0));
		fontShader.stop();
		basicShader.start();
		basicShader.loadProjectionMatrix();
		basicShader.stop();
		terrainShader.start();
		terrainShader.loadProjectionMatrix();
		terrainShader.stop();
		pickShader.start();
		pickShader.loadProjectionMatrix();
		pickShader.stop();
	}

	/**
	 * Prepares the screen for rendering.
	 */
	public void prepareScreen(float r, float g, float b)
	{
		GL11.glClearColor(r, g, b, 0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	/**
	 * Renders all the entities.
	 */
	public void renderEntities()
	{
		for (Map.Entry<Integer, List<Entity>> ents : entities.entrySet())
		{
			RawModel model = Entity.getModel(ents.getKey());
			BaseTexture texture = Entity.getTexture(ents.getKey());
			BaseTexture specularMap = Entity.getSpecularMap(ents.getKey());
			GL30.glBindVertexArray(model.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			List<Entity> entsArr = ents.getValue();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
			if (specularMap != null)
			{
				GL13.glActiveTexture(GL13.GL_TEXTURE1);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, specularMap.getID());
			}
			for (int e = 0; e < ents.getValue().size(); e++)
			{
				Entity currentEntity = entsArr.get(e);
				basicShader.loadTransformationMatrix(currentEntity.getPosition(), currentEntity.getRotation(), currentEntity.getScale());
				basicShader.loadMaterialValues(currentEntity.getMaterial());
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
		basicShader.loadTransformationMatrix(e.getPosition(), e.getRotation(), e.getScale());
		basicShader.loadMaterialValues(e.getMaterial());
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
		HashMap<String, Chunk> chunks = chunkMap.getChunkArray();
		for (Map.Entry<String, Chunk> chunk : chunks.entrySet())
		{
			Chunk ch = chunk.getValue();
			GL30.glBindVertexArray(ch.getModel().getVAOID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, chunkMap.getTexture().getID());
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			Vector2f chPos = ch.getPosition();
			terrainShader.loadTransformationMatrix(new Vector3f(chPos.x, 0, chPos.y), new Vector3f(0f, 0f, 0f), 1f);
			GL11.glDrawElements(GL11.GL_TRIANGLES, ch.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}
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
		for (Map.Entry<Integer, List<Entity>> ents : entities.entrySet())
		{
			RawModel model = Entity.getModel(ents.getKey());
			GL30.glBindVertexArray(model.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			List<Entity> entsArr = ents.getValue();
			for (int e = 0; e < ents.getValue().size(); e++)
			{
				Entity currentEntity = entsArr.get(e);
				pickShader.loadTransformationMatrix(currentEntity.getPosition(), currentEntity.getRotation(), currentEntity.getScale());
				pickShader.loadColor(currentEntity.getColor());
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			GL20.glDisableVertexAttribArray(0);
			GL30.glBindVertexArray(0);
		}
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

	public Vector3f pickColor(int x, int y)
	{
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
		basicShader.start();
		prepareScreen(0, 1, 1);
		renderEntities();
		basicShader.stop();
		terrainShader.start();
		renderTerrain();
		terrainShader.stop();
		unbindBuffers();
		saveScreenshot();
	}

	public ByteBuffer readScreen(int x, int y, int width, int height)
	{
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		GL11.glReadPixels(x, y, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		return buffer;
	}

	public void saveScreenshot()
	{
		ByteBuffer buffer = readScreen(0, 0, Window.WIDTH, Window.HEIGHT);
		File file = new File("Screenshot " + Calendar.DATE + ".png");
		String format = "png";
		BufferedImage image = new BufferedImage(Window.WIDTH, Window.HEIGHT, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < Window.WIDTH; x++)
		{
			for (int y = 0; y < Window.HEIGHT; y++)
			{
				int i = (x + (Window.WIDTH * y)) * 4;
				int r = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int b = buffer.get(i + 2) & 0xFF;
				image.setRGB(x, Window.HEIGHT - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
			}
		}

		try
		{
			ImageIO.write(image, format, file);
			System.out.println("SCREENSHOT");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Renders everything.
	 */
	public void render()
	{
		camera.control(this);
		prepareScreen(0, 1, 1);
		basicShader.start();
		picker.update();
		basicShader.loadLight(sun);
		basicShader.loadViewMatrix(camera);
		if (camera.getEntityHolder() != null)
		{
			camera.getEntityHolder().setPosition(picker.getMapPosition());
			renderEntity(camera.getEntityHolder());
		}
		GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
		renderEntities();
		basicShader.stop();
		terrainShader.start();
		terrainShader.loadViewMatrix(camera);
		renderTerrain();
		terrainShader.stop();
		if (Keyboard.isKeyDown(Keyboard.KEY_F1))
		{
			takeScreenshot();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F2))
		{
			chunkMap.getChunkByPos((int) picker.getMapPosition().x, (int) picker.getMapPosition().z);
		}
		// s.start();
		// fr.render(g);
		// s.stop();
	}
}
