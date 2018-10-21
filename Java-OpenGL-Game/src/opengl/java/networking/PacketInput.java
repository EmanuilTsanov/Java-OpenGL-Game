package opengl.java.networking;

import java.io.IOException;
import java.io.ObjectInputStream;

public class PacketInput
{
	private ObjectInputStream input;

	public PacketInput(ObjectInputStream input)
	{
		this.input = input;
	}

	public Object getObject()
	{
		Object obj = null;
		try
		{
			obj = input.readObject();
		}
		catch (ClassNotFoundException | IOException e)
		{
			e.printStackTrace();
		}
		return obj;
	}
}
