package opengl.java.particles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import opengl.java.loader.ModelLoader;

public class ParticleManager
{
	private static Map<ParticleTexture, List<Particle>> particles = new HashMap<ParticleTexture, List<Particle>>();
	private static ParticleRenderer renderer;

	public static void initialize(ModelLoader loader)
	{
		renderer = new ParticleRenderer(loader);
	}

	public static void update()
	{
		Iterator<Entry<ParticleTexture, List<Particle>>> mapIterator = particles.entrySet().iterator();
		while (mapIterator.hasNext())
		{
			List<Particle> list = mapIterator.next().getValue();
			Iterator<Particle> iterator = list.iterator();
			while (iterator.hasNext())
			{
				Particle p = iterator.next();
				boolean alive = p.update();
				if (!alive)
				{
					iterator.remove();
					if (list.isEmpty())
					{
						mapIterator.remove();
					}
				}
			}
			InsertionSort.sortHighToLow(list);
		}
	}

	public static void renderParticles()
	{
		renderer.render(particles);
	}

	public static void cleanUp()
	{
		renderer.cleanUp();
	}

	public static void addParticle(Particle particle)
	{
		List<Particle> list = particles.get(particle.getTexture());
		if (list == null)
		{
			list = new ArrayList<Particle>();
			particles.put(particle.getTexture(), list);
		}
		list.add(particle);
	}
}
