package opengl.java.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;

import opengl.java.packets.PlayerPacket;

public class Client extends Thread
{
	private Socket socket;

	private ObjectInputStream input;
	private ObjectOutputStream output;

	private PlayerPacket pPacket;

	private PlayerPacket p2PrevPacket;
	private PlayerPacket p2Packet;

	private boolean hasUpdate;

	private boolean running = true;

	public Client(PlayerPacket packet)
	{
		this.pPacket = packet;
		try
		{
			socket = new Socket("localhost", 1342);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());

		}
		catch (UnknownHostException e)
		{
			System.out.println("Unknown host.");
		}
		catch (IOException e)
		{
			System.out.println("An error occured while trying to establish a connection with the server.");
		}
	}

	@Override
	public void run()
	{
		while (running)
		{
			sendObject(pPacket.getCopy());
			handleIncomingData();
		}
		closeConnection();
	}

	public void handleIncomingData()
	{
		PlayerPacket temp = null;
		if (p2PrevPacket != null)
		{
			temp = p2PrevPacket;
			hasUpdate = true;
		}
		p2PrevPacket = (PlayerPacket) receiveObject();
		p2Packet = temp;
	}

	public void closeConnection()
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

	public void sendObject(Object obj)
	{
		try
		{
			output.writeObject(obj);
			output.reset();
			output.flush();
		}
		catch (IOException e)
		{
			System.out.println("An error occured while sending data to the server.");
		}
	}

	public Object receiveObject()
	{
		Object obj = null;
		try
		{
			obj = input.readObject();
		}
		catch (ClassNotFoundException | IOException e)
		{
			System.out.println("An error occured while receiving data from the server.");
		}
		return obj;
	}

	public boolean hasUpdate()
	{
		return hasUpdate;
	}

	public void setHasUpdate(boolean b)
	{
		hasUpdate = b;
	}

	public PlayerPacket getPlayerPacket()
	{
		return p2Packet;
	}
}
