package opengl.java.shader;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.maths.Maths;

public class GUIShader extends ShaderProgram
{
	private static final String VERTEX_SHADER = "gui-vertex";
	private static final String FRAGMENT_SHADER = "gui-fragment";

	private int loc_modelMatrix;

	public GUIShader()
	{
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}

	@Override
	public void bindAllAttributes()
	{
		super.bindAttribute(0, "vertex");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	public void getAllUniformLocations()
	{
		loc_modelMatrix = super.getUniformLocation("modelMatrix");
	}

	public void loadTransformationMatrix(Vector3f position, Vector3f rotation, float scale)
	{
		super.loadMatrix(loc_modelMatrix, Maths.createTransMat(position, rotation, scale));
	}
}
