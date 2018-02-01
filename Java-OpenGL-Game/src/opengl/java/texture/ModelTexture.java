package opengl.java.texture;

public class ModelTexture
{
	private int textureID;

	private float shineDamper = 1;
	private float reflectivity = 0;

	private boolean transparency;
	private boolean useFakeLighting;

	public ModelTexture(int textureID)
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

	public ModelTexture setShineDamper(float shineDamper)
	{
		this.shineDamper = shineDamper;
		return this;
	}

	public float getReflectivity()
	{
		return reflectivity;
	}

	public ModelTexture setReflectivity(float reflectivity)
	{
		this.reflectivity = reflectivity;
		return this;
	}

	public boolean isTransparent()
	{
		return transparency;
	}

	public ModelTexture setTransparency(boolean transparency)
	{
		this.transparency = transparency;
		return this;
	}

	public boolean shouldUseFakeLighting()
	{
		return useFakeLighting;
	}

	public ModelTexture setUseFakeLighting(boolean useFakeLighting)
	{
		this.useFakeLighting = useFakeLighting;
		return this;
	}
}
