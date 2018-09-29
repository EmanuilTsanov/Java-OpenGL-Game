package opengl.java.networking;

import java.io.DataInputStream;
import java.io.IOException;

import org.lwjgl.util.vector.Vector3f;

public class PacketReceiver
{
	private DataInputStream input;

	public PacketReceiver(DataInputStream input)
	{
		this.input = input;
	}

	public Vector3f getPlayerPosition()
	{
		Vector3f position = null;

		try
		{
			float x = input.readFloat();
			float y = input.readFloat();
			float z = input.readFloat();
			position = new Vector3f(x, y, z);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return position;
	}

	public Vector3f getPlayerRotation()
	{
		Vector3f rotation = null;

		try
		{
			float x = input.readFloat();
			float y = input.readFloat();
			float z = input.readFloat();
			rotation = new Vector3f(x, y, z);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return rotation;
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
			e.printStackTrace();
		}
		return players;
	}
}
