package opengl.java.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.loader.ModelLoader;
import opengl.java.maths.Maths;
import opengl.java.model.RawModel;
import opengl.java.shader.GUIShader;

public class GUIComponent
{
	protected float x, y;
	protected float width, height;

	protected static final int COLOR = 0;
	protected static final int TEXTURE = 1;

	protected int mode;

	protected Vector3f color;

	protected RawModel model;

	public GUIComponent(int x, int y, int width, int height)
	{
		this.x = Maths.toOpenGLWidth(x);
		this.y = Maths.toOpenGLHeight(y);
		this.width = width;
		this.height = height;
		model = createMesh(width, height);
		mode = COLOR;
		color = new Vector3f(1f, 1f, 1f);
	}

	public RawModel createMesh(float width, float height)
	{
		float[] vertices = { -1, 1, 0, -1, Maths.toOpenGLHeight(height), 0, Maths.toOpenGLWidth(width), Maths.toOpenGLHeight(height), 0, Maths.toOpenGLWidth(width), 1, 0 };
		int[] indices = { 0, 1, 3, 3, 1, 2 };
		float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };
		float[] normals = { 0 };
		return ModelLoader.loadModel(vertices, indices, textureCoords, normals);
	}

	public void setColor(float r, float g, float b)
	{
		color = Maths.normalizeColor(new Vector3f(r, g, b));
	}

	public void update()
	{

	}

	public void render(GUIShader shader)
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		shader.loadTransformationMatrix(new Vector3f(x + 1, y - 1, 0), new Vector3f(0, 0, 0), 1);
		shader.loadMode(mode);
		shader.loadColor(color);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
}