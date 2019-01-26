package opengl.java.terrain;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.lighting.Light;
import opengl.java.model.RawModel;
import opengl.java.shader.TerrainShader;
import opengl.java.shadows.ShadowMapMasterRenderer;

public class TerrainRenderer
{
	private static TerrainShader shader;

	public static void initialize()
	{
		shader = new TerrainShader();
		shader.start();
		shader.connectTextureUnits();
		shader.loadProjectionMatrix();
		shader.loadShadowMap();
		shader.loadShadowDistance();
		shader.loadMapSize(ShadowMapMasterRenderer.SHADOW_MAP_SIZE);
		shader.stop();
	}

	private static void prepare(Terrain terrain)
	{
		RawModel model = terrain.getModel();
		GL30.glBindVertexArray(model.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		bindTexture(terrain);
	}

	private static void bindTexture(Terrain terrain)
	{
		TerrainTexturepack texturepack = terrain.getTexturepack();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturepack.getBackgroundTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturepack.getrTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturepack.getgTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturepack.getbTexture().getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getBlendMap().getTextureID());
	}

	private static void load(Terrain terrain)
	{
		shader.loadTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), new Vector3f(0, 0, 0), 1);
	}

	private static void unbind()
	{
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	public static void render(Terrain terrain)
	{
		shader.start();
		shader.loadViewMatrix();
		shader.loadLight(Light.SUN);
		// shader.loadToShadowMapSpace(toShadowSpace);
		prepare(terrain);
		load(terrain);
		GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		unbind();
		shader.stop();
	}
}
