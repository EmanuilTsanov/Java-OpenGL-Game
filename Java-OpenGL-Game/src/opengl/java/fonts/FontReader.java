package opengl.java.fonts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class FontReader
{
	private int lineHeight;
	private int imgSizeX, imgSizeY;
	private String imgName;

	private final String FONT_FOLDER = "assets/fonts/";
	private final String FONT_EXTENSION = ".fnt";

	public ArrayList<Character> readFontsFile(String fileName)
	{
		ArrayList<Character> chars = new ArrayList<Character>();
		try
		{
			String line;
			BufferedReader stream = new BufferedReader(
					new FileReader(new File(FONT_FOLDER + fileName + FONT_EXTENSION)));
			while ((line = stream.readLine()) != null)
			{
				if (line.startsWith("info"))
				{

				}
				else if (line.startsWith("common"))
				{
					String[] tokens = line.split("\\s+");
					lineHeight = Integer.parseInt(tokens[1].split("=")[1]);
					imgSizeX = Integer.parseInt(tokens[3].split("=")[1]);
					imgSizeY = Integer.parseInt(tokens[4].split("=")[1]);
				}
				else if (line.startsWith("page"))
				{
					String[] tokens = line.split("\\s+");
					imgName = tokens[2].split("=")[1].replace("\"", "").split("\\.")[0];
				}
				else if (line.startsWith("chars"))
				{
					break;
				}
			}
			while ((line = stream.readLine()) != null)
			{
				if (!line.startsWith("char"))
				{
					continue;
				}
				String[] tokens = line.split("\\s+");
				Character c = new Character(Integer.parseInt(tokens[1].split("=")[1]),
						Integer.parseInt(tokens[2].split("=")[1]), Integer.parseInt(tokens[3].split("=")[1]),
						Integer.parseInt(tokens[4].split("=")[1]), Integer.parseInt(tokens[5].split("=")[1]),
						Float.parseFloat(tokens[6].split("=")[1]), Float.parseFloat(tokens[7].split("=")[1]),
						Integer.parseInt(tokens[8].split("=")[1]));
				chars.add(c);
			}
			stream.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("The specified file was not found.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return chars;
	}

	public int getLineHeight()
	{
		return lineHeight;
	}

	public int getImgSizeX()
	{
		return imgSizeX;
	}

	public int getImgSizeY()
	{
		return imgSizeY;
	}

	public String getImgName()
	{
		return imgName;
	}
}
