package opengl.java.material;

import java.util.HashMap;

public class Material
{
	private int id;
	private float shininess;

	private static HashMap<Integer, Material> materials = new HashMap<Integer, Material>();

	public static final Material defaultMaterial = new Material(0, 128.0f);

	public Material(int id, float shininess)
	{
		this.shininess = shininess;
		this.id = id;
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
}
