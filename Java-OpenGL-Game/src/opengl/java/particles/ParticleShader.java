package opengl.java.particles;

import opengl.java.maths.Maths;
import opengl.java.shader.ShaderProgram;

public class ParticleShader extends ShaderProgram
{

	private static final String VERTEX_FILE = "particle-vertex";
	private static final String FRAGMENT_FILE = "particle-fragment";

	private int locationNumberOfRows;
	private int locationProjectionMatrix;

	public ParticleShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations()
	{
		locationNumberOfRows = super.getUniformLocation("numberOfRows");
		locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "modelViewMatrix");
		super.bindAttribute(5, "textureOffsets");
		super.bindAttribute(6, "blendFactor");
	}

	protected void loadNumberOfRows(float numberOfRows)
	{
		super.loadFloat(locationNumberOfRows, numberOfRows);
	}

	protected void loadProjectionMatrix()
	{
		super.loadMatrix(locationProjectionMatrix, Maths.getProjectionMatrix());
	}
}
