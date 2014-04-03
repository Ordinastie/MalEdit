package net.malisis.me;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class MalEditCommand extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "maledit";
	}

	@Override
	public String getCommandUsage(ICommandSender var1)
	{
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params)
	{
		if (params[0] != null)
		{
			switch (params[0])
			{
				default:
					helpCommand(sender);
					break;
			}

		}		
	}
	
	private void helpCommand(ICommandSender sender)
	{
	
	}

	
}
