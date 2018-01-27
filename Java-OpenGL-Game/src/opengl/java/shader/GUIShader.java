package opengl.java.shader;

public class GUIShader extends ShaderProgram
{
	private static final String BASIC_V_SHADER = "vGui";
	private static final String BASIC_F_SHADER = "fGui";

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
	}
}
