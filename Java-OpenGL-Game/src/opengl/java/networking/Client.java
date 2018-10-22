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

	private PacketInput input;
	private PacketOutput output;

	private ObjectInputStream inStream;
	private ObjectOutputStream outStream;

	private PlayerPacket myPacket;

	private PlayerPacket p2PrevPacket;
	private PlayerPacket p2Packet;

	private boolean hasUpdate;

	private long start, elapsed;

	private boolean running = true;

	public Client(PlayerPacket packet)
	{
		this.myPacket = packet;
		try
		{
			socket = new Socket("localhost", 1342);
			outStream = new ObjectOutputStream(socket.getOutputStream());
			inStream = new ObjectInputStream(socket.getInputStream());

		}
		catch (UnknownHostException e)
		{
			System.out.println("Unknown host.");
		}
		catch (IOException e)
		{
			System.out.println("An error occured while trying to establish a connection with the server.");
		}
		input = new PacketInput(inStream);
		output = new PacketOutput(outStream);
	}

	@Override
	public void run()
	{
		while (running)
		{
			output.sendPacket(myPacket);
			start = System.currentTimeMillis();
			handleIncoming();
			elapsed = System.currentTimeMillis() - start;
		}
		closeConnection();
	}

	public void handleIncoming()
	{
		Object o = input.getObject();
		if (o instanceof PlayerPacket)
		{
			PlayerPacket temp = null;
			if (p2PrevPacket != null)
				temp = p2PrevPacket.getCopy();
			p2PrevPacket = (PlayerPacket) o;
			if (temp != null)
				p2Packet = temp.getCopy();
			hasUpdate = true;
		}
	}

	public void closeConnection()
	{
		try
		{
			inStream.close();
			outStream.close();
			socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public boolean hasUpdate()
	{
		return hasUpdate;
	}

	public void setHasUpdate(boolean b)
	{
		hasUpdate = b;
	}
	
	public PlayerPacket getPacket() {
		return p2PrevPacket;
	}
}
