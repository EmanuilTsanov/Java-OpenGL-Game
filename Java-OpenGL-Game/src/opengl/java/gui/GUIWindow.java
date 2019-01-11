package opengl.java.gui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.maths.Maths;
import opengl.java.shader.GUIShader;
import opengl.java.window.FrameController;

public class GUIWindow extends GUIComponent
{
	public GUIWindow(int x, int y, int width, int height, GUIComponent parent)
	{
		super(x, y, width, height, parent);
	}

	private ArrayList<GUIComponent> components = new ArrayList<GUIComponent>();

	public void addComponent(GUIComponent component)
	{
		if (component.getWidth() > width || component.getHeight() > height || component.getX() + component.getWidth() > x + width
				|| component.getY() + component.getHeight() > y + height)
		{
			System.out.println("There was a problem adding a component.");
		}
		else
		{
			components.add(component);
		}
	}

	public void update()
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_Q))
		{
			if (renderX < 0)
				x+= FrameController.getFrameTimeSeconds()*1000;
			if(x > 0) x = 0;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_E))
		{
			if (renderX + width > 0)
				x-= FrameController.getFrameTimeSeconds()*1000;
		}
		if (parent != null)
		{
			renderX = parent.getRenderX() + x;
			renderY = parent.getRenderY() + y;
		}
		else
		{
			renderX = x;
			renderY = y;
		}
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
		GL20.glEnableVertexAttribArray(1);
		shader.loadTransformationMatrix(new Vector3f(Maths.toOpenGLWidth(renderX) + 1, Maths.toOpenGLHeight(renderY) - 1, 0), new Vector3f(0, 0, 0), 1);
		shader.loadColor(color);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		for (GUIComponent component : components)
		{
			component.render(shader);
		}
	}
}