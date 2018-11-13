package opengl.java.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server
{
	private ServerSocket serverSocket;

	private boolean running = true;

	private HashMap<Integer, ServerConnection> connections = new HashMap<Integer, ServerConnection>();

	public static void main(String args[])
	{
		new Server(1342);
	}

	public Server(int port)
	{
		try
		{
			serverSocket = new ServerSocket(port);
			System.out.println("Server is ready to establish connection.");
			while (running)
			{
				Socket socket = serverSocket.accept();
				ServerConnection sConnection = new ServerConnection(socket.getPort(), socket, this);
				sConnection.start();
				connections.put(socket.getPort(), sConnection);
				System.out.println("A player has joined the game. Players online: " + connections.size() + ".");
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
