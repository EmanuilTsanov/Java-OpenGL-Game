package opengl.java.shader;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.lighting.Light;
import opengl.java.maths.Maths;

public class TerrainShader extends ShaderProgram
{
	private static final String VERTEX_SHADER = "terrain-vertex";
	private static final String FRAGMENT_SHADER = "terrain-fragment";

	private int loc_mat_trans;
	private int loc_mat_project;
	private int loc_mat_view;
	private int loc_lightPosition;
	private int loc_lightColor;

	public TerrainShader()
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
		loc_mat_trans = super.getUniformLocation("modelMat");
		loc_mat_project = super.getUniformLocation("projectionMat");
		loc_mat_view = super.getUniformLocation("viewMat");
		loc_lightPosition = super.getUniformLocation("lightPosition");
		loc_lightColor = super.getUniformLocation("lightColor");
	}

	public void loadTransformationMatrix(Vector3f position, Vector3f rotation, float scale)
	{
		super.loadMatrix(loc_mat_trans, Maths.createTransMat(position, rotation, scale));
	}

	public void loadProjectionMatrix()
	{
		super.loadMatrix(loc_mat_project, Maths.getProjectionMatrix());
	}

	public void loadViewMatrix()
	{
		super.loadMatrix(loc_mat_view, Maths.createViewMatrix());
	}

	public void loadLight(Light light)
	{
		super.loadVector3f(loc_lightPosition, light.getPosition());
		super.loadVector3f(loc_lightColor, light.getColor());
	}
}
