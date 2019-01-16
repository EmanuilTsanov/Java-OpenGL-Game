package opengl.java.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.maths.Maths;
import opengl.java.shader.GUIShader;

public class GUIWindow extends GUIComponent
{
	private ArrayList<GUIComponent> components = new ArrayList<GUIComponent>();

	public void move(int x, int y)
	{
		this.x += x;
		this.y += y;
	}

	public void addComponent(GUIComponent component)
	{
		if (component.x < this.x || component.y < this.y || component.x + component.width > this.x + this.width || component.y + component.height > this.y + this.height)
		{
			System.out.println("A component is out of bounds.");
		} else {
			components.add(component);
		}
	}

	@Override
	public void mouseClick()
	{
		for (GUIComponent component : components)
		{
			component.mouseClick();
		}
	}

	@Override
	public void update()
	{
		for (GUIComponent component : components)
		{
			component.update();
		}
	}

	@Override
	public void render(GUIShader shader)
	{
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		shader.loadColor(bgcolor);
		shader.loadTransformationMatrix(new Vector3f(Maths.toOpenGLWidth(x) + 1, Maths.toOpenGLHeight(y) - 1, 0), new Vector3f(0, 0, 0), 1); 
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		for (GUIComponent component : components)
		{
			component.render(shader);
		}
	}
}
