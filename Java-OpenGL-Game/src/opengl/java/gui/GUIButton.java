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

	private boolean isHovering;

	private ActionInterface action;

	public GUIButton(int x, int y, int width, int height,GUIComponent parent)
	{
		super(x, y, width, height, parent);
		int a = (int) (width * 1.2f);
		int b = (int) (height * 1.2f);
		bigModel = createMesh(a, b);
		bmX = (a - width) / 2f;
		bmY = (b - height) / 2f;
	}

	public void addAction(ActionInterface action)
	{
		this.action = action;
	}

	@Override
	public void update()
	{
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
		isHovering();
		if (isHovering && Mouse.isButtonDown(0))
		{
			if(action!=null)
			action.onClick();
		}
	}

	@Override
	public void render(GUIShader shader)
	{
		if (isHovering)
		{
			GL30.glBindVertexArray(bigModel.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			shader.loadTransformationMatrix(new Vector3f(Maths.toOpenGLWidth(renderX - bmX) + 1, Maths.toOpenGLHeight(renderY - bmY) - 1, 0), new Vector3f(0, 0, 0), 1);
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
			shader.loadTransformationMatrix(new Vector3f(Maths.toOpenGLWidth(renderX) + 1, Maths.toOpenGLHeight(renderY) - 1, 0), new Vector3f(0, 0, 0), 1);
			shader.loadColor(color);
			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}

	}

	public void isHovering()
	{
		if (Mouse.getX() >= renderX && Mouse.getX() <= renderX + width && Display.getHeight() - Mouse.getY() >= renderY && Display.getHeight() - Mouse.getY() < renderY + height)
			isHovering = true;
		else
			isHovering = false;
	}
}
