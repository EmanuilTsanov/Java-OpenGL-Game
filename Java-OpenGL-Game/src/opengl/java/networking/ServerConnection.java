package opengl.java.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class ServerConnection extends Thread
{
	private int id;

	private Socket socket;
	private Server server;
	private DataInputStream input;
	private DataOutputStream output;

	private boolean running = true;

	public ServerConnection(int id, Socket socket, Server server)
	{
		super("ConnectionThread");
		this.id = id;
		this.socket = socket;
		this.server = server;
		try
		{
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void sendPosToOthers(float x, float y, float z)
	{
		for (Map.Entry<Integer, ServerConnection> entry : server.getConnectionsList().entrySet())
		{
			if (entry.getKey() != id)
			{
				entry.getValue().sendPosition(x, y, z);
			}
		}
	}

	@Override
	public void run()
	{
		try
		{
			while (running)
			{
				float x = input.readFloat();
				float y = input.readFloat();
				float z = input.readFloat();
				sendPosToOthers(x, y, z);
			}
			input.close();
			output.close();
			socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void sendPosition(float x, float y, float z)
	{
		try
		{
			output.writeLong(server.getConnectionsList().size());
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
}
