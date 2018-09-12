package opengl.java.shader;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.lighting.Light;
import opengl.java.shadows.ShadowBox;
import opengl.java.view.Camera;

public class TerrainShader extends ShaderProgram
{
	private static final String BASIC_V_SHADER = "tvertex";
	private static final String BASIC_F_SHADER = "tfragment";

	private int loc_mat_trans;
	private int loc_mat_project;
	private int loc_mat_view;
	private int locToShadowMapSpace;
	private int locShadowMap;
	private int locShadowDistance;
	private int loc_mapSize;
	private int loc_backgroundTexture;
	private int loc_rTexture;
	private int loc_gTexture;
	private int loc_bTexture;
	private int loc_blendMap;
	private int loc_lightPosition;
	private int loc_lightColor;

	public TerrainShader()
	{
		super(BASIC_V_SHADER, BASIC_F_SHADER);
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
		locToShadowMapSpace = super.getUniformLocation("toShadowMapSpace");
		locShadowMap = super.getUniformLocation("shadowMap");
		locShadowDistance = super.getUniformLocation("shadowDistance");
		loc_mapSize = super.getUniformLocation("mapSize");
		loc_backgroundTexture = super.getUniformLocation("backgroundTexture");
		loc_rTexture = super.getUniformLocation("rTexture");
		loc_gTexture = super.getUniformLocation("gTexture");
		loc_bTexture = super.getUniformLocation("bTexture");
		loc_blendMap = super.getUniformLocation("blendMap");

		loc_lightPosition = super.getUniformLocation("lightPosition");
		loc_lightColor = super.getUniformLocation("lightColor");
	}

	public void connectTextureUnits()
	{
		super.loadInt(loc_backgroundTexture, 0);
		super.loadInt(loc_rTexture, 1);
		super.loadInt(loc_gTexture, 2);
		super.loadInt(loc_bTexture, 3);
		super.loadInt(loc_blendMap, 4);
	}

	public void loadTransformationMatrix(Vector3f position, Vector3f rotation, float scale)
	{
		super.loadMatrix(loc_mat_trans, Maths.createTransMat(position, rotation, scale));
	}

	public void loadProjectionMatrix()
	{
		super.loadMatrix(loc_mat_project, Maths.getProjectionMatrix());
	}

	public void loadViewMatrix(Camera camera)
	{
		super.loadMatrix(loc_mat_view, Maths.createViewMatrix());
	}

	public void loadToShadowMapSpace(Matrix4f mat)
	{
		super.loadMatrix(locToShadowMapSpace, mat);
	}

	public void loadShadowMap()
	{
		super.loadInt(locShadowMap, 5);
	}

	public void loadLight(Light light)
	{
		super.loadVector3f(loc_lightPosition, light.getPosition());
		super.loadVector3f(loc_lightColor, light.getColor());
	}

	public void loadShadowDistance()
	{
		super.loadFloat(locShadowDistance, ShadowBox.getShadowDistance());
	}

	public void loadMapSize(float mapSize)
	{
		super.loadFloat(loc_mapSize, mapSize);
	}
}
