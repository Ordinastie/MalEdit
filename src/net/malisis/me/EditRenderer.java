package net.malisis.me;

import net.malisis.core.renderer.BaseRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EditRenderer
{
	private static int X = 0;
	private static int Y = 1;
	private static int Z = 2;

	public BaseRenderer renderer = new BaseRenderer();
	public Tessellator t = Tessellator.instance;
	public EditManager editManager = EditManager.getManager();
	public Edit currentEdit;
	public double[] playerPosition;

	@SubscribeEvent
	public void onPostRender(RenderWorldLastEvent event)
	{
		currentEdit = editManager.getCurrentEdit();
		EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
		playerPosition = new double[] { p.lastTickPosX + (p.posX - p.lastTickPosX) * (double) event.partialTicks,
				p.lastTickPosY + (p.posY - p.lastTickPosY) * (double) event.partialTicks,
				p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * (double) event.partialTicks };
		
		drawAffectedBlocks();
		

	}

	private void drawAffectedBlocks()
	{
		if (currentEdit.firstAnchor != null && currentEdit.secondAnchor != null)
		{
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glTranslated(-playerPosition[X], -playerPosition[Y], -playerPosition[Z]);

			ChunkPosition f = currentEdit.firstAnchor;
			ChunkPosition s = currentEdit.secondAnchor;
			int fx = Math.min(f.chunkPosX, s.chunkPosX);
			int sx = Math.max(f.chunkPosX, s.chunkPosX);
			int fy = Math.min(f.chunkPosY, s.chunkPosY);
			int sy = Math.max(f.chunkPosY, s.chunkPosY);
			int fz = Math.min(f.chunkPosZ, s.chunkPosZ);
			int sz = Math.max(f.chunkPosZ, s.chunkPosZ);

			AxisAlignedBB aabb = AxisAlignedBB.getAABBPool().getAABB(fx, fy, fz, sx + 1, sy + 1, sz + 1);
			drawBox(aabb, 0xFF666666);

			aabb = AxisAlignedBB.getAABBPool().getAABB(f.chunkPosX, f.chunkPosY, f.chunkPosZ, f.chunkPosX + 1, f.chunkPosY + 1,
					f.chunkPosZ + 1);
			RenderGlobal.drawOutlinedBoundingBox(aabb, 0xFF3333);

			aabb = AxisAlignedBB.getAABBPool().getAABB(s.chunkPosX, s.chunkPosY, s.chunkPosZ, s.chunkPosX + 1, s.chunkPosY + 1,
					s.chunkPosZ + 1);
			RenderGlobal.drawOutlinedBoundingBox(aabb, 0xFF3333);

			GL11.glEnable(GL11.GL_DEPTH_TEST);
			
			
			// for(int i = fx; i <= sx; i++)
			// for(int j = fy; j <= sy; j++)
			// for(int k = fz; k <= sz; k++)
			// {
			// int color = 0x999999;
			// if((i == currentEdit.firstAnchor.chunkPosX && j ==
			// currentEdit.firstAnchor.chunkPosY && k ==
			// currentEdit.firstAnchor.chunkPosZ)
			// || (i == currentEdit.secondAnchor.chunkPosX && j ==
			// currentEdit.secondAnchor.chunkPosY && k ==
			// currentEdit.secondAnchor.chunkPosZ))
			// color = 0xFF0000;
			// drawSelectionBox(i, j, k, color);
			// }

		}

	}

	public void drawBox(AxisAlignedBB aabb, int color)
	{
	
//		t.startDrawingQuads();
//		RenderParameters rp = new RenderParameters();
//		rp.useTexture = false;
//		rp.renderBounds = new double[][] {{aabb.minX, aabb.minY, aabb.minZ}, {aabb.maxX, aabb.maxY, aabb.maxZ }};
//		rp.colorMultiplier = color & 0xFFFFFF;
//		rp.alpha = (color >> 24) & 0xFF;
//		
//		renderer.drawShape(ShapePreset.Cube(), rp);
//		t.draw();
		
		
		GL11.glBegin(GL11.GL_TRIANGLES);

		GL11.glColor3f(1, 0.3F, 0.3F);
		GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);
		GL11.glVertex3d(aabb.minX,  aabb.minY, aabb.maxZ);
		GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ); 
		 
		GL11.glEnd();
		
	
		
		
	}

}
