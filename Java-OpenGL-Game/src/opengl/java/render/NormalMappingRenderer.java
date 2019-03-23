package opengl.java.render;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import opengl.java.entity.Entity;
import opengl.java.entity.EntityBase;
import opengl.java.lighting.Light;
import opengl.java.maths.Maths;
import opengl.java.model.RawModel;
import opengl.java.shader.NormalMappingShader;
import opengl.java.view.Camera;

public class NormalMappingRenderer
{
	private NormalMappingShader shader;

	public NormalMappingRenderer(Matrix4f projectionMatrix)
	{
		this.shader = new NormalMappingShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
	}

	public void render(Map<EntityBase, List<Entity>> entities, Vector4f clipPlane, List<Light> lights, Camera camera)
	{
		shader.start();
		prepare(clipPlane, lights, camera);
		for (EntityBase model : entities.keySet())
		{
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for (Entity entity : batch)
			{
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
		shader.stop();
	}

	public void cleanUp()
	{
	}

	private void prepareTexturedModel(EntityBase model)
	{
		RawModel rawModel = model.getModel();
		GL30.glBindVertexArray(rawModel.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		int texture = model.getTexture();
		shader.loadShineVariables(model.getShineDamper(), model.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
	}

	private void unbindTexturedModel()
	{
		MainRenderer.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	private void prepareInstance(Entity entity)
	{
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
	}

	private void prepare(Vector4f clipPlane, List<Light> lights, Camera camera)
	{
		shader.loadClipPlane(clipPlane);
		// need to be public variables in MasterRenderer
		shader.loadSkyColor(MainRenderer.RED, MainRenderer.GREEN, MainRenderer.BLUE);
		Matrix4f viewMatrix = Maths.createViewMatrix();

		shader.loadLights(lights, viewMatrix);
		shader.loadViewMatrix(viewMatrix);
	}

}
