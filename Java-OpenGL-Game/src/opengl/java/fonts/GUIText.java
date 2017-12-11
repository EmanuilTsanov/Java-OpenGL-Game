package opengl.java.fonts;

import java.util.ArrayList;
import java.util.HashMap;

import opengl.java.calculations.Maths;
import opengl.java.loader.ModelLoader;
import opengl.java.model.RawModel;

public class GUIText
{
	private int x, y;

	private RawModel txtModel;

	private int imgID;

	public GUIText(int x, int y, String text, FontType fontType, float fontSize, int maxLineWidth)
	{
		txtModel = translateText(text, maxLineWidth, fontType, fontSize);
		imgID = fontType.getImg().getID();
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
				line = new Line(maxLineWidth);
				line.addWord(word);
			}
			else if (result == Line.NOT_ADDED_TOO_LONG)
			{
				System.out.println(
						"A word in the specified text is too long to be displayed within the maximum line length. The text will not be displayed.");
				break;
			}
		}
		linesArr.add(line);
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
					vertices.add(Maths.getScreenValue(x + lineXAdvance + ch.getXOffset(), 0));
					vertices.add(Maths.getScreenValue(y + lineHeight + ch.getYOffset(), 1));
					vertices.add(Maths.getScreenValue(x + lineXAdvance + ch.getXOffset(), 0));
					vertices.add(Maths.getScreenValue(y + lineHeight + ch.getScrHeight() + ch.getYOffset(), 1));
					vertices.add(Maths.getScreenValue(x + lineXAdvance + ch.getScrWidth() + ch.getXOffset(), 0));
					vertices.add(Maths.getScreenValue(y + lineHeight + ch.getYOffset(), 1));
					vertices.add(Maths.getScreenValue(x + lineXAdvance + ch.getScrWidth() + ch.getXOffset(), 0));
					vertices.add(Maths.getScreenValue(y + lineHeight + ch.getYOffset(), 1));
					vertices.add(Maths.getScreenValue(x + lineXAdvance + ch.getXOffset(), 0));
					vertices.add(Maths.getScreenValue(y + lineHeight + ch.getScrHeight() + ch.getYOffset(), 1));
					vertices.add(Maths.getScreenValue(x + lineXAdvance + ch.getScrWidth() + ch.getXOffset(), 0));
					vertices.add(Maths.getScreenValue(y + lineHeight + ch.getScrHeight() + ch.getYOffset(), 1));
					textureCoords.add(Maths.getImageValue((float)ch.getX()+1, textureSize));
					textureCoords.add(Maths.getImageValue((float)ch.getY(), textureSize));
					textureCoords.add(Maths.getImageValue((float)ch.getX()+1, textureSize));
					textureCoords.add(Maths.getImageValue((float)ch.getY() + (float)ch.getHeight()+1, textureSize));
					textureCoords.add(Maths.getImageValue((float)ch.getX() + (float)ch.getWidth()-1, textureSize));
					textureCoords.add(Maths.getImageValue((float)ch.getY(), textureSize));
					textureCoords.add(Maths.getImageValue((float)ch.getX() + (float)ch.getWidth()-1, textureSize));
					textureCoords.add(Maths.getImageValue((float)ch.getY(), textureSize));
					textureCoords.add(Maths.getImageValue((float)ch.getX()+1, textureSize));
					textureCoords.add(Maths.getImageValue((float)ch.getY() + (float)ch.getHeight()+1, textureSize));
					textureCoords.add(Maths.getImageValue((float)ch.getX() + (float)ch.getWidth()-1, textureSize));
					textureCoords.add(Maths.getImageValue((float)ch.getY() + (float)ch.getHeight()+1, textureSize));
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

		ModelLoader loader = new ModelLoader();
		return loader.loadFonts(verticesArr, textureArr);
	}

	public HashMap<Integer, Character> scaleFonts(ArrayList<Character> chars, float fontSize)
	{
		HashMap<Integer, Character> scaledChars = new HashMap<Integer, Character>();
		for (int c = 0; c < chars.size(); c++)
		{
			scaledChars.put(chars.get(c).getID(), chars.get(c).setFontSize(fontSize));
		}
		return scaledChars;
	}

	public RawModel getModel()
	{
		return txtModel;
	}

	public int getTextureID()
	{
		return imgID;
	}
}