package opengl.java.lighting;

import org.lwjgl.util.vector.Vector3f;

public class LightManager
{
	private Light sun = new Light(new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(1.0f, 1.0f, 1.0f));
	
	private static LightManager singleton = new LightManager();
	
	public static LightManager getInstance() {
		return singleton;
	}
	
	public Light getSun() {
		return sun;
	}
}
