package opengl.java.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
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
		int mx = Mouse.getX();
		int my = Mouse.getY();
		System.out.println(my + " / " + this.getY());
		if (mx > this.getX() && mx < this.getX() + width && my > this.getY() && my < this.getY() + height)
		{
			System.out.println(1);
			if (!highlighted)
			{
				this.x -= 10;
				this.y -= 10;
				this.width += 20;
				this.height += 20;
				highlighted = true;
				create();
			}
		}
		else
		{
			if (highlighted)
			{
				this.x += 10;
				this.y += 10;
				this.width -= 20;
				this.height -= 20;
				highlighted = false;
				create();
			}
		}
	}

	@Override
	public void render(GUIShader shader)
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		if (textureSample == 0)
			shader.loadColor(color);
		else
		{
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, image.getID());
		}
		shader.loadTextureSample(textureSample);
		shader.loadTransformationMatrix(new Vector3f(Maths.toOpenGLWidth(parent == null ? x : parent.getX() + x),
				Maths.toOpenGLHeight(parent == null ? y : parent.getY() + y), 1), new Vector3f(0, 0, 0), 1);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
}