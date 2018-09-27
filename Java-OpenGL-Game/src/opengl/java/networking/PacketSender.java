package opengl.java.networking;

import java.io.DataOutputStream;
import java.io.IOException;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Player;

public class PacketSender
{
	private DataOutputStream output;

	public PacketSender(DataOutputStream output)
	{
		this.output = output;
	}

	public void sendPlayerLocation(Player player)
	{
		Vector3f position = player.getPosition();
		try
		{
			output.writeFloat(position.getX());
			output.writeFloat(position.getY());
			output.writeFloat(position.getZ());
			output.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
