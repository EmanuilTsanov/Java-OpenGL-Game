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
	private Action action;

	private Vector3f hoverColor;

	public GUIButton()
	{
		bgcolor = new Vector3f(1f, 0.8f, 0f);
		hoverColor = new Vector3f(0.85f, 0.65f, 0f);
	}

	public void addAction(Action action)
	{
		this.action = action;
	}

	public boolean isHovering()
	{
		float x = Mouse.getX();
		float y = Mouse.getY();
		float x1 = parent == null ? 0 : parent.getX();
		float y1 = parent == null ? 0 : parent.getY();
		return x > x1 + this.x && x < x1 + this.x + this.width && Display.getHeight() - y > y1 + this.y && Display.getHeight() - y < y1 + this.y + this.height;
	}

	public void setHoverColor(float r, float g, float b)
	{
		float px = 1f / 255f;
		hoverColor = new Vector3f(r * px, g * px, b * px);
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
		if (isHovering())
			shader.loadColor(hoverColor);
		else
			shader.loadColor(bgcolor);
		shader.loadTransformationMatrix(new Vector3f(Maths.toOpenGLWidth(parent == null ? 0 : parent.getX() + x) + 1, Maths.toOpenGLHeight(parent == null ? 0 : parent.getY() + y) - 1, 0), new Vector3f(0, 0, 0), 1f);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
}