package opengl.java.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class Logger
{
	private static File file = new File("log.txt");
	private static PrintWriter writer;
	private static Calendar c = Calendar.getInstance();

	public static void log(String s)
	{
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		if (writer == null)
			try
			{
				writer = new PrintWriter(new FileWriter(file, true));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		writer.println(getDate() + getTime() + " " + s);
		writer.flush();
	}

	private static String getTime()
	{
		StringBuilder builder = new StringBuilder();
		int h = c.get(Calendar.HOUR_OF_DAY);
		int m = c.get(Calendar.MINUTE);
		int s = c.get(Calendar.SECOND);
		String divider = ":";
		builder.append("[").append(h < 10 ? 0 + "" + h : h).append(divider).append(m < 10 ? 0 + "" + m : m).append(divider).append(s < 10 ? 0 + "" + s : s).append("]");
		return builder.toString();
	}

	private static String getDate()
	{
		StringBuilder builder = new StringBuilder();
		int d = c.get(Calendar.DAY_OF_MONTH);
		int m = c.get(Calendar.MONTH);
		int y = c.get(Calendar.YEAR);
		String divider = ".";
		builder.append("[").append(d < 10 ? 0 + "" + d : d).append(divider).append(m < 10 ? 0 + "" + m : m).append(divider).append(y).append("]");
		return builder.toString();
	}
}
