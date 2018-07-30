package opengl.java.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.shader.GUIShader;

public class GUIWindow extends GUICanvas
{
	private ArrayList<GUIComponent> array;

	public GUIWindow(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		setColor(new Vector3f(100, 100, 100));
		array = new ArrayList<GUIComponent>();
	}

	public GUIWindow addChild(GUIComponent component)
	{
		array.add(component);
		return this;
	}

	public void update()
	{
		for (GUIComponent comp : array)
		{
			comp.update();
		}
	}

	public void render(GUIShader shader)
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		shader.loadColor(color);
		shader.loadTransformationMatrix(new Vector3f(Maths.toOpenGLWidth(x), Maths.toOpenGLHeight(y), 1), new Vector3f(0, 0, 0), 1);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		for (GUIComponent comp : array)
		{
			comp.render(shader);
		}
	}
}
