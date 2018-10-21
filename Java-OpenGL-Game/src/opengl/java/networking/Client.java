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
	
	private long start, elapsed;

	private boolean running = true;

	public Client(PlayerPacket packet)
	{
		this.myPacket = packet;
		try
		{
			socket = new Socket("localhost", 1342);
			inStream = new ObjectInputStream(socket.getInputStream());
			outStream = new ObjectOutputStream(socket.getOutputStream());

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
			Object o = input.getObject();
			elapsed = System.currentTimeMillis()-start;
			PlayerPacket temp = p2PrevPacket.getCopy();
			if (o instanceof PlayerPacket)
				p2PrevPacket = (PlayerPacket) o;
			p2Packet = temp.getCopy();
		}
		closeConnection();
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
}
