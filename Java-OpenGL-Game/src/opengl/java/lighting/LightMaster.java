package opengl.java.lighting;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

public class LightMaster
{
	public static Light SUN = new Light(new Vector3f(0, 10000, -7000), new Vector3f(1, 1, 1));
	public static Light l1 = new Light(new Vector3f(-200, 10, -200), new Vector3f(10, 0, 0));
	public static Light l2 = new Light(new Vector3f(200, 10, 200), new Vector3f(0, 0, 10));

	public static List<Light> lights = new ArrayList<Light>();

	public LightMaster()
	{
		lights.add(SUN);
		lights.add(l1);
		lights.add(l2);
	}
}