package opengl.java.material;

import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;

public class Material
{
	private int id;
	private float shininess;
	private Vector3f diffuse;
	private Vector3f specular;

	private static HashMap<Integer, Material> materials = new HashMap<Integer, Material>();

	public static final Material defaultMaterial = new Material(0, new Vector3f(0.3f, 0.3f, 0.3f), new Vector3f(0.2f, 0.2f, 0.2f), 6.0f);

	public Material(int id, Vector3f diffuse, Vector3f specular, float shininess)
	{
		this.id = id;
		this.shininess = shininess;
		this.diffuse = diffuse;
		this.specular = specular;
		materials.put(id, this);
	}

	public int getID()
	{
		return id;
	}

	public static Material getMaterial(int id)
	{
		return materials.get(id);
	}

	public float getShininess()
	{
		return shininess;
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
