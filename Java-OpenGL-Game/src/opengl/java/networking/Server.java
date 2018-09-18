package opengl.java.networking;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server
{
	private static ServerSocket socket;
	private static Socket s1;
	private static Scanner scanner;
	private static PrintStream stream;

	public static void main(String args[])
	{
		try
		{
			socket = new ServerSocket(1342);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		System.out.println("Server is ready to establish connection.");
		try
		{
			s1 = socket.accept();
			scanner = new Scanner(s1.getInputStream());
			stream = new PrintStream(s1.getOutputStream());
			float x = 0, y = 0, z = 0;
			while (true)
			{
				x = scanner.nextFloat();
				y = scanner.nextFloat();
				z = scanner.nextFloat();
				stream.print(x);
				stream.print(y);
				stream.print(z);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
