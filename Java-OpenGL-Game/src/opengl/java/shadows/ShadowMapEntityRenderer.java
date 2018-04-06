package opengl.java.shadows;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import opengl.java.calculations.Maths;
import opengl.java.entity.Entity;
import opengl.java.interaction.MouseController;
import opengl.java.model.Model;
import opengl.java.model.TexturedModel;
import opengl.java.shader.ShadowShader;

public class ShadowMapEntityRenderer
{

	private Matrix4f projectionViewMatrix;
	private ShadowShader shader;

	/**
	 * @param shader
	 *            - the simple shader program being used for the shadow render pass.
	 * @param projectionViewMatrix
	 *            - the orthographic projection matrix multiplied by the light's
	 *            "view" matrix.
	 */
	protected ShadowMapEntityRenderer(ShadowShader shader, Matrix4f projectionViewMatrix)
	{
		this.shader = shader;
		this.projectionViewMatrix = projectionViewMatrix;
	}

	/**
	 * Renders entities to the shadow map. Each model is first bound and then all of
	 * the entities using that model are rendered to the shadow map.
	 * 
	 * @param entities
	 *            - the entities to be rendered to the shadow map.
	 */
	protected void render(HashMap<Integer, HashMap<Integer, Entity>> entities)
	{
		for (Map.Entry<Integer, HashMap<Integer, Entity>> entry : entities.entrySet())
		{
			for (Map.Entry<Integer, Entity> innerEntry : entry.getValue().entrySet())
			{
				Model rawModel = TexturedModel.getTexturedModel
						(innerEntry
								.getValue()
								.getAsset())
						.getModel();
				bindModel(rawModel);
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, TexturedModel.getTexturedModel(innerEntry.getValue().getAsset()).getTexture().getID());
				prepareInstance(innerEntry.getValue());
				GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
		}
		if (MouseController.getInstance().getEntityHolder() != null)
		{

			Model rawModel = TexturedModel.getTexturedModel(MouseController.getInstance().getEntityHolder().getAsset()).getModel();
			bindModel(rawModel);
			prepareInstance(MouseController.getInstance().getEntityHolder());
			GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}

	/**
	 * Binds a raw model before rendering. Only the attribute 0 is enabled here
	 * because that is where the positions are stored in the VAO, and only the
	 * positions are required in the vertex shader.
	 * 
	 * @param rawModel
	 *            - the model to be bound.
	 */
	private void bindModel(Model rawModel)
	{
		GL30.glBindVertexArray(rawModel.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}

	/**
	 * Prepares an entity to be rendered. The model matrix is created in the usual
	 * way and then multiplied with the projection and view matrix (often in the
	 * past we've done this in the vertex shader) to create the mvp-matrix. This is
	 * then loaded to the vertex shader as a uniform.
	 * 
	 * @param entity
	 *            - the entity to be prepared for rendering.
	 */
	private void prepareInstance(Entity entity)
	{
		Matrix4f modelMatrix = Maths.createTransMat(entity.getPosition(), entity.getRotation(), entity.getScale());
		Matrix4f mvpMatrix = Matrix4f.mul(projectionViewMatrix, modelMatrix, null);
		shader.loadMvpMatrix(mvpMatrix);
	}

}
