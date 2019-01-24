package opengl.java.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.maths.Maths;
import opengl.java.shader.GUIShader;

public class GUIMenuBar extends GUIComponent
{
	private int rowWidth;
	private float buttonSize;
	private float buttonSpacing;
	private float borderX, borderY;

	private ArrayList<GUIButton> buttons = new ArrayList<GUIButton>();

	public GUIMenuBar(int rowWidth)
	{
		this.rowWidth = rowWidth;
	}

	public void addButton(GUIButton button)
	{
		if (buttons.size() < rowWidth)
		{
			button.setParent(parent);
			button.setPosition((int) (x + borderX + buttons.size() * (buttonSize + buttonSpacing)), (int) (y + borderY));
			button.setSize((int) buttonSize, (int) buttonSize);
			buttons.add(button);
		}
		else
		{
			System.out.println("This menubar is full!");
		}
	}

	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		float spacing1 = (50 / rowWidth) + 1;
		float spacing2 = (height / 100f) * 10f;
		float btn1 = (this.width - ((rowWidth + 1) * spacing1)) / rowWidth;
		float btn2 = (this.height - (2 * spacing2));
		if (btn1 < btn2)
		{
			System.out.println(1);
			buttonSpacing = spacing1;
			buttonSize = btn1;
			borderY = (height - buttonSize) / 2;
		}
		else
		{
			System.out.println(2);
			buttonSize = btn2;
			buttonSpacing = (width - (rowWidth * buttonSize)) / (rowWidth + 1);
			borderY = spacing2;
		}
		borderX = (width - ((rowWidth * buttonSize) + ((rowWidth - 1) * buttonSpacing))) / 2;
	}

	@Override
	public void mouseClick()
	{
		for (GUIButton button : buttons)
		{
			button.mouseClick();
		}
	}

	@Override
	public void update()
	{
		for (GUIButton button : buttons)
		{
			button.update();
		}
	}

	@Override
	public void render(GUIShader shader)
	{
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		shader.loadColor(bgcolor);
		shader.loadTransformationMatrix(new Vector3f(Maths.toOpenGLWidth(parent == null ? 0 : parent.getX() + x) + 1, Maths.toOpenGLHeight(parent == null ? 0 : parent.getY() + y) - 1, 0), new Vector3f(0, 0, 0), 1);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		for (GUIButton button : buttons)
		{
			button.render(shader);
		}
	}
}