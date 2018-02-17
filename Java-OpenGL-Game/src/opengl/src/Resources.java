package opengl.src;

import java.util.HashMap;

import opengl.java.model.Model;
import opengl.java.texture.ModelTexture;

public class Resources
{
	private static HashMap<Integer, Model> models = new HashMap<Integer, Model>();
	private static HashMap<Integer, ModelTexture> textures = new HashMap<Integer, ModelTexture>();

	public static void addModel(int key, Model model)
	{
		models.put(key, model);
	}

	public static void addTexture(int key, ModelTexture texture)
	{
		textures.put(key, texture);
	}
	
	public static Model getModel(int key) {
		return models.get(key);
	}
	
	public static ModelTexture getTexture(int key) {
		return textures.get(key);
	}
}
