package opengl.java.fonts;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;

import opengl.java.loader.ModelLoader;
import opengl.java.maths.Maths;
import opengl.java.model.RawModel;

public class GUIText
{
	private int x, y;

	private RawModel txtModel;

	private int imgID;

	private int maxLineWidth;
	private FontType fontType;
	private float fontSize;

	private Vector2f textDimensions = new Vector2f();

	public GUIText(int x, int y, String text, FontType fontType, float fontSize, int maxLineWidth)
	{
		this.x = x;
		this.y = y;
		this.fontType = fontType;
		this.fontSize = fontSize;
		this.maxLineWidth = maxLineWidth;
		txtModel = translateText(text, maxLineWidth, fontType, fontSize);
		imgID = fontType.getImg().getID();
	}

	public void update(String t)
	{
		txtModel = translateText(t, maxLineWidth, fontType, fontSize);
	}

	public RawModel translateText(String text, int maxLineWidth, FontType fontType, float fontSize)
	{
		String[] tokens = text.split("\\s+");
		HashMap<Integer, Character> chars = scaleFonts(fontType.getChars(), fontSize);
		ArrayList<Line> linesArr = new ArrayList<Line>();
		Line line = new Line(maxLineWidth);
		for (int t = 0; t < tokens.length; t++)
		{
			Word word = new Word();
			char[] charArray = tokens[t].toCharArray();
			for (int c = 0; c < charArray.length; c++)
			{
				word.addCharacter(chars.get((int) charArray[c]));
			}
			int result = line.addWord(word);
			if (result == Line.NOT_ADDED)
			{
				linesArr.add(line);
				if (textDimensions.x < line.getLineWidth())
					textDimensions.x = line.getLineWidth();
				line = new Line(maxLineWidth);
				line.addWord(word);
			}
			else if (result == Line.NOT_ADDED_TOO_LONG)
			{
				System.out.println("A word in the specified text is too long to be displayed within the maximum line length. The text will not be displayed.");
				break;
			}
		}
		linesArr.add(line);
		if (textDimensions.x < line.getLineWidth())
			textDimensions.x = line.getLineWidth();
		textDimensions.y = fontType.getLineHeight() * fontSize * linesArr.size();
		return loadTextMesh(linesArr, fontType, fontSize);
	}

	public RawModel loadTextMesh(ArrayList<Line> lines, FontType ft, float fontSize)
	{
		int lineXAdvance = 0;
		ArrayList<Float> vertices = new ArrayList<Float>();
		ArrayList<Float> textureCoords = new ArrayList<Float>();
		int textureSize = ft.getImageSize();
		float space_advance = ft.getChars().get(32).getXAdvance() * fontSize;
		for (int l = 0; l < lines.size(); l++)
		{
			ArrayList<Word> words = lines.get(l).getWords();
			int lineHeight;
			for (int w = 0; w < words.size(); w++)
			{
				Word word = words.get(w);
				lineHeight = (int) (ft.getLineHeight() * fontSize) * l;
				ArrayList<Character> chars = word.getChars();
				for (int c = 0; c < chars.size(); c++)
				{
					Character ch = chars.get(c);
					vertices.add(Maths.toOpenGLWidth(x + lineXAdvance + ch.getXOffset()));
					vertices.add(Maths.toOpenGLHeight(y + lineHeight + ch.getYOffset()));
					vertices.add(Maths.toOpenGLWidth(x + lineXAdvance + ch.getXOffset()));
					vertices.add(Maths.toOpenGLHeight(y + lineHeight + ch.getScrHeight() + ch.getYOffset()));
					vertices.add(Maths.toOpenGLWidth(x + lineXAdvance + ch.getScrWidth() + ch.getXOffset()));
					vertices.add(Maths.toOpenGLHeight(y + lineHeight + ch.getYOffset()));
					vertices.add(Maths.toOpenGLWidth(x + lineXAdvance + ch.getScrWidth() + ch.getXOffset()));
					vertices.add(Maths.toOpenGLHeight(y + lineHeight + ch.getYOffset()));
					vertices.add(Maths.toOpenGLWidth(x + lineXAdvance + ch.getXOffset()));
					vertices.add(Maths.toOpenGLHeight(y + lineHeight + ch.getScrHeight() + ch.getYOffset()));
					vertices.add(Maths.toOpenGLWidth(x + lineXAdvance + ch.getScrWidth() + ch.getXOffset()));
					vertices.add(Maths.toOpenGLHeight(y + lineHeight + ch.getScrHeight() + ch.getYOffset()));
					textureCoords.add(Maths.getImageValue((float) ch.getX() + 1, textureSize));
					textureCoords.add(Maths.getImageValue((float) ch.getY(), textureSize));
					textureCoords.add(Maths.getImageValue((float) ch.getX() + 1, textureSize));
					textureCoords.add(Maths.getImageValue((float) ch.getY() + (float) ch.getHeight() + 1, textureSize));
					textureCoords.add(Maths.getImageValue((float) ch.getX() + (float) ch.getWidth() - 1, textureSize));
					textureCoords.add(Maths.getImageValue((float) ch.getY(), textureSize));
					textureCoords.add(Maths.getImageValue((float) ch.getX() + (float) ch.getWidth() - 1, textureSize));
					textureCoords.add(Maths.getImageValue((float) ch.getY(), textureSize));
					textureCoords.add(Maths.getImageValue((float) ch.getX() + 1, textureSize));
					textureCoords.add(Maths.getImageValue((float) ch.getY() + (float) ch.getHeight() + 1, textureSize));
					textureCoords.add(Maths.getImageValue((float) ch.getX() + (float) ch.getWidth() - 1, textureSize));
					textureCoords.add(Maths.getImageValue((float) ch.getY() + (float) ch.getHeight() + 1, textureSize));
					lineXAdvance += ch.getXAdvance();
				}
				lineXAdvance += space_advance;
			}
			lineXAdvance = 0;
		}
		float[] verticesArr = new float[vertices.size()];
		float[] textureArr = new float[textureCoords.size()];

		for (int i = 0; i < vertices.size(); i++)
		{
			verticesArr[i] = vertices.get(i);
		}

		for (int i = 0; i < textureCoords.size(); i++)
		{
			textureArr[i] = textureCoords.get(i);
		}

		return ModelLoader.getInstance().loadFonts(verticesArr, textureArr);
	}

	public HashMap<Integer, Character> scaleFonts(ArrayList<Character> chars, float fontSize)
	{
		HashMap<Integer, Character> scaledChars = new HashMap<Integer, Character>();
		for (int c = 0; c < chars.size(); c++)
		{
			scaledChars.put(chars.get(c).getID(), chars.get(c).getCopy().setFontSize(fontSize));
		}
		return scaledChars;
	}

	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public RawModel getModel()
	{
		return txtModel;
	}

	public int getTextureID()
	{
		return imgID;
	}

	public Vector2f getTextDimensions()
	{
		return textDimensions;
	}
}
