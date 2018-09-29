package opengl.java.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

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
	}

	@Override
	public void run()
	{
		while (running)
		{
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
}
