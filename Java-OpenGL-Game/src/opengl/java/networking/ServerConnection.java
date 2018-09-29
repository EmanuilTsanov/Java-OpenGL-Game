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

	public void sendPosToOthers(float x, float y, float z,float xR, float yR, float zR)
	{
		sendOnlinePlayers();
		for (Map.Entry<Integer, ServerConnection> entry : server.getConnectionsList().entrySet())
		{
			if (entry.getKey() != id)
			{
				entry.getValue().sendPosition(x, y, z);
				entry.getValue().sendPosition(xR, yR, zR);
				try
				{
					output.flush();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				float xR = input.readFloat();
				float yR = input.readFloat();
				float zR = input.readFloat();
				sendPosToOthers(x, y, z, xR, yR, zR);
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
			output.writeFloat(x);
			output.writeFloat(y);
			output.writeFloat(z);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void sendOnlinePlayers()
	{
		try
		{
			output.writeLong(server.getConnectionsList().size());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
