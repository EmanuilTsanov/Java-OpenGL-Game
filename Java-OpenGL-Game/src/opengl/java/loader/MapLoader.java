package opengl.java.loader;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import opengl.java.entity.Entity;

public class MapLoader
{
	public static ArrayList<Entity> loadMap(String file)
	{
		ArrayList<Entity> entities = new ArrayList<Entity>();
		try (DataInputStream stream = new DataInputStream(new FileInputStream("assets/maps/" + file + ".map")))
		{
			while (!(stream.available() == 0))
			{
				Entity e = new Entity(stream.readInt())
						.setPosition(new Vector3f(stream.readFloat(), stream.readFloat(), stream.readFloat()))
						.setRotationInRadians(new Vector3f(stream.readFloat(), stream.readFloat(), stream.readFloat()))
						.getCopy();
				entities.add(e);
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("System was unable to find file: " + file + ".");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return entities;
	}

	public static void saveEntity(String mapName, Entity ent)
	{
		try
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			Element rootElement = doc.createElement("entities");
			doc.appendChild(rootElement);

			Element entity = doc.createElement("entity");
			entity.setAttribute("ID", "" + ent.getID());
			entity.setAttribute("asset", "" + ent.getAsset());
			rootElement.appendChild(entity);

			Element position = doc.createElement("position");
			entity.appendChild(position);

			Attr x = doc.createAttribute("x");
			x.setValue("1");
			position.setAttributeNode(x);
			Attr y = doc.createAttribute("y");
			y.setValue("2");
			position.setAttributeNode(y);
			Attr z = doc.createAttribute("z");
			z.setValue("3");
			position.setAttributeNode(z);

			Element rotation = doc.createElement("rotation");
			entity.appendChild(rotation);

			Attr x1 = doc.createAttribute("x");
			x1.setValue("4");
			rotation.setAttributeNode(x1);
			Attr y1 = doc.createAttribute("y");
			y1.setValue("5");
			rotation.setAttributeNode(y1);
			Attr z1 = doc.createAttribute("z");
			z1.setValue("6");
			rotation.setAttributeNode(z1);

			Element scale = doc.createElement("scale");
			entity.appendChild(scale);

			Attr s = doc.createAttribute("s");
			s.setValue("1");
			scale.setAttributeNode(s);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("assets/maps/new_map.xml"));
			transformer.transform(source, result);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void clearMapFile(String file)
	{
		try (BufferedReader stream = new BufferedReader(new FileReader(new File("assets/maps/" + file + ".map"))))
		{
			String line;
			while ((line = stream.readLine()) != null)
			{
				if (line.startsWith("e "))
				{

				}
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
