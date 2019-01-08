package opengl.java.gui;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.model.RawModel;
import opengl.java.shader.GUIShader;
import opengl.java.texture.ModelTexture;

public abstract class GUIComponent
{
	protected int x, y;
	protected int width, height;
	protected RawModel model;
	protected ModelTexture texture;
	protected Vector3f color = new Vector3f(0, 0, 0);

	public GUIComponent(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		model = Maths.createPlane(x, y, width, height);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setColor(float r, float g, float b) {
		color = new Vector3f(r/255f, g/255f, b/255f);
	}

	public abstract void update();

	public abstract void render(GUIShader shader);
}
