package opengl.java.shader;

import org.lwjgl.util.vector.Matrix4f;

public class ShadowShader extends ShaderProgram
{

	private static final String VERTEX_SHADER = "shadow-vertex";
	private static final String FRAGMENT_SHADER = "shadow-fragment";

	private int location_mvpMatrix;

	public ShadowShader()
	{
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}

	@Override
	protected void getAllUniformLocations()
	{
		location_mvpMatrix = super.getUniformLocation("mvpMatrix");
	}

	public void loadMvpMatrix(Matrix4f mvpMatrix)
	{
		super.loadMatrix(location_mvpMatrix, mvpMatrix);
	}

	@Override
	public void bindAllAttributes()
	{
		super.bindAttribute(0, "in_position");
		super.bindAttribute(1, "in_textureCoords");
	}
}
