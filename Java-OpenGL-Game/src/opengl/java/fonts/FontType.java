package opengl.java.fonts;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;

import opengl.java.texture.ModelTexture;

public class FontType
{
	private ArrayList<Character> chars;
	private int lineHeight;
	private int imgSize;
	private ModelTexture img;
	private static String defaultFolder = "assets/textures/";
	private static String defaultExtension = ".png";

	public FontType(FontReader fReader, String fontName)
	{
		chars = fReader.readFontsFile(fontName);
		lineHeight = fReader.getLineHeight();
		imgSize = fReader.getImgSizeX();
		img = loadTexture(fReader.getImgName());
	}

	public ArrayList<Character> getChars()
	{
		return chars;
	}

	public int getLineHeight()
	{
		return lineHeight;
	}

	public ModelTexture getImg()
	{
		return img;
	}

	public int getImageSize()
	{
		return imgSize;
	}

	public static ModelTexture loadTexture(String file)
	{
		Texture tex = null;
		try
		{
			tex = org.newdawn.slick.opengl.TextureLoader.getTexture("png", new FileInputStream(defaultFolder + file + defaultExtension));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_MAX_TEXTURE_LOD_BIAS, 0);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return new ModelTexture(tex.getTextureID());
	}
}
