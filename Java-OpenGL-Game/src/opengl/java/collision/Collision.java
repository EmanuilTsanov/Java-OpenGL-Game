package opengl.java.collision;

import opengl.java.entity.Entity;

public class Collision
{
	public static boolean checkCollision(Entity a, Entity b)
	{
		CollisionModel colModelA = Entity.getCollisionModel(a.getId());
		CollisionModel colModelB = Entity.getCollisionModel(b.getId());

		for (int i = 0; i < colModelA.getBoxes().size(); i++)
		{
			CollisionBox boxA = colModelA.getBoxes().get(i);
			float boxAX = a.getPosition().x + boxA.getX() * a.getScale();
			float boxAY = a.getPosition().y + boxA.getY() * a.getScale();
			float boxAZ = a.getPosition().z + boxA.getZ() * a.getScale();
			float boxASizeX = boxA.getSizeX() * a.getScale();
			float boxASizeY = boxA.getSizeY() * a.getScale();
			float boxASizeZ = boxA.getSizeZ() * a.getScale();
			for (int j = 0; j < colModelB.getBoxes().size(); j++)
			{
				CollisionBox boxB = colModelB.getBoxes().get(j);
				float boxBX = b.getPosition().x + boxB.getX() * b.getScale();
				float boxBY = b.getPosition().y + boxB.getY() * b.getScale();
				float boxBZ = b.getPosition().z + boxB.getZ() * b.getScale();
				float boxBSizeX = boxB.getSizeX() * b.getScale();
				float boxBSizeY = boxB.getSizeY() * b.getScale();
				float boxBSizeZ = boxB.getSizeZ() * b.getScale();
				if (Math.abs(boxAX - boxBX) < (boxASizeX + boxBSizeX) / 2f)
				{
					if (Math.abs(boxAY - boxBY) < (boxASizeY + boxBSizeY) / 2f)
					{
						if (Math.abs(boxAZ - boxBZ) < (boxASizeZ + boxBSizeZ) / 2f)
						{
							return true;
						}
					}
				}

			}
		}
		return false;
	}
}
