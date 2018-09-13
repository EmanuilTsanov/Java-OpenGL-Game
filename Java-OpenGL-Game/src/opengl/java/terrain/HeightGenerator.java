package opengl.java.terrain;

import java.util.Random;

public class HeightGenerator
{
	private static final float AMPLITUDE = 70f;
	private static final int OCTAVES = 3;
	private static final float ROUGHNESS = 0.1f;

	private Random rand = new Random();
	private int seed;

	public HeightGenerator()
	{
		this.seed = rand.nextInt(1000000000);
	}

	public float generateHeight(int x, int z)
	{
		float total = 0;
		float d = (float) Math.pow(2, OCTAVES - 1);
		for (int i = 0; i < OCTAVES; i++)
		{
			float freq = (float) (Math.pow(2, i) / d);
			float amp = (float) Math.pow(ROUGHNESS, i) * AMPLITUDE;
			total += getInterpolatedNoise(x * freq, z * freq) * amp;
		}
		return total;
	}

	private float getInterpolatedNoise(float x, float z)
	{
		int x1 = (int) x;
		int z1 = (int) z;
		float fracX = x - x1;
		float fracZ = z - z1;

		float v1 = getSmoothNoise(x1, z1);
		float v2 = getSmoothNoise(x1 + 1, z1);
		float v3 = getSmoothNoise(x1, z1 + 1);
		float v4 = getSmoothNoise(x1 + 1, z1 + 1);
		float i1 = interpolate(v1, v2, fracX);
		float i2 = interpolate(v3, v4, fracX);
		return interpolate(i1, i2, fracZ);
	}

	private float interpolate(float a, float b, float blend)
	{
		double theta = blend * Math.PI;
		float f = (float) (1f - Math.cos(theta)) * 0.5f;
		return a * (1f - f) + b * f;
	}

	public float getSmoothNoise(int x, int z)
	{
		float corners = (getNoise(x - 1, z - 1) + getNoise(x + 1, z - 1) + getNoise(x - 1, z + 1) + getNoise(x + 1, z + 1)) / 16f;
		float sides = (getNoise(x - 1, z) + getNoise(x + 1, z) + getNoise(x, z - 1) + getNoise(x, z + 1)) / 8f;
		float center = getNoise(x, z) / 4f;
		return corners + sides + center;
	}

	public float getNoise(int x, int z)
	{
		rand.setSeed(x * 55712 + z * 412955 + seed);
		return rand.nextFloat() * 2f - 1f;
	}
}
