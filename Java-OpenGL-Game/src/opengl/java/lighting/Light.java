package opengl.java.lighting;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Light
{
	private Vector3f position;

	private Vector3f ambient;
	private Vector3f diffuse;
	private Vector3f specular;

	private boolean directional = true;

	private Vector3f dirVec = new Vector3f(-0.2f, -10000.0f, -0.3f);

	public Light(Vector3f ambient, Vector3f diffuse, Vector3f specular)
	{
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
	}

	public Vector4f getPosition()
	{
		if (directional)
			return new Vector4f(dirVec.x, dirVec.y, dirVec.z, 0.0f);
		else
			return new Vector4f(position.x, position.y, position.z, 1.0f);
	}

	public Light setDirectional(Vector3f position, boolean bool)
	{
		this.directional = bool;
		this.position = position;
		return this;
	}

	public Vector3f getAmbient()
	{
		return ambient;
	}

	public Vector3f getDiffuse()
	{
		return diffuse;
	}

	public Vector3f getSpecular()
	{
		return specular;
	}

}
