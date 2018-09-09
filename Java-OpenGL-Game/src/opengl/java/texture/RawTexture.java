package opengl.java.texture;

public class RawTexture
{
	private int textureID;

	private float shineDamper = 1;
	private float reflectivity = 0;

	private boolean transparency;
	private boolean useFakeLighting;

	public RawTexture(int textureID)
	{
		this.textureID = textureID;
	}

	public int getID()
	{
		return this.textureID;
	}

	public float getShineDamper()
	{
		return shineDamper;
	}

	public RawTexture setShineDamper(float shineDamper)
	{
		this.shineDamper = shineDamper;
		return this;
	}

	public float getReflectivity()
	{
		return reflectivity;
	}

	public RawTexture setReflectivity(float reflectivity)
	{
		this.reflectivity = reflectivity;
		return this;
	}

	public boolean isTransparent()
	{
		return transparency;
	}

	public RawTexture setTransparency(boolean transparency)
	{
		this.transparency = transparency;
		return this;
	}

	public boolean shouldUseFakeLighting()
	{
		return useFakeLighting;
	}

	public RawTexture setUseFakeLighting(boolean useFakeLighting)
	{
		this.useFakeLighting = useFakeLighting;
		return this;
	}
}
