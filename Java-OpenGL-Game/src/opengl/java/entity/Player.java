package opengl.java.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.model.TexturedModel;
import opengl.java.networking.Client;
import opengl.java.terrain.Terrain;
import opengl.java.view.Camera;
import opengl.java.window.FPSCounter;
import opengl.java.window.Window;
import opengl.java.window.WindowFrameController;

public class Player extends Entity
{
	private boolean jumping;
	private float jumpSpeed;
	private float currentJumpHeight;
	private static final float maxJumpSpeed = 20f;

	private float mouseX = Window.getWidth() / 2;

	private Vector3f pDist = new Vector3f(0, 0, 0);
	private Vector3f rDist = new Vector3f(0, 0, 0);

	public Player()
	{
		super(TexturedModel.PLAYER.getID());
	}

	private static final float speed = 16f;

	public void update(Camera camera, Terrain terrain)
	{
		float a = speed * WindowFrameController.getFrameTimeSeconds();
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
				{
					jumping = true;
					jumpSpeed = maxJumpSpeed;
				}
			}
			rotation.y -= (Mouse.getX() - mouseX) * 0.1f;
			Mouse.setGrabbed(true);
			Mouse.setCursorPosition(Window.getWidth() / 2, Window.getHeight() / 2);
			if (jumping)
			{
				currentJumpHeight+= jumpSpeed * WindowFrameController.getFrameTimeSeconds();
				System.out.println(WindowFrameController.getFrameTimeSeconds());
				jumpSpeed -= 50f * WindowFrameController.getFrameTimeSeconds();
				if(currentJumpHeight + position.y <= 0) {
					jumping = false;
					jumpSpeed = 0;
					currentJumpHeight = 0;
				}
			}
			position.y = terrain.getHeightOfTerrain(position.x, position.z) + currentJumpHeight;
		}
	}

	public void insert(Client client)
	{
		if (client.hasUpdate())
		{
			this.position = client.getPrevPacket().getPosition();
			this.rotation = client.getPrevPacket().getRotation();
			Vector3f newPos = client.getNewPacket().getPosition();
			Vector3f prevPos = client.getPrevPacket().getPosition();
			Vector3f newRot = client.getNewPacket().getRotation();
			Vector3f prevRot = client.getPrevPacket().getRotation();
			pDist = new Vector3f(newPos.getX() - prevPos.getX(), newPos.getY() - prevPos.getY(), newPos.getZ() - prevPos.getZ());
			rDist = new Vector3f(newRot.getX() - prevRot.getX(), newRot.getY() - prevRot.getY(), newRot.getZ() - prevRot.getZ());
			client.setHasUpdate(false);
		}
	}

	public void move(long time)
	{
		int t = (int) (time / (1000 / FPSCounter.getFPS()));
		this.position.x += pDist.getX() / t;
		this.position.y += pDist.getY() / t;
		this.position.z += pDist.getZ() / t;
		this.rotation.x += rDist.getX() / t;
		this.rotation.y += rDist.getY() / t;
		this.rotation.z += rDist.getZ() / t;
	}
}
