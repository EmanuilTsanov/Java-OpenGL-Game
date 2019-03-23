package opengl.java.render;

import java.util.ArrayList;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Entity;
import opengl.java.entity.EntityBase;
import opengl.java.maths.Maths;
import opengl.java.model.RawModel;
import opengl.java.shader.EntityShader;
import opengl.java.view.Camera;

public class EntityRenderer
{
	public EntityRenderer()
	{
		MainRenderer.enableCulling();
	}

	public void render(EntityShader shader)
	{
		for (Map.Entry<EntityBase, ArrayList<Entity>> outer : Entity.getEntities().entrySet())
		{
			EntityBase base = outer.getKey();
			RawModel model = base.getModel();
			int texture = base.getTexture();
			GL30.glBindVertexArray(model.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
			shader.loadSpecularValues(base.getShineDamper(), base.getReflectivity());
			shader.loadFakeLighting(base.hasFakeLighting());
			if (base.hasTransparency())
			{
				MainRenderer.disableCulling();
			}
			for (Entity entity : outer.getValue())
			{
				if (shouldSkipEntity(entity))
					continue;
				shader.loadTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			MainRenderer.enableCulling();
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(2);
			GL30.glBindVertexArray(0);
		}
	}

	public boolean shouldSkipEntity(Entity entity)
	{
		Vector3f camPosition = Camera.getPosition();
		float dx = (float) (Maths.getFarPlane() * Math.sin(Math.toRadians(Camera.getRotation().y - 90)));
		float dy = (float) (Maths.getFarPlane() * Math.cos(Math.toRadians(Camera.getRotation().y - 90)));
		float dx1 = camPosition.x + dx;
		float dy1 = camPosition.z - dy;
		float dx2 = camPosition.x - dx;
		float dy2 = camPosition.z + dy;
		float d = (entity.getPosition().x - dx1) * (dy2 - dy1) - (entity.getPosition().z - dy1) * (dx2 - dx1);
		if (entity.getPosition().x - camPosition.x > Maths.getFarPlane() || camPosition.x - entity.getPosition().x > Maths.getFarPlane()
				|| entity.getPosition().z - camPosition.z > Maths.getFarPlane() || camPosition.z - entity.getPosition().z > Maths.getFarPlane() || d < 0)
			return true;
		return false;
	}
}
