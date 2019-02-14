package opengl.java.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.fonts.GUIText;
import opengl.java.shader.FontShader;

public class TextRenderer
{
	private FontShader shader;

	public TextRenderer()
	{
		shader = new FontShader();
		shader.start();
		shader.loadColor(new Vector3f(0, 0, 0));
	}

	private void renderText(GUIText text)
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL30.glBindVertexArray(text.getModel().getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, text.getTextureID());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getModel().getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public void render(GUIText text) {
		shader.start();
		shader.loadColor(text.getColor());
		renderText(text);
		shader.stop();
	}
}