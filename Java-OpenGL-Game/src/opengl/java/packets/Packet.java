package opengl.java.packets;

import java.io.Serializable;

public abstract class Packet implements Serializable
{
	private static final long serialVersionUID = -2837019272511593145L;

	public abstract Packet getCopy();
}
