package opengl.java.interaction;

import org.lwjgl.input.Keyboard;

import opengl.java.gui.Inventory;

public class KeyboardMaster
{
	public static void update()
	{
		while (Keyboard.next())
		{
			if (Keyboard.getEventKeyState())
			{
			}
			else
			{
				if (Keyboard.getEventKey() == Keyboard.KEY_TAB)
				{
					Inventory.toggle();
				}
			}
		}
	}
}
