package opengl.java.texture;

public class BaseTexture
{
	private int textureID;

	private float shineDamper = 10;
	private float reflectivity = 0;

	public BaseTexture(int textureID)
	{
		this.textureID = textureID;
	}

	public int getID()
	{
		return this.textureID;
	}

	public BaseTexture setShineDamper(float shineDamper)
	{
		this.shineDamper = shineDamper;
		return this;
	}

	public BaseTexture setReflectivity(float reflectivity)
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
