package net.malisis.me;

import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;


@Mod(modid = MalEdit.modid, name = MalEdit.modname, version = MalEdit.version)
public class MalEdit
{
	public static final String modid = "maledit";
	public static final String modname = "MalEdit";
	public static final String version = "1.7.2-0.1";

	public static MalEdit instance;

	public MalEdit()
	{
		instance = this;
	}

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		if (event.getSide() == Side.CLIENT)
		{
			MinecraftForge.EVENT_BUS.register(new EditRenderer());
			MinecraftForge.EVENT_BUS.register(new EditControls());
		}
	}

	@EventHandler
	public void serverStart(FMLServerStartingEvent event)
	{
		MinecraftServer server = MinecraftServer.getServer();
		((ServerCommandManager) server.getCommandManager()).registerCommand(new MalEditCommand());

	}

}
