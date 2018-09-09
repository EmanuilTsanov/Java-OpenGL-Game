package opengl.java.terrain;

public class TerrainTexturepack
{
	private TerrainTexture backgroundTexture;
	private TerrainTexture rTexture;
	private TerrainTexture gTexture;
	private TerrainTexture bTexture;

	public TerrainTexturepack(TerrainTexture backgroundTexture, TerrainTexture rTexture, TerrainTexture gTexture,
			TerrainTexture bTexture)
	{
		super();
		this.backgroundTexture = backgroundTexture;
		this.rTexture = rTexture;
		this.gTexture = gTexture;
		this.bTexture = bTexture;
	}

	public TerrainTexture getBackgroundTexture()
	{
		return backgroundTexture;
	}

	public TerrainTexture getrTexture()
	{
		return rTexture;
	}

	public TerrainTexture getgTexture()
	{
		return gTexture;
	}

	public TerrainTexture getbTexture()
	{
		return bTexture;
	}
}
