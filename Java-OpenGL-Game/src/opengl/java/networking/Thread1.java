package opengl.java.networking;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Player;

public class Thread1 extends Thread
{
	private long onlinePlayers;

	private Client client;

	private Player player;

	private static ArrayList<Player> players = new ArrayList<Player>();

	public Thread1(Player player)
	{
		client = new Client();
		this.player = player;
	}

	@Override
	public void run()
	{
		while (true)
		{
			client.update(player);
			onlinePlayers = client.getOnlinePlayers();
			if (players.size() < onlinePlayers - 1)
			{
				for (int i = 0; i < onlinePlayers - 1 - players.size(); i++)
				{
					Player player = new Player();
					players.add(player);
				}
			}
			else if (players.size() > onlinePlayers - 1)
			{
				for (int i = 0; i < players.size() - onlinePlayers - 1; i++)
				{
					players.remove(i);
				}
			}
			for (int i = 0; i < players.size(); i++)
			{
				client.read(players.get(i));
			}
		}
	}

	public ArrayList<Player> getPlayers()
	{
		return players;
	}

	public Vector3f getPostion()
	{
		return client.getPosition();
	}

	public Vector3f getPastPostion()
	{
		return client.getPastPosition();
	}
}
