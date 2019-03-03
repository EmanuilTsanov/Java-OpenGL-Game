package opengl.java.lighting;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class LightMaster
{
	public static Light SUN = new Light(new Vector3f(1000000, 1500000, -1000000), new Vector3f(1, 1, 1));
	public static Light l1 = new Light(new Vector3f(250, 10, 250), new Vector3f(10, 0f, 0f));
	public static Light l2 = new Light(new Vector3f(200, 10, 200), new Vector3f(0f, 0f, 10f));

	public static List<Light> lights = new ArrayList<Light>();

	public LightMaster()
	{
		lights.add(SUN);
		lights.add(l1);
		lights.add(l2);
	}
}