package opengl.java.fonts;

import java.util.ArrayList;

public class Word
{
	private ArrayList<Character> chars = new ArrayList<Character>();
	private int wordWidth;

	public void addCharacter(Character c)
	{
		chars.add(c);
		wordWidth += c.getXAdvance();
	}

	public ArrayList<Character> getChars()
	{
		return chars;
	}

	public int getWordWidth()
	{
		return wordWidth;
	}
}
