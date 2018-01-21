package opengl.java.texture;

public class ModelTexture
{
	private int textureID;

	private float shineDamper = 10;
	private float reflectivity = 0;

	public ModelTexture(int textureID)
	{
		this.textureID = textureID;
	}

	public int getID()
	{
		return this.textureID;
	}

	public ModelTexture setShineDamper(float shineDamper)
	{
		this.shineDamper = shineDamper;
		return this;
	}

	public ModelTexture setReflectivity(float reflectivity)
	{
		this.reflectivity = reflectivity;
		return this;
	}

	public float getShineDamper()
	{
		return shineDamper;
	}

	public float getReflectivity()
	{
		return reflectivity;
	}
}
