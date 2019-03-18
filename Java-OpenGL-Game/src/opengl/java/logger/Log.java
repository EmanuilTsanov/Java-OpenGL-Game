package opengl.java.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class Log
{
	private static File file = new File("log.txt");
	private static Calendar c = Calendar.getInstance();

	public static void println(String s)
	{
		if (!file.exists())
		{
			try (PrintWriter writer = new PrintWriter(new FileWriter(file, true)))
			{
				file.createNewFile();
				writer.println("# This file was created on " + getDate() + ", " + getTime() + "h.");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		try (PrintWriter writer = new PrintWriter(new FileWriter(file, true)))
		{
			writer.println("[" + getDate() + "] [" + getTime() + "] " + s);
			writer.flush();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String getTime()
	{
		StringBuilder builder = new StringBuilder();
		int h = c.get(Calendar.HOUR_OF_DAY);
		int m = c.get(Calendar.MINUTE);
		int s = c.get(Calendar.SECOND);
		String divider = ":";
		builder.append(h < 10 ? "0" + h : h).append(divider).append(m < 10 ? "0" + m : m).append(divider).append(s < 10 ? "0" + s : s);
		return builder.toString();
	}

	public static String getFormattedTime()
	{
		StringBuilder builder = new StringBuilder();
		int h = c.get(Calendar.HOUR_OF_DAY);
		int m = c.get(Calendar.MINUTE);
		int s = c.get(Calendar.SECOND);
		String divider = "_";
		builder.append(h < 10 ? "0" + h : h).append(divider).append(m < 10 ? "0" + m : m).append(divider).append(s < 10 ? "0" + s : s);
		return builder.toString();
	}

	public static String getDate()
	{
		StringBuilder builder = new StringBuilder();
		int d = c.get(Calendar.DAY_OF_MONTH);
		int m = c.get(Calendar.MONTH)+1;
		int y = c.get(Calendar.YEAR);
		String divider = ".";
		builder.append(d < 10 ? "0" + d : d).append(divider).append(m < 10 ? "0" + m : m).append(divider).append(y);
		return builder.toString();
	}
}
