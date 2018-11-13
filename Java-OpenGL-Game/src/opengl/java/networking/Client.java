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

	private boolean running = true;
	private boolean hasUpdate;

	private PlayerPacket packetOut;

	private PlayerPacket newPacket;
	private PlayerPacket previousPacket;

	private long start, elapsed;

	public Client(PlayerPacket packet)
	{
		connectToServer("212.75.28.190", 1342);
		this.packetOut = packet;
		this.packetOut.setPort(socket.getLocalPort());
	}

	private void connectToServer(String address, int port)
	{
		try
		{
			socket = new Socket(address, port);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());

		}
		catch (UnknownHostException e)
		{
			System.out.println("Unknown host.");
		}
		catch (IOException e)
		{
			System.out.println("An error occured while trying to establish connection with the server.");
		}
	}

	@Override
	public void run()
	{
		start = System.currentTimeMillis() - 1;
		while (running)
		{
			sendObject(packetOut);
			Object obj = receiveObject();
			hasUpdate=true;
			elapsed = System.currentTimeMillis() - start;
			start = System.currentTimeMillis();
			process(obj);
		}
		closeConnection();
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

	public PlayerPacket getNewPacket()
	{
		return newPacket;
	}

	public PlayerPacket getPrevPacket()
	{
		return previousPacket;
	}
	
	public long getTime() {
		return elapsed;
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

	public void process(Object obj)
	{
		if (obj instanceof PlayerPacket)
		{
			processPlayerPacket((PlayerPacket) obj);
		}
	}

	private void processPlayerPacket(PlayerPacket packet)
	{
		PlayerPacket temp = newPacket.getCopy();
		newPacket = packet;
		previousPacket = temp;
	}
	
	public boolean hasUpdate() {
		return hasUpdate;
	}
	
	public void setHasUpdate(boolean b) {
		hasUpdate = b;
	}
}
