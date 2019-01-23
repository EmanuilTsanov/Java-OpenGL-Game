package opengl.java.audio;

import org.lwjgl.openal.AL10;

public class AudioSource
{
	private int sourceID;

	public AudioSource()
	{
		sourceID = AL10.alGenSources();
		AL10.alSource3f(sourceID, AL10.AL_GAIN, 0, 0, 0);
		AL10.alSourcef(sourceID, AL10.AL_PITCH, 2);
		AL10.alSource3f(sourceID, AL10.AL_POSITION, 0, 0, 0);
	}

	public void play(int buffer)
	{
		AL10.alSourcei(sourceID, AL10.AL_BUFFER, buffer);
		AL10.alSourcePlay(sourceID);
	}

	public void delete()
	{
		AL10.alDeleteSources(sourceID);
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
