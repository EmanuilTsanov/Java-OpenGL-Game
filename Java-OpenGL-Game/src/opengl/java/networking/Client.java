package opengl.java.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class Client extends Thread
{
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;

	private PacketSender sender;
	private PacketReceiver receiver;

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

	@Override
	public void run()
	{
		while (running)
		{
			
		}
	}
}
