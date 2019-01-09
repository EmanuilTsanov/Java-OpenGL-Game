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

	public GUIButton(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		int a = (int) (width * 1.2f);
		int b = (int) (height * 1.2f);
		bigModel = createMesh(a, b);
		bmX = Maths.toOpenGLWidth((a - width) / 2f);
		bmY = Maths.toOpenGLHeight((b - height) / 2f);
	}

	public void addAction(ActionInterface action)
	{
		this.action = action;
	}

	@Override
	public void update()
	{
		isHovering();
		if(isHovering && Mouse.isButtonDown(0)) {
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
			shader.loadTransformationMatrix(new Vector3f(glX - bmX, glY - bmY, 0), new Vector3f(0, 0, 0), 1);
			shader.loadMode(mode);
			shader.loadColor(color);
			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
		}
		else
			super.render(shader);
	}

	public void isHovering()
	{
		if (Mouse.getX() >= x && Mouse.getX() <= x + width && Display.getHeight() - Mouse.getY() >= y && Display.getHeight() - Mouse.getY() < y + height)
			isHovering = true;
		else
			isHovering = false;
	}
}
