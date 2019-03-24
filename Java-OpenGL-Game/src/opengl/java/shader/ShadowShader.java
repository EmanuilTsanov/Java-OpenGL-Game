package opengl.java.shader;

import org.lwjgl.util.vector.Matrix4f;

public class ShadowShader extends ShaderProgram
{

	private static final String VERTEX_FILE = "shadow-vertex";
	private static final String FRAGMENT_FILE = "shadow-fragment";

	private int location_mvpMatrix;

	public ShadowShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
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
	protected void bindAttributes()
	{
		super.bindAttribute(0, "in_position");
		super.bindAttribute(1, "textureCoords");
	}

}
