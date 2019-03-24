package opengl.java.shader;

import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

public class FontShader extends ShaderProgram
{
	private static final String VERTEX_SHADER = "font-vertex";
	private static final String FRAGMENT_SHADER = "font-fragment";

	private int color_loc;

	public FontShader()
	{
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}

	@Override
	public void bindAttributes()
	{
		super.bindAttribute(0, "vertex");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	public void getAllUniformLocations()
	{
		color_loc = super.getUniformLocation("color");
	}

	public void loadColor(Vector3f color)
	{
		GL20.glUniform3f(color_loc, color.x, color.y, color.z);
	}
}
