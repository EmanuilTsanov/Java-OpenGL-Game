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
	private Vector3f myRotation = new Vector3f(0, 0, 0);

	private long start, timeBetween = 1;

	private Vector3f previousFrame = new Vector3f(0, 0, 0);
	private Vector3f currentFrame = new Vector3f(0, 0, 0);
	private Vector3f bufferFrame = new Vector3f(0, 0, 0);

	private Vector3f prevRotFrame = new Vector3f(0, 0, 0);
	private Vector3f currentRotFrame = new Vector3f(0, 0, 0);
	private Vector3f bufferRotFrame = new Vector3f(0, 0, 0);

	private boolean running = true;

	public Client()
	{
		try
		{
			socket = new Socket("212.75.28.156", 1342);
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
		this.myRotation = player.getRotation();
	}

	@Override
	public void run()
	{
		while (running)
		{
			sender.sendPosition(myPosition);
			sender.sendPosition(myRotation);
			onlinePlayers = receiver.getOnlinePlayers();
			bufferFrame = new Vector3f(currentFrame);
			currentFrame = new Vector3f(receiver.getPlayerPosition());
			previousFrame = new Vector3f(bufferFrame);
			bufferRotFrame = new Vector3f(currentRotFrame);
			currentRotFrame = new Vector3f(receiver.getPlayerRotation());
			prevRotFrame = new Vector3f(bufferRotFrame);
			timeBetween = System.currentTimeMillis() - start + 1;
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

	public Vector3f getCurrentRotFrame()
	{
		return currentRotFrame;
	}

	public Vector3f getPrevRotFrame()
	{
		return prevRotFrame;
	}
}
