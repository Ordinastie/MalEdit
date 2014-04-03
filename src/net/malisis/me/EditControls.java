package net.malisis.me;

import net.malisis.core.util.Point;
import net.malisis.core.util.Raytrace;
import net.malisis.core.util.Vector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EditControls
{
	public EditManager editManager = EditManager.getManager();
	public long lastEvent;
	
	public EditControls()
	{
		
		
	}
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(!event.entity.worldObj.isRemote)
			return;
		
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR || event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
		{
			EntityPlayer p = (EntityPlayer) event.entity;
	        Point start = new Point(p.getPosition(1));
	        Vector direction = new Vector(p.getLook(1));
			Raytrace rt = new Raytrace(start, direction);
			MovingObjectPosition mop = rt.trace();
			if(mop != null && mop.typeOfHit != MovingObjectType.MISS)
				editManager.getCurrentEdit().setAnchor(new ChunkPosition(mop.blockX, mop.blockY, mop.blockZ));
		}
	
	}
	
}
