package opengl.java.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.shader.GUIShader;

public class GUIButton extends GUIComponent
{

	public GUIButton(int x, int y, int width, int height)
	{
		super(x, y, width, height);
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
		shader.loadTransformationMatrix(new Vector3f(Maths.toOpenGLWidth(x), Maths.toOpenGLHeight(y), 1), new Vector3f(0, 0, 0), 1);
		shader.loadColor(color);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

}
