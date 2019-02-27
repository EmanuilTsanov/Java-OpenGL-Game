package opengl.java.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.model.RawModel;
import opengl.java.shader.TerrainShader;
import opengl.java.terrain.Terrain;

public class TerrainRenderer
{
	private void prepare(Terrain terrain)
	{
		RawModel model = terrain.getModel();
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
	}

	private void bindTexture(Terrain terrain)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getTexture());
	}

	private void unbind()
	{
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	public void render(Terrain terrain, TerrainShader shader)
	{
		prepare(terrain);
		bindTexture(terrain);
		shader.loadTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), new Vector3f(0, 0, 0), 1);
		GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		unbind();

	}
}
