package opengl.java.shader;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.lighting.Light;
import opengl.java.maths.Maths;

public class EntityShader extends ShaderProgram
{
	private static final String VERTEX_SHADER = "main-vertex";
	private static final String FRAGMENT_SHADER = "main-fragment";

	private int modelMatrixLocation;
	private int projectionMatrixLocation;
	private int viewMatrixLocation;
	private int lightPositionLocation;
	private int lightColorLocation;
	private int shineDamperLocation;
	private int reflectivityLocation;
	private int useFakeLightingLocation;

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
		lightPositionLocation = super.getUniformLocation("lightPosition");
		lightColorLocation = super.getUniformLocation("lightColor");
		shineDamperLocation = super.getUniformLocation("shineDamper");
		reflectivityLocation = super.getUniformLocation("reflectivity");
		useFakeLightingLocation = super.getUniformLocation("useFakeLighting");
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

	public void loadLight(Light light)
	{
		super.loadVector3f(lightPositionLocation, light.getPosition());
		super.loadVector3f(lightColorLocation, light.getColor());
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
}
