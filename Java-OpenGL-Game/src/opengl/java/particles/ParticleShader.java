package opengl.java.particles;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import opengl.java.maths.Maths;
import opengl.java.shader.ShaderProgram;

public class ParticleShader extends ShaderProgram
{

	private static final String VERTEX_FILE = "particle-vertex";
	private static final String FRAGMENT_FILE = "particle-fragment";

	private int location_modelViewMatrix;
	private int location_projectionMatrix;
	private int locationOffset1;
	private int locationOffset2;
	private int locationTexCoordInfo;

	public ParticleShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations()
	{
		location_modelViewMatrix = super.getUniformLocation("modelViewMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		locationOffset1 = super.getUniformLocation("offset1");
		locationOffset2 = super.getUniformLocation("offset2");
		locationTexCoordInfo = super.getUniformLocation("texCoordInfo");
	}

	@Override
	protected void bindAllAttributes()
	{
		super.bindAttribute(0, "position");
	}

	protected void loadModelViewMatrix(Matrix4f modelView)
	{
		super.loadMatrix(location_modelViewMatrix, modelView);
	}

	protected void loadProjectionMatrix()
	{
		super.loadMatrix(location_projectionMatrix, Maths.getProjectionMatrix());
	}

	public void loadTextureCoordInfo(Vector2f offset1, Vector2f offset2, float numRows, float blend)
	{
		super.loadVector2f(locationOffset1, offset1);
		super.loadVector2f(locationOffset2, offset2);
		super.loadVector2f(locationTexCoordInfo, new Vector2f(numRows, blend));
	}

}
