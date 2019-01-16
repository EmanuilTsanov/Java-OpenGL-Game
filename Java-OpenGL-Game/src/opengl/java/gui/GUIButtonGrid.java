package opengl.java.gui;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.maths.Maths;
import opengl.java.shader.GUIShader;

public class GUIButtonGrid extends GUIComponent
{
	private int gridWidth;
	private int gridHeight;
	private int buttonSize;
	private int xSpacing, ySpacing;

	private int lastPage;

	private int currentPage;

	private HashMap<Integer, ArrayList<GUIButton>> buttons = new HashMap<Integer, ArrayList<GUIButton>>();

	public GUIButtonGrid(int gridWidth, int gridHeight)
	{
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		buttons.put(lastPage, new ArrayList<GUIButton>());
	}

	public void addButton(GUIButton button)
	{
		if (buttons.get(lastPage).size() == gridWidth * gridHeight)
		{
			lastPage++;
			buttons.put(lastPage, new ArrayList<GUIButton>());
		}
		int gridX = buttons.get(lastPage).size() % gridWidth;
		int gridY = (int) (buttons.get(lastPage).size() / gridWidth);
		System.out.println(buttons.size());
		button.setPosition(x + (gridX * buttonSize) + ((gridX + 1) * xSpacing), y + (gridY * buttonSize) + ((gridY + 1) * ySpacing));
		button.setSize(buttonSize, buttonSize);
		buttons.get(lastPage).add(button);
	}

	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
			this.xSpacing = 30 / gridWidth;
			this.buttonSize = (this.width - (gridWidth + 1) * xSpacing) / gridWidth;
			ySpacing = (height - (gridHeight * buttonSize)) / (gridHeight + 1);
	}

	@Override
	public void mouseClick()
	{
		for (GUIButton button : buttons.get(currentPage))
		{
			button.mouseClick();
		}
	}

	@Override
	public void update()
	{
		for (GUIButton button : buttons.get(currentPage))
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
		shader.loadTransformationMatrix(new Vector3f(Maths.toOpenGLWidth(x) + 1, Maths.toOpenGLHeight(y) - 1, 0), new Vector3f(0, 0, 0), 1);
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		for (GUIButton button : buttons.get(currentPage))
		{
			button.render(shader);
		}
	}

}
