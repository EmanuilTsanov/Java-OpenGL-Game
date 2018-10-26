package opengl.java.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Player;
import opengl.java.packets.PlayerPacket;
import opengl.java.window.FPSCounter;

public class Client extends Thread
{
	private Socket socket;

	private ObjectInputStream input;
	private ObjectOutputStream output;

	private PlayerPacket pPacket;

	private long start, elapsed;

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
		elapsed = System.currentTimeMillis() - start;
		start = System.currentTimeMillis();
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

	public synchronized void movePlayer(Player player)
	{
		if (hasUpdate)
		{
			if (p2Packet != null)
			{
				player.setPosition(p2Packet.getPosition());
				player.setRotation(p2Packet.getRotation());
				hasUpdate = false;
			}
		}
		float a = FPSCounter.getFPS() / (1000 / elapsed);
		Vector3f b = new Vector3f(p2Packet.getPosition().getX() - p2PrevPacket.getPosition().getX(),
				p2Packet.getPosition().getY() - p2PrevPacket.getPosition().getY(),
				p2Packet.getPosition().getZ() - p2PrevPacket.getPosition().getZ());
		player.move(b.x/a, b.y/a, b.z/a);
	}
}
