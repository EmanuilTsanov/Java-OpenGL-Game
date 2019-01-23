package opengl.java.audio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class AudioMaster
{
	private static ArrayList<Integer> audioBuffers = new ArrayList<Integer>();

	public static void initialize()
	{
		try
		{
			AL.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}

	public static int loadSound(String file)
	{
		int bufferID = AL10.alGenBuffers();
		audioBuffers.add(bufferID);
		WaveData wavFile = null;
		try
		{
			wavFile = WaveData.create(new BufferedInputStream(new FileInputStream(file)));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found at " + file);
		}
		AL10.alBufferData(bufferID, wavFile.format, wavFile.data, wavFile.samplerate);
		wavFile.dispose();
		return bufferID;
	}

	public static void setListenerData(float x, float y, float z)
	{
		AL10.alListener3f(AL10.AL_POSITION, x, y, z);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}

	public static void clean()
	{
		for (int bufferID : audioBuffers)
		{
			AL10.alDeleteBuffers(bufferID);
		}
		AL.destroy();
	}
}
