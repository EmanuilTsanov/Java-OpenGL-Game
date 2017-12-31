package opengl.java.shader;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.view.Camera;

public class PickShader extends ShaderProgram
{
	private static final String BASIC_V_SHADER = "pvertex";
	private static final String BASIC_F_SHADER = "pfragment";

	private int loc_mat_trans;
	private int loc_mat_project;
	private int loc_mat_view;
	private int loc_vec_color;

	public PickShader()
	{
		super(BASIC_V_SHADER, BASIC_F_SHADER);
	}

	@Override
	public void bindAllAttributes()
	{
		super.bindAttribute(0, "position");
	}

	@Override
	public void getAllUniformLocations()
	{
		loc_mat_trans = super.getUniformLocation("modelMat");
		loc_mat_project = super.getUniformLocation("projectionMat");
		loc_mat_view = super.getUniformLocation("viewMat");
		loc_vec_color = super.getUniformLocation("color");
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
	
	public void loadColor(Vector3f color) {
		super.loadVector3f(loc_vec_color, color);
	}
}
