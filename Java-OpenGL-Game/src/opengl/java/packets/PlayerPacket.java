package opengl.java.packets;

import java.io.Serializable;

import org.lwjgl.util.vector.Vector3f;

import opengl.java.entity.Player;

public class PlayerPacket implements Serializable
{
	private int port;
	
	private Vector3f position;
	private Vector3f rotation;

	private static final long serialVersionUID = -3712673437714393338L;

	public PlayerPacket()
	{
		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
	}

	public void update(Player player)
	{
		position = player.getPosition();
		rotation = player.getRotation();
	}

	public PlayerPacket setPosition(Vector3f position)
	{
		this.position = position;
		return this;
	}

	public PlayerPacket setRotation(Vector3f rotation)
	{
		this.rotation = rotation;
		return this;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public Vector3f getRotation()
	{
		return rotation;
	}

	public PlayerPacket getCopy()
	{
		return new PlayerPacket().setPosition(position).setRotation(rotation);
	}
}