package opengl.java.networking;

import java.io.DataOutputStream;
import java.io.IOException;

import org.lwjgl.util.vector.Vector3f;

public class PacketSender
{
	private DataOutputStream output;

	public PacketSender(DataOutputStream output)
	{
		this.output = output;
	}

	public void sendPosition(Vector3f position)
	{
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
