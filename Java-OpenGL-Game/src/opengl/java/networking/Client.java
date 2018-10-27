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

	private long start = System.currentTimeMillis(), elapsed = 1;

	private PlayerPacket p2PrevPacket;
	private PlayerPacket p2Packet;

	private boolean hasUpdate;
	private Vector3f b;

	private boolean running = true;

	public Client(PlayerPacket packet)
	{
		this.pPacket = packet;
		try
		{
			socket = new Socket("212.75.28.190", 1342);
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
		PlayerPacket temp = p2Packet;
		p2Packet = (PlayerPacket) receiveObject();
		p2PrevPacket = temp;
		elapsed = System.currentTimeMillis() - start + 1;
		start = System.currentTimeMillis();
		hasUpdate = true;
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
		if (p2PrevPacket != null)
			if (hasUpdate)
			{
				player.setPosition(p2PrevPacket.getPosition());
				player.setRotation(p2PrevPacket.getRotation());
				hasUpdate = false;
				b = new Vector3f(p2Packet.getPosition().getX() - p2PrevPacket.getPosition().getX(),
						p2Packet.getPosition().getY() - p2PrevPacket.getPosition().getY(),
						p2Packet.getPosition().getZ() - p2PrevPacket.getPosition().getZ());
			}
		float a = FPSCounter.getFPS() / (1000 / elapsed);
		if (b != null)
			player.move(b.x / a, b.y / a, b.z / a);
	}
}
