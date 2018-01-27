package opengl.java.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import opengl.java.management.SRCLoader;
import opengl.java.render.GameRenderer;

public class GUITexture extends GUIComponent
{
	public GUITexture(int x, int y, int width, int height, String imageName)
	{
		super(x, y, width, height);
		this.model = super.createCanvas();
		this.image = SRCLoader.loadTexture(imageName);
	}

	@Override
	public void update()
	{
	}

	@Override
	public void render()
	{
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, GameRenderer.getInstance().getShadowMapTexture());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

}
