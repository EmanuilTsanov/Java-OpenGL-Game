package opengl.java.management;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.lighting.Light;

public class LightManager
{
	private Light sun = new Light(new Vector3f(1000000, 1500000, -1000000), new Vector3f(1.0f, 1.0f, 1.0f));

	private static LightManager singleton = new LightManager();

	public static LightManager getInstance()
	{
		return singleton;
	}

	public Light getSun()
	{
		return sun;
	}
}
