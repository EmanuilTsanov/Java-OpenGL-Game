package opengl.java.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.maths.Maths;
import opengl.java.model.RawModel;
import opengl.java.shader.GUIShader;

public class GUIButton extends GUIComponent
{

	private RawModel bigModel;
	private float bmX, bmY;

	private ActionInterface action;

	public GUIButton(float x, float y, float width, float height, GUIComponent parent)
	{
		super(x, y, width, height, parent);
		int a = (int) (width * 1.2f);
		int b = (int) (height * 1.2f);
		bigModel = createMesh(a, b);
		bmX = (a - width) / 2f;
		bmY = (b - height) / 2f;
		super.getClickableComponents().add(this);
	}

	public void addAction(ActionInterface action)
	{
		this.action = action;
	}

	@Override
	public void update()
	{
	}

	@Override
	public void render(GUIShader shader)
	{
		if (isHovering())
		{
			GL30.glBindVertexArray(bigModel.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			shader.loadTransformationMatrix(new Vector3f(Maths.toOpenGLWidth(x - bmX) + 1, Maths.toOpenGLHeight(y - bmY) - 1, 0), new Vector3f(0, 0, 0), 1);
			shader.loadColor(color);
			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}
		else
		{
			GL30.glBindVertexArray(model.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			shader.loadTransformationMatrix(new Vector3f(Maths.toOpenGLWidth(x) + 1, Maths.toOpenGLHeight(y) - 1, 0), new Vector3f(0, 0, 0), 1);
			shader.loadColor(color);
			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}

	}

	public void click()
	{
		if (action != null)
			action.onClick();
	}

	public boolean isHovering()
	{
		if (Mouse.getX() >= x && Mouse.getX() <= x + width && Display.getHeight() - Mouse.getY() >= y && Display.getHeight() - Mouse.getY() < y + height)
			return true;
		else
			return false;
	}
}
