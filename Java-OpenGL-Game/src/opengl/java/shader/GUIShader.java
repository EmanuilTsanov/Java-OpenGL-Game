package opengl.java.shader;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;

public class GUIShader extends ShaderProgram
{
	private static final String BASIC_V_SHADER = "vGui";
	private static final String BASIC_F_SHADER = "fGui";

	private int loc_modelMatrix;

	public GUIShader()
	{
		super(BASIC_V_SHADER, BASIC_F_SHADER);
	}

	@Override
	public void bindAllAttributes()
	{
		super.bindAttribute(0, "vertex");
		super.bindAttribute(1, "texCoords");
	}

	@Override
	public void getAllUniformLocations()
	{
		loc_modelMatrix = super.getUniformLocation("modelMat");
	}

	public void loadTransformationMatrix(Vector3f position, Vector3f rotation, float scale)
	{
		super.loadMatrix(loc_modelMatrix, Maths.createTransMat(position, rotation, scale));
	}
}
