package opengl.java.particles;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import opengl.java.view.Camera;
import opengl.java.window.WindowManager;

public class Particle
{
	private Vector3f position;
	private Vector3f velocity;
	private float gravityEffect;
	private float lifeLength;
	private float rotation;
	private float scale;

	private ParticleTexture texture;

	private Vector2f offset1 = new Vector2f();
	private Vector2f offset2 = new Vector2f();
	private float blend;

	private float elapsedTime = 0;
	private float distance;

	public Particle(ParticleTexture texture, Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation, float scale)
	{
		super();
		this.texture = texture;
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		ParticleManager.addParticle(this);
	}

	public ParticleTexture getTexture()
	{
		return texture;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public float getRotation()
	{
		return rotation;
	}

	public float getScale()
	{
		return scale;
	}

	public Vector2f getOffset1()
	{
		return offset1;
	}

	public Vector2f getOffset2()
	{
		return offset2;
	}

	public float getBlend()
	{
		return blend;
	}

	protected boolean update()
	{
		velocity.y += -50 * gravityEffect * WindowManager.getFrameTimeSeconds();
		Vector3f change = new Vector3f(velocity);
		change.scale(WindowManager.getFrameTimeSeconds());
		Vector3f.add(change, position, position);
		distance = Vector3f.sub(Camera.getPosition(), position, null).lengthSquared();
		updateTextureInfo();
		elapsedTime += WindowManager.getFrameTimeSeconds();
		return elapsedTime < lifeLength;
	}

	private void updateTextureInfo()
	{
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = texture.getNumberOfRows() * texture.getNumberOfRows();
		float atlasProgression = lifeFactor * stageCount;
		int index1 = (int) Math.floor(atlasProgression);
		int index2 = index1 < stageCount - 1 ? index1 + 1 : index1;
		this.blend = atlasProgression % 1;
		setTextureOffset(offset1, index1);
		setTextureOffset(offset2, index2);
	}

	private void setTextureOffset(Vector2f offset, int index)
	{
		int column = index % texture.getNumberOfRows();
		int row = index / texture.getNumberOfRows();
		offset.x = (float) column / texture.getNumberOfRows();
		offset.y = (float) row / texture.getNumberOfRows();
	}

	public float getDistance()
	{
		return distance;
	}
}
