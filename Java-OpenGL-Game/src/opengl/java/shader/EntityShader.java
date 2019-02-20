package opengl.java.shader;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.maths.Maths;

public class EntityShader extends ShaderProgram
{
	private static final String VERTEX_SHADER = "main-vertex";
	private static final String FRAGMENT_SHADER = "main-fragment";

	private int modelMatrixLocation;
	private int projectionMatrixLocation;
	private int viewMatrixLocation;

	public EntityShader()
	{
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}

	@Override
	public void bindAllAttributes()
	{
		super.bindAttribute(0, "vertex");
		super.bindAttribute(1, "texCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	public void getAllUniformLocations()
	{
		modelMatrixLocation = super.getUniformLocation("modelMatrix");
		projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
		viewMatrixLocation = super.getUniformLocation("viewMatrix");
	}

	public void loadTransformationMatrix(Vector3f position, Vector3f rotation, float scale)
	{
		super.loadMatrix(modelMatrixLocation, Maths.createTransMat(position, rotation, scale));
	}

	public void loadProjectionMatrix()
	{
		super.loadMatrix(projectionMatrixLocation, Maths.getProjectionMatrix());
	}

	public void loadViewMatrix()
	{
		super.loadMatrix(viewMatrixLocation, Maths.createViewMatrix());
	}
}
