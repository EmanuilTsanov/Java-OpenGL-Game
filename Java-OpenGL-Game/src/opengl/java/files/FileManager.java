package opengl.java.files;

public class FileManager {

	//
	// public void takeScreenshot()
	// {
	// eShader.start();
	// prepareScreen(0, 1, 1);
	// renderEntities();
	// eShader.stop();
	// tShader.start();
	// renderTerrain();
	// tShader.stop();
	// unbindBuffers();
	// saveScreenshot();
	// }
	//
	// public void saveScreenshot()
	// {
	// ByteBuffer buffer = readScreen(0, 0, Window.WIDTH, Window.HEIGHT);
	// File file = new File("Screenshot " + Calendar.DATE + ".png");
	// String format = "png";
	// BufferedImage image = new BufferedImage(Window.WIDTH, Window.HEIGHT,
	// BufferedImage.TYPE_INT_RGB);
	//
	// for (int x = 0; x < Window.WIDTH; x++)
	// {
	// for (int y = 0; y < Window.HEIGHT; y++)
	// {
	// int i = (x + (Window.WIDTH * y)) * 4;
	// int r = buffer.get(i) & 0xFF;
	// int g = buffer.get(i + 1) & 0xFF;
	// int b = buffer.get(i + 2) & 0xFF;
	// image.setRGB(x, Window.HEIGHT - (y + 1), (0xFF << 24) | (r << 16) | (g << 8)
	// | b);
	// }
	// }
	//
	// try
	// {
	// ImageIO.write(image, format, file);
	// System.out.println("SCREENSHOT");
	// }
	// catch (IOException e)
	// {
	// e.printStackTrace();
	// }
	// }

	// public RawModel initModel(int size) {
	// float[] vertices = { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 2.0f, 2.0f, 0.0f, 2.0f,
	// 2.0f, 0.0f, 0.0f };
	// int[] indices = { 0, 1, 3, 3, 1, 2 };
	// float[] texCoords = { 0 };
	// float[] normals = { 0 };
	// if (size % 2 == 0) {
	// int s = size / 2;
	// int t = s * TerrainGenerator.getQuadSize();
	// vertices = new float[] { -t, 0.0f, -t, -t, 0.0f, t, t, 0.0f, t, t, 0.0f, -t
	// };
	// }
	// ModelLoader loader = new ModelLoader();
	// return loader.loadModel(vertices, indices, texCoords, normals);
	// }
	//
	// public void renderModel(RawModel model, Vector3f pos) {
	// GL30.glBindVertexArray(model.getVAOID());
	// GL20.glEnableVertexAttribArray(0);
	// cShader.loadViewMatrix(camera);
	// cShader.loadTransformationMatrix(pos, new Vector3f(0, 0, 0), 1f);
	// GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(),
	// GL11.GL_UNSIGNED_INT, 0);
	// GL20.glDisableVertexAttribArray(0);
	// GL30.glBindVertexArray(0);
	// }

}
