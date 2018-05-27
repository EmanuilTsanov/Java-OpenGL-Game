package opengl.java.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import opengl.java.fonts.FontReader;
import opengl.java.fonts.FontType;
import opengl.java.fonts.GUIText;
import opengl.java.management.SRCLoader;
import opengl.java.render.GameRenderer;
import opengl.java.shader.GUIShader;

public class GUIButton extends GUIComponent
{
	private String text;
	private FontType font;
	private float fontSize;
	private GUIText guiText;

	protected GUIButton(int width, int height, String imageName)
	{
		super(width, height);
		this.image = SRCLoader.loadTexture(imageName);
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public void setFont(String fileName, float fontSize, int maxLineWidth)
	{
		FontReader reader = new FontReader();
		this.font = new FontType(reader, fileName);
	}

	public void reloadText()
	{
		// guiText = new GUIText(x, y, text == null ? "" : text, font == null ?
		// Fonts.FONTS_DEFAULT : font, fontSize, width);
		// guiText.setPosition(x + (width - (int) guiText.getTextDimensions().x) / 2, y
		// + (height - (int) guiText.getTextDimensions().y) / 2);
	}

	@Override
	public void update()
	{
	}

	@Override
	public void render(GUIShader shader)
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
