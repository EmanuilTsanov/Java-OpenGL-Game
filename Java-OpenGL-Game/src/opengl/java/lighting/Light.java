package opengl.java.lighting;

import org.lwjgl.util.vector.Vector3f;

public class Light
{
	private Vector3f position;

	private Vector3f ambient;
	private Vector3f diffuse;
	private Vector3f specular;

	public Light(Vector3f position, Vector3f ambient, Vector3f diffuse, Vector3f specular)
	{
		this.position = position;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
	}

	public Vector3f getPosition()
	{
		return position;
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
