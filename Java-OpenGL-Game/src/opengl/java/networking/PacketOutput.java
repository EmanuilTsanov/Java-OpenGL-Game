package opengl.java.networking;

import java.io.IOException;
import java.io.ObjectOutputStream;

import opengl.java.packets.Packet;

public class PacketOutput
{
	private ObjectOutputStream output;

	public PacketOutput(ObjectOutputStream output)
	{
		this.output = output;
	}

	public void sendPacket(Packet packet)
	{
		try
		{
			output.writeObject(packet);
			output.flush();
		}
		catch (IOException e)
		{
			System.out.println("An error occured while trying to send packet to server.");
		}
	}
}
