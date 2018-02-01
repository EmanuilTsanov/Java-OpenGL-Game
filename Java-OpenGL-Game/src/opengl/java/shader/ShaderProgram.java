package opengl.java.shader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public abstract class ShaderProgram
{
	private int programID;
	private String defaultPath = "assets/shaders/";
	private String defaultExtension = ".sr";

	private FloatBuffer buffer = BufferUtils.createFloatBuffer(16);

	public ShaderProgram(String vertexFile, String fragmentFile)
	{
		programID = loadProgram(vertexFile, fragmentFile);
		getAllUniformLocations();
	}

	public void start()
	{
		GL20.glUseProgram(programID);
	}

	public void stop()
	{
		GL20.glUseProgram(0);
	}

	public abstract void bindAllAttributes();

	public void bindAttribute(int index, String name)
	{
		GL20.glBindAttribLocation(programID, index, name);
	}

	protected abstract void getAllUniformLocations();

	public int getUniformLocation(String name)
	{
		return GL20.glGetUniformLocation(programID, name);
	}

	private int loadProgram(String vShadFileName, String fShadFileName)
	{
		int programID = GL20.glCreateProgram();
		int vShader = loadShader(vShadFileName, GL20.GL_VERTEX_SHADER);
		int fShader = loadShader(fShadFileName, GL20.GL_FRAGMENT_SHADER);
		GL20.glAttachShader(programID, vShader);
		GL20.glAttachShader(programID, fShader);
		bindAllAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);

		int status = GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS);
		if (status == GL11.GL_TRUE)
		{
			System.out.println("Successfully linked shader program!");
		}
		else
		{
			System.out.println("Failed to link shader program!");
		}
		return programID;
	}

	private int loadShader(String file, int type)
	{
		int shaderID = GL20.glCreateShader(type);
		String source = readShader(file);
		GL20.glShaderSource(shaderID, source);
		GL20.glCompileShader(shaderID);
		int status = GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS);
		if (status == GL11.GL_TRUE)
		{
			System.out.println("Successfully compiled shader!");
		}
		else
		{
			System.out.println("Failed to compile shader!");
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
		}
		return shaderID;
	}

	public String readShader(String fileName)
	{
		StringBuilder builder = new StringBuilder();
		try
		{
			String line;
			BufferedReader reader = new BufferedReader(new FileReader(new File(defaultPath + fileName + defaultExtension)));
			while ((line = reader.readLine()) != null)
			{
				builder.append(line).append("\n");
			}
			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return builder.toString();
	}

	public void loadMatrix(int location, Matrix4f matrix)
	{
		matrix.store(buffer);
		buffer.flip();
		GL20.glUniformMatrix4(location, false, buffer);
	}

	public void loadVector3f(int location, Vector3f vector)
	{
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}

	public void loadVector4f(int location, Vector4f vector)
	{
		GL20.glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
	}

	public void loadFloat(int location, float var)
	{
		GL20.glUniform1f(location, var);
	}

	public void loadInt(int location, int var)
	{
		GL20.glUniform1i(location, var);
	}
	
	public void loadBoolean(int location, boolean value) {
		GL20.glUniform1f(location, value ? 1.0f : 0.0f);
	}
}
