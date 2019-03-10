package opengl.java.shader;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.lighting.Light;
import opengl.java.maths.Maths;

public class EntityShader extends ShaderProgram
{
	private static final int MAX_LIGHTS = 4;

	private static final String VERTEX_SHADER = "main-vertex";
	private static final String FRAGMENT_SHADER = "main-fragment";

	private int modelMatrixLocation;
	private int projectionMatrixLocation;
	private int viewMatrixLocation;
	private int lightPositionLocation[];
	private int lightColorLocation[];
	private int attenuationLocation[];
	private int shineDamperLocation;
	private int reflectivityLocation;
	private int useFakeLightingLocation;
	private int skyColorLocation;

	public EntityShader()
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
		modelMatrixLocation = super.getUniformLocation("modelMatrix");
		projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
		viewMatrixLocation = super.getUniformLocation("viewMatrix");
		shineDamperLocation = super.getUniformLocation("shineDamper");
		reflectivityLocation = super.getUniformLocation("reflectivity");
		useFakeLightingLocation = super.getUniformLocation("useFakeLighting");
		skyColorLocation = super.getUniformLocation("skyColor");
		lightPositionLocation = new int[MAX_LIGHTS];
		lightColorLocation = new int[MAX_LIGHTS];
		attenuationLocation = new int[MAX_LIGHTS];
		for (int i = 0; i < MAX_LIGHTS; i++)
		{
			lightPositionLocation[i] = super.getUniformLocation("lightPosition[" + i + "]");
			lightColorLocation[i] = super.getUniformLocation("lightColor[" + i + "]");
			attenuationLocation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
	}

	public void loadTransformationMatrix(Vector3f position, Vector3f rotation, float scale)
	{
		super.loadMatrix(modelMatrixLocation, Maths.createTransMat(position, rotation, scale));
	}

	public void loadProjectionMatrix()
	{
		super.loadMatrix(projectionMatrixLocation, Maths.getProjectionMatrix());
	}

	public void loadViewMatrix()
	{
		super.loadMatrix(viewMatrixLocation, Maths.createViewMatrix());
	}

	public void loadLights(List<Light> lights)
	{
		for (int i = 0; i < MAX_LIGHTS; i++)
		{
			if (i < lights.size())
			{
				super.loadVector3f(lightPositionLocation[i], lights.get(i).getPosition());
				super.loadVector3f(lightColorLocation[i], lights.get(i).getColor());
				super.loadVector3f(attenuationLocation[i], lights.get(i).getAttenuation());
			}
			else
			{
				super.loadVector3f(lightPositionLocation[i], new Vector3f(0, 0, 0));
				super.loadVector3f(lightColorLocation[i], new Vector3f(0, 0, 0));
				super.loadVector3f(attenuationLocation[i], new Vector3f(1, 0, 0));
			}
		}
	}

	public void loadSpecularValues(float shineDamper, float reflectivity)
	{
		super.loadFloat(shineDamperLocation, shineDamper);
		super.loadFloat(reflectivityLocation, reflectivity);
	}

	public void loadFakeLighting(boolean b)
	{
		super.loadBoolean(useFakeLightingLocation, b);
	}

	public void loadSkyColor(float r, float g, float b)
	{
		super.loadVector3f(skyColorLocation, new Vector3f(r, g, b));
	}
}
