package opengl.java.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.maths.Maths;
import opengl.java.shader.GUIShader;

public class GUIButton extends GUIComponent
{
	public Action action;

	public void addAction(Action action)
	{
		this.action = action;
	}

	public boolean isHovering()
	{
		float x = Mouse.getX();
		float y = Mouse.getY();
		return x > this.x && x < this.x + this.width && Display.getHeight() - y > this.y && Display.getHeight() - y < this.y + this.height;
	}

	@Override
	public void mouseClick()
	{
		if (isHovering())
		{
			if (action != null)
				action.onClick();
		}
	}

	@Override
	public void update()
	{
	}

	@Override
	public void render(GUIShader shader)
	{
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		shader.loadColor(bgcolor);
		shader.loadTransformationMatrix(new Vector3f(Maths.toOpenGLWidth(x) + 1, Maths.toOpenGLHeight(y) - 1, 0), new Vector3f(0, 0, 0), 1f);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
}
