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

	public Client()
	{
		try
		{
			socket = new Socket("localhost", 1342);
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
			float x = input.readFloat();
			float y = input.readFloat();
			float z = input.readFloat();
			Vector3f position = new Vector3f(x, y, z);
			player.setPosition(position);
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
}
