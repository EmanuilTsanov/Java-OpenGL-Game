package opengl.java.gui;

import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.shader.GUIShader;

public class GUICanvas extends GUIComponent
{
	boolean highlighted;

	public GUICanvas(int width, int height)
	{
		super(width, height);
	}

	@Override
	public void update()
	{
		x = xAdv + (parent == null ? 0 : parent.getX());
		y = yAdv + (parent == null ? 0 : parent.getY());
		for (Map.Entry<Integer, GUIComponent> component : children.entrySet())
		{
			component.getValue().update();
		}
	}
	
	@Override
	public void render(GUIShader shader)
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		shader.loadColor(color);
		shader.loadTransformationMatrix(new Vector3f(Maths.toOpenGLWidth(x), Maths.toOpenGLHeight(y), 1),
				new Vector3f(0, 0, 0), 1);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		for(Map.Entry<Integer, GUIComponent> component : children.entrySet()) {
			component.getValue().render(shader);
		}
	}
}