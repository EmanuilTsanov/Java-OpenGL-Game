package opengl.java.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.shader.GUIShader;

public class GUIWindow extends GUIComponent
{
	public ArrayList<GUIComponent> components = new ArrayList<GUIComponent>();

	public GUIWindow(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	public void addComponent(GUIComponent g)
	{
		if (g.getWidth() > this.getWidth() || g.getHeight() > this.getHeight())
		{
			System.out.println("Component is bigger than the window.");
		}
		else
		{
			g.x += x;
			g.y += y;
			components.add(g);
		}
	}

	public void update()
	{
	}

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
		for(GUIComponent g : components) {
			g.render(shader);
		}
	}
}
