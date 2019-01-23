package opengl.java.audio;

import org.lwjgl.openal.AL10;

public class AudioSource
{
	private int sourceID;

	public AudioSource()
	{
		sourceID = AL10.alGenSources();
	}

	public void play(int buffer)
	{
		this.stop();
		AL10.alSourcei(sourceID, AL10.AL_BUFFER, buffer);
		AL10.alSourcePlay(sourceID);
	}

	public boolean isPlaying()
	{
		return AL10.alGetSourcei(sourceID, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}

	public void pause()
	{
		AL10.alSourcePause(sourceID);
	}

	public void resume()
	{
		AL10.alSourcePlay(sourceID);
	}

	public void stop()
	{
		AL10.alSourceStop(sourceID);
	}

	public void delete()
	{
		this.stop();
		AL10.alDeleteSources(sourceID);
	}

	public void setLooping(boolean bool)
	{
		AL10.alSourcei(sourceID, AL10.AL_LOOPING, bool ? AL10.AL_TRUE : AL10.AL_FALSE);
	}

	public void setVelocity(float x, float y, float z)
	{
		AL10.alSource3f(sourceID, AL10.AL_VELOCITY, x, y, z);
	}

	public void setVolume(float volume)
	{
		AL10.alSourcef(sourceID, AL10.AL_GAIN, volume);
	}

	public void setPitch(float pitch)
	{
		AL10.alSourcef(sourceID, AL10.AL_PITCH, pitch);
	}

	public void setPosition(float x, float y, float z)
	{
		AL10.alSource3f(sourceID, AL10.AL_POSITION, x, y, z);
	}
}
