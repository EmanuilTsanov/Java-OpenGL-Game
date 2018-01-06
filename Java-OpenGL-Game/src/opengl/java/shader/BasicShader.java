package opengl.java.shader;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.lighting.Light;
import opengl.java.material.Material;
import opengl.java.view.Camera;

public class BasicShader extends ShaderProgram
{
	private static final String BASIC_V_SHADER = "vertex";
	private static final String BASIC_F_SHADER = "fragment";

	private int locTransMat;
	private int locProjectionMat;
	private int locViewMat;

	private int locLightVector;
	private int locLightAmbient;
	private int locLightDiffuse;
	private int locLightSpecular;
	private int locMaterialDiffuse;
	private int locMaterialSpecular;
	private int locMaterialShininess;

	private int locCameraPosition;

	public BasicShader()
	{
		super(BASIC_V_SHADER, BASIC_F_SHADER);
	}

	@Override
	public void bindAllAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "texCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	public void getAllUniformLocations()
	{
		locTransMat = super.getUniformLocation("modelMat");
		locProjectionMat = super.getUniformLocation("projectionMat");
		locViewMat = super.getUniformLocation("viewMat");

		locCameraPosition = super.getUniformLocation("camPos");

		locLightVector = super.getUniformLocation("lightPosition");
		locLightAmbient = super.getUniformLocation("light.ambient");
		locLightDiffuse = super.getUniformLocation("light.diffuse");
		locLightSpecular = super.getUniformLocation("light.specular");

		locMaterialDiffuse = super.getUniformLocation("material.diffuse");
		locMaterialSpecular = super.getUniformLocation("material.specular");
		locMaterialShininess = super.getUniformLocation("material.shininess");
	}

	public void loadTransformationMatrix(Vector3f position, Vector3f rotation, float scale)
	{
		super.loadMatrix(locTransMat, Maths.createTransMat(position, rotation, scale));
	}

	public void loadProjectionMatrix()
	{
		super.loadMatrix(locProjectionMat, Maths.getProjectionMatrix());
	}

	public void loadViewMatrix(Camera camera)
	{
		super.loadMatrix(locViewMat, Maths.createViewMatrix());
		super.loadVector3f(locCameraPosition, camera.getPosition());
	}

	public void loadMaterialValues(Material material)
	{
		super.loadVector3f(locMaterialDiffuse, new Vector3f(0,0,0));
		super.loadVector3f(locMaterialSpecular, new Vector3f(1,1,1));
		super.loadFloat(locMaterialShininess, material.getShininess());
	}

	public void loadLight(Light light)
	{
		super.loadVector4f(locLightVector, light.getPosition());
		super.loadVector3f(locLightAmbient, light.getAmbient());
		super.loadVector3f(locLightDiffuse, light.getDiffuse());
		super.loadVector3f(locLightSpecular, light.getSpecular());
	}
}
