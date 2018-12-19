package opengl.java.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.calculations.Maths;
import opengl.java.model.TexturedModel;
import opengl.java.networking.Client;
import opengl.java.terrain.Terrain;
import opengl.java.view.Camera;
import opengl.java.window.FPSCounter;
import opengl.java.window.FrameController;
import opengl.java.window.Window;

public class Player extends Entity
{
	private boolean jumping;
	private final float jumpSpeed = 40f;
	private float currentJumpSpeed;
	private boolean falling;
	private float mouseX = Window.getWidth() / 2;

	private static final float height = 2.5f;
	private float currentHeight = height;

	private Vector3f pDist = new Vector3f(0, 0, 0);
	private Vector3f rDist = new Vector3f(0, 0, 0);

	private static final float speed = 16f;
	private static float currentSpeed = speed;

	public Player()
	{
		super(TexturedModel.PLAYER.getID());
	}

	public void update(Camera camera, Terrain terrain)
	{
		if (camera.getMode() == Camera.FIRST_PERSON)
		{
			rotation.y -= (Mouse.getX() - mouseX) * 0.1f;
		}
		else if (camera.getMode() == Camera.SCOPE)
		{
			rotation.y -= (Mouse.getX() - mouseX) / Maths.getDefaultFOV() * camera.getZoom() / 10;
		}
		handleKeyboardInput(camera, terrain);
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

	public void handleKeyboardInput(Camera camera, Terrain terrain)
	{
		float a = currentSpeed * FrameController.getFrameTimeSeconds() * (camera.getMode() == Camera.SCOPE ? 0.1f : 1f);
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
			if (!jumping && !falling)
			{
				jumping = true;
				currentJumpSpeed = jumpSpeed;
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			currentSpeed = speed * 2f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
		{
			currentSpeed = speed / 2f;
			currentHeight = height / 1.5f;
		}
		else
		{
			currentSpeed = speed;
			currentHeight = height;
		}
		Mouse.setGrabbed(true);
		Mouse.setCursorPosition(Window.getWidth() / 2, Window.getHeight() / 2);
		if (jumping)
		{
			position.y += currentJumpSpeed * FrameController.getFrameTimeSeconds();
			currentJumpSpeed /= 10f * FrameController.getFrameTimeSeconds()+1;
			if (currentJumpSpeed < 1f)
			{
				jumping = false;
				falling = true;
			}
		}
		else if (falling)
		{
			position.y -= currentJumpSpeed * FrameController.getFrameTimeSeconds();
			if(currentJumpSpeed>20f) currentJumpSpeed = 20f;
			currentJumpSpeed *= 10f * FrameController.getFrameTimeSeconds()+1;
			if (position.y < terrain.getHeightOfTerrain(position.x, position.z))
				falling = false;
		}
		else
			position.y = terrain.getHeightOfTerrain(position.x, position.z);
	}

	public float getHeight()
	{
		return currentHeight;
	}

	public void move(long time)
	{
		try
		{
			int t = (int) (time / (1000 / FPSCounter.getFPS()));
			this.position.x += pDist.getX() / t;
			this.position.y += pDist.getY() / t;
			this.position.z += pDist.getZ() / t;
			this.rotation.x += rDist.getX() / t;
			this.rotation.y += rDist.getY() / t;
			this.rotation.z += rDist.getZ() / t;
		}
		catch (ArithmeticException e)
		{
			e.printStackTrace();
		}
	}
}
