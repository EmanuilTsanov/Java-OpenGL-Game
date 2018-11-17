package opengl.java.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.management.SRCLoader;
import opengl.java.model.RawModel;
import opengl.java.shader.GUIShader;
import opengl.java.texture.RawTexture;
import opengl.java.window.Window;

public class Scope extends GUIComponent
{
	private RawModel model;
	private RawTexture texture;

	public Scope()
	{
		super(0, 0, 1024, 1024);
		model = Maths.createPlane(448,28, width, height);
		texture = SRCLoader.loadTexture("scope");
	}

	@Override
	public void update()
	{
	}

	@Override
	public void render(GUIShader shader)
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		shader.loadTransformationMatrix(new Vector3f(Maths.toOpenGLWidth(x), Maths.toOpenGLHeight(y),1),
				new Vector3f(0, 0, 0), 1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

}
