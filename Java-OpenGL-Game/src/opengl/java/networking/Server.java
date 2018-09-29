package opengl.java.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server
{
	private ServerSocket sSocket;
	private HashMap<Integer, ServerConnection> connections = new HashMap<Integer, ServerConnection>();
	private boolean running = true;
	private static int nextID = 0;

	public static void main(String args[])
	{
		new Server();
	}

	public Server()
	{
		try
		{
			sSocket = new ServerSocket(1342);
			System.out.println("Server is ready to establish connection.");
			while (running)
			{
				Socket socket = sSocket.accept();
				ServerConnection sConnection = new ServerConnection(nextID, socket, this);
				sConnection.start();
				connections.put(nextID, sConnection);
				System.out.println("A new user has connected! Players online: " + connections.size());
				nextID++;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public HashMap<Integer, ServerConnection> getConnectionsList()
	{
		return connections;
	}
}
