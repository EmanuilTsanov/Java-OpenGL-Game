package opengl.java.shader;

import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

public class FontShader extends ShaderProgram
{
	private static final String BASIC_V_SHADER = "vertex2";
	private static final String BASIC_F_SHADER = "fragment2";

	private int color_loc;

	public FontShader()
	{
		super(BASIC_V_SHADER, BASIC_F_SHADER);
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
		color_loc = super.getUniformLocation("color");
	}

	public void loadColor(Vector3f color)
	{
		GL20.glUniform3f(color_loc, color.x, color.y, color.z);
	}
}
