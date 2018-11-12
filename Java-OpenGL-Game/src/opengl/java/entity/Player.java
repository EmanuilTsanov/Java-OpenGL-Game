package opengl.java.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import opengl.java.model.TexturedModel;
import opengl.java.packets.PlayerPacket;
import opengl.java.terrain.Terrain;
import opengl.java.view.Camera;
import opengl.java.window.Window;
import opengl.java.window.WindowFrameController;

public class Player extends Entity
{
	private boolean jumping;
	private float jumpSpeed;
	private static final float maxJumpSpeed = 5f;

	private float mouseX = Window.getWidth() / 2;

	public Player()
	{
		super(TexturedModel.PLAYER.getID());
	}

	private static final float speed = 16f;

	public void update(Camera camera, Terrain terrain)
	{
		float a = speed *WindowFrameController.getFrameTimeSeconds();
		if (camera.getMode() == Camera.THIRD_PERSON)
		{
			float camYaw = (float) Math.toRadians(rotation.y);
			float dx = (float) Math.sin(camYaw) * a;
			float dz = (float) Math.cos(camYaw) * a;
			if (Keyboard.isKeyDown(Keyboard.KEY_W))
			{
				position.x += dx;
				position.z += dz;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S))
			{
				position.x -= dx;
				position.z -= dz;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A))
			{
				rotate(0, 1, 0);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
			{

				rotate(0, -1, 0);
			}
		}
		else if (camera.getMode() == Camera.FIRST_PERSON)
		{
			float camYaw = (float) Math.toRadians(camera.getRotation().y + 90);
			float dx = (float) Math.cos(camYaw) * a;
			float dz = (float) Math.sin(camYaw) * a;
			if (Keyboard.isKeyDown(Keyboard.KEY_W))
			{
				position.x -= dx;
				position.z -= dz;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S))
			{
				position.x += dx;
				position.z += dz;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A))
			{
				float rot = (float) Math.toRadians(camera.getRotation().y);
				float dx1 = (float) Math.cos(rot) * a;
				float dz1 = (float) Math.sin(rot) * a;
				position.x -= dx1;
				position.z -= dz1;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
			{
				float rot = (float) Math.toRadians(camera.getRotation().y - 180);
				float dx1 = (float) Math.cos(rot) * a;
				float dz1 = (float) Math.sin(rot) * a;
				position.x -= dx1;
				position.z -= dz1;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
			{
				if (!jumping)
					jumping = true;
				jumpSpeed = maxJumpSpeed;
			}
			if(!jumping)
			position.y = terrain.getHeightOfTerrain(position.x, position.z);
			rotation.y -= (Mouse.getX() - mouseX) * 0.1f;
			Mouse.setGrabbed(true);
			Mouse.setCursorPosition(Window.getWidth() / 2, Window.getHeight() / 2);
			position.y += jumpSpeed;
			if (position.y > terrain.getHeightOfTerrain(position.x, position.z))
			{
				jumpSpeed -= 0.1f;
			}
			else if(position.y <= terrain.getHeightOfTerrain(position.x, position.z))
				jumping = false;
		}
	}
	
	public void insert(PlayerPacket packet) {
		if(packet!=null) {
		this.position = packet.getPosition();
		this.rotation = packet.getRotation();
		}
	}
}
