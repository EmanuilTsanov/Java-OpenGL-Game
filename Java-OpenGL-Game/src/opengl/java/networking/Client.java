package opengl.java.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.rmi.UnknownHostException;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Player;

public class Client extends Thread
{
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;

	private PacketSender sender;
	private PacketReceiver receiver;

	private boolean hasUpdate;

	private long onlinePlayers;
	private Vector3f myPosition = new Vector3f(0, 0, 0);

	private long start, timeBetween = 1;

	private Vector3f previousFrame = new Vector3f(0, 0, 0);
	private Vector3f currentFrame = new Vector3f(0, 0, 0);
	private Vector3f bufferFrame = new Vector3f(0, 0, 0);

	private boolean running = true;

	public Client()
	{
		try
		{
			socket = new Socket("localhost", 1342);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());

		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		sender = new PacketSender(output);
		receiver = new PacketReceiver(input);
	}

	public void update(Player player)
	{
		this.myPosition = player.getPosition();
	}

	@Override
	public void run()
	{
		while (running)
		{
			sender.sendPosition(myPosition);
			onlinePlayers = receiver.getOnlinePlayers();
			bufferFrame = new Vector3f(currentFrame);
			currentFrame = new Vector3f(receiver.getPlayerPosition());
			previousFrame = new Vector3f(bufferFrame);
			timeBetween = System.currentTimeMillis() - start+1;
			start = System.currentTimeMillis();
			hasUpdate = true;
		}
	}

	public boolean hasUpdate()
	{
		return hasUpdate;
	}

	public void setUpdateState(boolean state)
	{
		hasUpdate = state;
	}

	public long getOnlinePlayers()
	{
		return onlinePlayers;
	}

	public long getTimeBetweenUpdates()
	{
		return timeBetween;
	}

	public Vector3f getCurrentFrame()
	{
		return currentFrame;
	}

	public Vector3f getPreviousFrame()
	{
		return previousFrame;
	}
}
