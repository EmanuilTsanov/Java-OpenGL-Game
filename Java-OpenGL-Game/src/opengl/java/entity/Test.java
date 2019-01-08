package opengl.java.entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.loader.ModelLoader;
import opengl.java.management.SRCLoader;
import opengl.java.maths.Maths;
import opengl.java.model.TexturedModel;
import opengl.java.shader.GUIShader;

public class Test extends Entity
{
	static float[] vertices = { -1, 1, 0, -1, Maths.toOpenGLHeight(100), 0, Maths.toOpenGLWidth(100), Maths.toOpenGLHeight(100), 0, Maths.toOpenGLWidth(100), 1, 0 };
	static int[] indices = { 0, 1, 3, 3, 1, 2 };
	static float[] textureCoords = { 0, 0, 0, 1, 1, 1, 1, 0 };
	static float[] normals = { 0 };

	private GUIShader shader = new GUIShader();
	private static TexturedModel model = new TexturedModel(15, ModelLoader.getInstance().loadModel(vertices, indices, textureCoords, normals), SRCLoader.loadTexture("kondio"));

	public Test()
	{
		super(model.getID());
	}

	public void render()
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		shader.start();
		GL30.glBindVertexArray(model.getRawModel().getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
		shader.loadTransformationMatrix(new Vector3f(Maths.toOpenGLWidth(100)+1,Maths.toOpenGLHeight(100)-1,0), new Vector3f(0, 0, 0), 1);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		shader.stop();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

}