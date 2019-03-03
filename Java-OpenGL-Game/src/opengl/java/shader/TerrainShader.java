package opengl.java.shader;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.lighting.Light;
import opengl.java.maths.Maths;

public class TerrainShader extends ShaderProgram
{
	private static final String VERTEX_SHADER = "terrain-vertex";
	private static final String FRAGMENT_SHADER = "terrain-fragment";

	private int modelMatrixLocation;
	private int projectionMatrixLocation;
	private int viewMatrixLocation;
	private int lightPositionLocation[];
	private int lightColorLocation[];
	private int skyColorLocation;

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
		modelMatrixLocation = super.getUniformLocation("modelMatrix");
		projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
		viewMatrixLocation = super.getUniformLocation("viewMatrix");
		skyColorLocation = super.getUniformLocation("skyColor");
		lightPositionLocation = new int[4];
		lightColorLocation = new int[4];
		for (int i = 0; i < 4; i++)
		{
			lightPositionLocation[i] = super.getUniformLocation("lightPosition[" + i + "]");
			lightColorLocation[i] = super.getUniformLocation("lightColor[" + i + "]");
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
		for (int i = 0; i < 4; i++)
		{
			if (i < lights.size())
			{
				super.loadVector3f(lightPositionLocation[i], lights.get(i).getPosition());
				super.loadVector3f(lightColorLocation[i], lights.get(i).getColor());
			}
			else
			{
				super.loadVector3f(lightPositionLocation[i], new Vector3f(0, 0, 0));
				super.loadVector3f(lightColorLocation[i], new Vector3f(0, 0, 0));
			}
		}
	}

	public void loadSkyColor(float r, float g, float b)
	{
		super.loadVector3f(skyColorLocation, new Vector3f(r, g, b));
	}
}
