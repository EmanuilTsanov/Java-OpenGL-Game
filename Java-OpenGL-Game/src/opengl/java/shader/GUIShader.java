package opengl.java.shader;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.maths.Maths;

public class GUIShader extends ShaderProgram
{
	private static final String VERTEX_SHADER = "gui-vertex";
	private static final String FRAGMENT_SHADER = "gui-fragment";

	private int loc_modelMatrix;
	private int loc_mode;
	private int loc_color;

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
		loc_mode = super.getUniformLocation("mode");
		loc_color = super.getUniformLocation("color");
	}

	public void loadTransformationMatrix(Vector3f position, Vector3f rotation, float scale)
	{
		super.loadMatrix(loc_modelMatrix, Maths.createTransMat(position, rotation, scale));
	}

	public void loadMode(int mode)
	{
		super.loadInt(loc_mode, mode);
	}

	public void loadColor(Vector3f color)
	{
		super.loadVector3f(loc_color, color);
	}
}
