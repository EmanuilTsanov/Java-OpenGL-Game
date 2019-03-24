package opengl.java.shader;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import opengl.java.lighting.Light;

public class NormalMappingShader extends ShaderProgram
{

	private static final int MAX_LIGHTS = 4;

	private static final String VERTEX_FILE = "src/normalMappingRenderer/normalMapVShader.txt";
	private static final String FRAGMENT_FILE = "src/normalMappingRenderer/normalMapFShader.txt";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPositionEyeSpace[];
	private int location_lightColour[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColour;
	private int location_plane;
	private int location_modelTexture;

	public NormalMappingShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	public void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations()
	{
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_skyColour = super.getUniformLocation("skyColour");
		location_plane = super.getUniformLocation("plane");
		location_modelTexture = super.getUniformLocation("modelTexture");

		location_lightPositionEyeSpace = new int[MAX_LIGHTS];
		location_lightColour = new int[MAX_LIGHTS];
		location_attenuation = new int[MAX_LIGHTS];
		for (int i = 0; i < MAX_LIGHTS; i++)
		{
			location_lightPositionEyeSpace[i] = super.getUniformLocation("lightPositionEyeSpace[" + i + "]");
			location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
	}

	public void connectTextureUnits()
	{
		super.loadInt(location_modelTexture, 0);
	}

	public void loadClipPlane(Vector4f plane)
	{
		super.loadVector4f(location_plane, plane);
	}

	public void loadSkyColor(float r, float g, float b)
	{
		super.loadVector3f(location_skyColour, new Vector3f(r, g, b));
	}

	public void loadShineVariables(float damper, float reflectivity)
	{
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}

	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadLights(List<Light> lights, Matrix4f viewMatrix)
	{
		for (int i = 0; i < MAX_LIGHTS; i++)
		{
			if (i < lights.size())
			{
				super.loadVector3f(location_lightPositionEyeSpace[i], getEyeSpacePosition(lights.get(i), viewMatrix));
				super.loadVector3f(location_lightColour[i], lights.get(i).getColor());
				super.loadVector3f(location_attenuation[i], lights.get(i).getAttenuation());
			}
			else
			{
				super.loadVector3f(location_lightPositionEyeSpace[i], new Vector3f(0, 0, 0));
				super.loadVector3f(location_lightColour[i], new Vector3f(0, 0, 0));
				super.loadVector3f(location_attenuation[i], new Vector3f(1, 0, 0));
			}
		}
	}

	public void loadViewMatrix(Matrix4f viewMatrix)
	{
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}

	public void loadProjectionMatrix(Matrix4f projection)
	{
		super.loadMatrix(location_projectionMatrix, projection);
	}

	private Vector3f getEyeSpacePosition(Light light, Matrix4f viewMatrix)
	{
		Vector3f position = light.getPosition();
		Vector4f eyeSpacePos = new Vector4f(position.x, position.y, position.z, 1f);
		Matrix4f.transform(viewMatrix, eyeSpacePos, eyeSpacePos);
		return new Vector3f(eyeSpacePos);
	}

}
