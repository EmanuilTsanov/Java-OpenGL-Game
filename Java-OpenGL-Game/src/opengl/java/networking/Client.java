package opengl.java.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.rmi.UnknownHostException;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Player;

public class Client
{
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	private long start, elapsed;
	private long timeElapsed;

	private float x, y, z;
	private float tempX, tempY, tempZ;
	private float pastX, pastY, pastZ;

	public Client()
	{
		try
		{
			socket = new Socket("212.75.28.156", 1342);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());

		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
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
		try
		{
			output.writeFloat(x);
			output.writeFloat(y);
			output.writeFloat(z);
			output.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void read(Player player)
	{
		try
		{
			start = System.currentTimeMillis();
			tempX = x;
			tempY = y;
			tempZ = z;
			x = input.readFloat();
			y = input.readFloat();
			z = input.readFloat();
			pastX = tempX;
			pastY = tempY;
			pastZ = tempZ;
			System.out.println(x + " / " + pastX);
			elapsed = System.currentTimeMillis();
			timeElapsed = elapsed - start;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public long getOnlinePlayers()
	{
		long players = 0;
		try
		{
			players = input.readLong();
		}
		catch (IOException e)
		{

		}
		return players;
	}

	public Vector3f getPosition()
	{
		return new Vector3f(x, y, z);	}

	public Vector3f getPastPosition()
	{
		return new Vector3f(pastX, pastY, pastZ);
	}

	public long getElapsedTime()
	{
		return timeElapsed;
	}
}
