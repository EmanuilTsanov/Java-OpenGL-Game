package opengl.java.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

public class ServerConnection extends Thread
{
	private Socket socket;
	private Server server;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	private Object packet;

	private boolean running = true;

	public ServerConnection(int id, Socket socket, Server server)
	{
		super("ConnectionThread");
		this.socket = socket;
		this.server = server;
		try
		{
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void sendObjectToOthers(Object obj)
	{
		for (Map.Entry<Integer, ServerConnection> entry : server.getConnectionsList().entrySet())
		{
			if (entry.getKey() != socket.getPort())
			{
				entry.getValue().sendObject(obj);
			}
		}
	}

	@Override
	public void run()
	{
		while (running)
		{
			packet = readObject();
			sendObjectToOthers(packet);
		}
		closeConnnection();
	}

	public void closeConnnection()
	{
		try
		{
			output.close();
			input.close();
			socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public Object readObject()
	{
		Object obj = null;
		try
		{
			obj = input.readObject();
		}
		catch (SocketException e)
		{
			server.getConnectionsList().remove(socket.getPort());
			running = false;
			System.out.println(
					"A player has left the game. " + "Players online: " + server.getConnectionsList().size() + ".");
		}
		catch (ClassNotFoundException | IOException e)
		{
			e.printStackTrace();
		}
		return obj;
	}

	public void sendObject(Object obj)
	{
		try
		{
			output.writeObject(obj);
			output.reset();
			output.flush();
		}
		catch (SocketException e)
		{
			running = false;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
