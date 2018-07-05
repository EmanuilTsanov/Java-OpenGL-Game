package opengl.java.shader;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;

public class GUIShader extends ShaderProgram
{
	private static final String BASIC_V_SHADER = "vGui";
	private static final String BASIC_F_SHADER = "fGui";

	private int loc_modelMatrix;
	private int loc_color;

	public GUIShader()
	{
		super(BASIC_V_SHADER, BASIC_F_SHADER);
	}

	@Override
	public void bindAllAttributes()
	{
		super.bindAttribute(0, "vertex");
	}

	@Override
	public void getAllUniformLocations()
	{
		loc_modelMatrix = super.getUniformLocation("modelMat");
		loc_color = super.getUniformLocation("inputColor");
	}

	public void loadTransformationMatrix(Vector3f position, Vector3f rotation, float scale)
	{
		super.loadMatrix(loc_modelMatrix, Maths.createTransMat(position, rotation, scale));
	}
	
	public void loadColor(Vector3f color) {
		super.loadVector3f(loc_color, color);
	}
}
