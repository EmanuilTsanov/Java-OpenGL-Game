package opengl.java.networking;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Player;

public class Client
{
	private Socket socket;
	private Scanner scanner;
	private PrintStream stream;

	public Client()
	{
		try
		{
			socket = new Socket("192.168.1.149", 1342);
			scanner = new Scanner(socket.getInputStream());
			stream = new PrintStream(socket.getOutputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void update(Player player)
	{
		Vector3f pos = player.getPosition();
		float x = pos.x;
		float y = pos.y;
		float z = pos.z;
		stream.println(x);
		stream.println(y);
		stream.println(z);
	}

	public void read(Player player)
	{
		float x = scanner.nextFloat();
		float y = scanner.nextFloat();
		float z = scanner.nextFloat();
		Vector3f position = new Vector3f(x, y, z);
		player.setPosition(position);
	}
}
