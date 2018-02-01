package opengl.java.shader;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.lighting.Light;
import opengl.java.texture.ModelTexture;
import opengl.java.view.Camera;

public class BasicShader extends ShaderProgram
{
	private static final String BASIC_V_SHADER = "vertex";
	private static final String BASIC_F_SHADER = "fragment";

	private int loc_modelMatrix;
	private int loc_projectionMatrix;
	private int loc_viewMatrix;

	private int loc_lightPosition;
	private int loc_lightColor;

	private int loc_shineDamper;
	private int loc_reflectivity;
	private int loc_useFakeLighting;

	private int loc_cameraPosition;

	public BasicShader()
	{
		super(BASIC_V_SHADER, BASIC_F_SHADER);
	}

	@Override
	public void bindAllAttributes()
	{
		super.bindAttribute(0, "in_position");
		super.bindAttribute(1, "in_textureCoords");
		super.bindAttribute(2, "in_normal");
	}

	@Override
	public void getAllUniformLocations()
	{
		loc_modelMatrix = super.getUniformLocation("modelMatrix");
		loc_projectionMatrix = super.getUniformLocation("projectionMatrix");
		loc_viewMatrix = super.getUniformLocation("viewMatrix");

		loc_lightPosition = super.getUniformLocation("lightPosition");
		loc_lightColor = super.getUniformLocation("lightColor");

		loc_shineDamper = super.getUniformLocation("shineDamper");
		loc_reflectivity = super.getUniformLocation("reflectivity");
		loc_useFakeLighting = super.getUniformLocation("useFakeLighting");

		loc_cameraPosition = super.getUniformLocation("cameraPosition");
	}

	public void loadTransformationMatrix(Vector3f position, Vector3f rotation, float scale)
	{
		super.loadMatrix(loc_modelMatrix, Maths.createTransMat(position, rotation, scale));
	}

	public void loadProjectionMatrix()
	{
		super.loadMatrix(loc_projectionMatrix, Maths.getProjectionMatrix());
	}

	public void loadViewMatrix(Camera camera)
	{
		super.loadMatrix(loc_viewMatrix, Maths.createViewMatrix());
		super.loadVector3f(loc_cameraPosition, camera.getPosition());
	}

	public void loadLight(Light light)
	{
		super.loadVector3f(loc_lightPosition, light.getPosition());
		super.loadVector3f(loc_lightColor, light.getColor());
	}

	public void loadTextureVariables(ModelTexture texture)
	{
		super.loadFloat(loc_shineDamper, texture.getShineDamper());
		super.loadFloat(loc_reflectivity, texture.getReflectivity());
		super.loadBoolean(loc_useFakeLighting, texture.shouldUseFakeLighting());
	}
}
