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
import opengl.java.loader.ModelLoader;
import opengl.java.model.RawModel;
import opengl.java.shader.BasicShader;
import opengl.java.shader.ColorfulShader;
import opengl.java.shader.FontShader;
import opengl.java.shader.PickShader;
import opengl.java.shader.TerrainShader;
import opengl.java.terrain.Terrain;
import opengl.java.terrain.TerrainGenerator;
import opengl.java.texture.BaseTexture;
import opengl.java.view.Camera;
import opengl.java.window.Window;

public class MainRenderer
{
	private Light sun;
	private Camera camera;

	private Terrain terrain;

	private int framebufferID;
	private int colorTextureID;
	private int renderBufferID;

	private MousePicker picker;

	private BasicShader eShader;
	private TerrainShader tShader;
	private PickShader pickShader;
	private FontShader fontShader;
	private ColorfulShader cShader;

	private EntityManager eManager;

	public static HashMap<Integer, List<Entity>> entities;

	public MainRenderer()
	{
		sun = new Light(new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(1.0f, 1.0f, 1.0f));
		Vector2f m = TerrainGenerator.getMidPoint();
		camera = new Camera(new Vector3f(m.x, 20, m.y), 35, 45, 45);
		terrain = new Terrain();
		bindBuffers(Window.WIDTH, Window.HEIGHT);
		picker = new MousePicker(Maths.getProjectionMatrix(), camera);
		initShaders();
		eManager = new EntityManager();
		entities = eManager.loadEntities();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
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
			GL30.glBindVertexArray(model.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			List<Entity> entsArr = ents.getValue();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
			for (int e = 0; e < ents.getValue().size(); e++)
			{
				Entity currentEntity = entsArr.get(e);
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
		GL30.glBindVertexArray(terrain.getModel().getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getTexture().getID());
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		Vector2f chPos = terrain.getPosition();
		tShader.loadTransformationMatrix(new Vector3f(chPos.x, 0, chPos.y), new Vector3f(0f, 0f, 0f), 1f);
		GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
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
		eShader.start();
		prepareScreen(0, 1, 1);
		renderEntities();
		eShader.stop();
		tShader.start();
		renderTerrain();
		tShader.stop();
		unbindBuffers();
		saveScreenshot();
	}

	public Terrain getTerrain()
	{
		return terrain;
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
		eShader.start();
		picker.update();
		eShader.loadLight(sun);
		eShader.loadViewMatrix(camera);
		if (camera.getEntityHolder() != null)
		{
			Vector3f mPos = picker.getMapPosition();
			Vector2f vec = terrain.getCellPos(mPos.x, mPos.z);
			System.out.println((int) (vec.x + 0.5f) * TerrainGenerator.getQuadSize() + " / " + (int) (vec.y + 0.5f) * TerrainGenerator.getQuadSize());
			camera.getEntityHolder().setPosition((int) (vec.x + 0.5f) * TerrainGenerator.getQuadSize(), 0, (int) (vec.y + 0.5f) * TerrainGenerator.getQuadSize());
			renderEntity(camera.getEntityHolder());
		}
		GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
		renderEntities();

		eShader.stop();

		tShader.start();
		tShader.loadViewMatrix(camera);
		renderTerrain();
		tShader.stop();
		if (Keyboard.isKeyDown(Keyboard.KEY_F1))
		{
			takeScreenshot();
		}
		// s.start();
		// fr.render(g);
		// s.stop();
	}

	public RawModel initModel()
	{
		float[] vertices = { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 2.0f, 2.0f, 0.0f, 2.0f, 2.0f, 0.0f, 0.0f };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		float[] texCoords = { 0 };
		float[] normals = { 0 };
		ModelLoader loader = new ModelLoader();
		return loader.loadModel(vertices, indices, texCoords, normals);
	}

	public void renderModel(RawModel model, Vector3f pos)
	{
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		cShader.loadViewMatrix(camera);
		cShader.loadTransformationMatrix(pos, new Vector3f(0, 0, 0), 1f);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
}
