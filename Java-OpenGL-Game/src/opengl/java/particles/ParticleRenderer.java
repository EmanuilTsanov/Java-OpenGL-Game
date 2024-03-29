package opengl.java.particles;

import java.nio.FloatBuffer;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.loader.ModelLoader;
import opengl.java.maths.Maths;
import opengl.java.model.RawModel;

public class ParticleRenderer
{
	private static final float[] VERTICES = { -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f };
	private static final int MAX_INSTANCES = 10000;
	private static final int INSTANCE_DATA_LENGTH = 21;

	private static FloatBuffer buffer = BufferUtils.createFloatBuffer(MAX_INSTANCES * INSTANCE_DATA_LENGTH);

	private RawModel quad;
	private ParticleShader shader;

	private int vbo;
	private int pointer = 0;

	protected ParticleRenderer(ModelLoader loader)
	{
		this.vbo = ModelLoader.createEmptyVBO(INSTANCE_DATA_LENGTH * MAX_INSTANCES);
		quad = ModelLoader.loadToVAO(VERTICES, 2);
		ModelLoader.addInstancedAttribute(quad.getVAOID(), vbo, 1, 4, INSTANCE_DATA_LENGTH, 0);
		ModelLoader.addInstancedAttribute(quad.getVAOID(), vbo, 2, 4, INSTANCE_DATA_LENGTH, 4);
		ModelLoader.addInstancedAttribute(quad.getVAOID(), vbo, 3, 4, INSTANCE_DATA_LENGTH, 8);
		ModelLoader.addInstancedAttribute(quad.getVAOID(), vbo, 4, 4, INSTANCE_DATA_LENGTH, 12);
		ModelLoader.addInstancedAttribute(quad.getVAOID(), vbo, 5, 4, INSTANCE_DATA_LENGTH, 16);
		ModelLoader.addInstancedAttribute(quad.getVAOID(), vbo, 6, 1, INSTANCE_DATA_LENGTH, 20);
		shader = new ParticleShader();
		shader.start();
		shader.loadProjectionMatrix();
		shader.stop();
	}

	protected void render(Map<ParticleTexture, List<Particle>> particles)
	{
		Matrix4f viewMatrix = Maths.createViewMatrix();
		prepare();
		for (ParticleTexture texture : particles.keySet())
		{
			bindTexture(texture);
			List<Particle> list = particles.get(texture);
			pointer = 0;
			float[] VBOData = new float[list.size() * INSTANCE_DATA_LENGTH];
			for (Particle particle : list)
			{
				updateModelViewMatrix(particle.getPosition(), particle.getRotation(), particle.getScale(), viewMatrix, VBOData);
				updateTextureCoordInfo(particle, VBOData);
			}
			ModelLoader.updateVBO(vbo, VBOData, buffer);
			GL31.glDrawArraysInstanced(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount(), list.size());
		}
		finishRendering();
	}

	public void bindTexture(ParticleTexture texture)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		shader.loadNumberOfRows(texture.getNumberOfRows());
	}

	private void updateModelViewMatrix(Vector3f position, float rotation, float scale, Matrix4f viewMatrix, float[] VBOData)
	{
		Matrix4f modelMatrix = new Matrix4f();
		Matrix4f.translate(position, modelMatrix, modelMatrix);
		modelMatrix.m00 = viewMatrix.m00;
		modelMatrix.m01 = viewMatrix.m10;
		modelMatrix.m02 = viewMatrix.m20;
		modelMatrix.m10 = viewMatrix.m01;
		modelMatrix.m11 = viewMatrix.m11;
		modelMatrix.m12 = viewMatrix.m21;
		modelMatrix.m20 = viewMatrix.m02;
		modelMatrix.m21 = viewMatrix.m12;
		modelMatrix.m22 = viewMatrix.m22;
		Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0, 0, 1), modelMatrix, modelMatrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), modelMatrix, modelMatrix);
		Matrix4f modelViewMatrix = Matrix4f.mul(viewMatrix, modelMatrix, null);
		storeMatrixData(modelViewMatrix, VBOData);
	}

	private void storeMatrixData(Matrix4f matrix, float[] VBOData)
	{
		VBOData[pointer++] = matrix.m00;
		VBOData[pointer++] = matrix.m01;
		VBOData[pointer++] = matrix.m02;
		VBOData[pointer++] = matrix.m03;
		VBOData[pointer++] = matrix.m10;
		VBOData[pointer++] = matrix.m11;
		VBOData[pointer++] = matrix.m12;
		VBOData[pointer++] = matrix.m13;
		VBOData[pointer++] = matrix.m20;
		VBOData[pointer++] = matrix.m21;
		VBOData[pointer++] = matrix.m22;
		VBOData[pointer++] = matrix.m23;
		VBOData[pointer++] = matrix.m30;
		VBOData[pointer++] = matrix.m31;
		VBOData[pointer++] = matrix.m32;
		VBOData[pointer++] = matrix.m33;
	}
	
	public void updateTextureCoordInfo(Particle particle, float[] data) {
		data[pointer++] = particle.getOffset1().x;
		data[pointer++] = particle.getOffset1().y;
		data[pointer++] = particle.getOffset2().x;
		data[pointer++] = particle.getOffset2().y;
		data[pointer++] = particle.getBlend();
	}

	protected void cleanUp()
	{
	}

	private void prepare()
	{
		shader.start();
		GL30.glBindVertexArray(quad.getVAOID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL20.glEnableVertexAttribArray(3);
		GL20.glEnableVertexAttribArray(4);
		GL20.glEnableVertexAttribArray(5);
		GL20.glEnableVertexAttribArray(6);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(false);
	}

	private void finishRendering()
	{
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(3);
		GL20.glDisableVertexAttribArray(4);
		GL20.glDisableVertexAttribArray(5);
		GL20.glDisableVertexAttribArray(6);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

}
