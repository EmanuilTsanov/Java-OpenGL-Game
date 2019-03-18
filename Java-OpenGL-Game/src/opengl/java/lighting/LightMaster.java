package opengl.java.lighting;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class LightMaster
{
	public static Light SUN = new Light(new Vector3f(100000, 200000, 100000), new Vector3f(1,1,1));

	public static List<Light> lights = new ArrayList<Light>();

	public LightMaster()
	{
		lights.add(SUN);
	}
}