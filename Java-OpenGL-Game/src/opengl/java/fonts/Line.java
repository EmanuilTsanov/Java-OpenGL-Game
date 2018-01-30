package opengl.java.fonts;

import java.util.ArrayList;

public class Line
{
	private ArrayList<Word> words = new ArrayList<Word>();
	private int maxLineWidth, lineWidth;

	public static final int NOT_ADDED = 0;
	public static final int ADDED = 1;
	public static final int NOT_ADDED_TOO_LONG = 2;

	public Line(int maxLineWidth)
	{
		this.maxLineWidth = maxLineWidth;
	}

	public int addWord(Word w)
	{
		if (lineWidth + w.getWordWidth() <= maxLineWidth)
		{
			lineWidth += w.getWordWidth();
			words.add(w);
			return ADDED;
		}
		if (w.getWordWidth() > maxLineWidth)
			return NOT_ADDED_TOO_LONG;
		return NOT_ADDED;
	}

	public ArrayList<Word> getWords()
	{
		return words;
	}
	
	public int getLineWidth() {
		return lineWidth;
	}
}
