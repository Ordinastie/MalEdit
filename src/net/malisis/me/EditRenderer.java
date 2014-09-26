package net.malisis.me;

import net.malisis.core.renderer.BaseRenderer;
import net.malisis.core.renderer.RenderParameters;
import net.malisis.core.renderer.preset.ShapePreset;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EditRenderer extends BaseRenderer
{
	private static int X = 0;
	private static int Y = 1;
	private static int Z = 2;

	public World world;
	public Tessellator t = Tessellator.instance;
	public EditManager editManager = EditManager.getManager();
	public Edit currentEdit;
	public double[] playerPosition;

	@SubscribeEvent
	public void onPostRender(RenderWorldLastEvent event)
	{
		currentEdit = editManager.getCurrentEdit();
		EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
		playerPosition = new double[] { p.lastTickPosX + (p.posX - p.lastTickPosX) * event.partialTicks,
				p.lastTickPosY + (p.posY - p.lastTickPosY) * event.partialTicks,
				p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * event.partialTicks };
		world = Minecraft.getMinecraft().theWorld;
		drawAffectedBlocks();

	}

	private void drawAffectedBlocks()
	{
		if (currentEdit.firstAnchor != null && currentEdit.secondAnchor != null)
		{

			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDisable(GL11.GL_TEXTURE_2D);

			ChunkPosition f = currentEdit.firstAnchor;
			ChunkPosition s = currentEdit.secondAnchor;
			int fx = Math.min(f.chunkPosX, s.chunkPosX);
			int sx = Math.max(f.chunkPosX, s.chunkPosX);
			int fy = Math.min(f.chunkPosY, s.chunkPosY);
			int sy = Math.max(f.chunkPosY, s.chunkPosY);
			int fz = Math.min(f.chunkPosZ, s.chunkPosZ);
			int sz = Math.max(f.chunkPosZ, s.chunkPosZ);

			reset();

			GL11.glTranslated(-playerPosition[X], -playerPosition[Y], -playerPosition[Z]);

			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(fx, fy, fz, sx + 1, sy + 1, sz + 1);
			drawBox(aabb, 0x55333333);
			drawOutline(aabb, 0xFF333333);

			GL11.glEnable(GL11.GL_DEPTH_TEST);

			aabb = AxisAlignedBB.getBoundingBox(f.chunkPosX, f.chunkPosY, f.chunkPosZ, f.chunkPosX + 1, f.chunkPosY + 1, f.chunkPosZ + 1);
			drawOutline(aabb, 0xFFFF6666);
			// drawOutline()

			aabb = AxisAlignedBB.getBoundingBox(s.chunkPosX, s.chunkPosY, s.chunkPosZ, s.chunkPosX + 1, s.chunkPosY + 1, s.chunkPosZ + 1);
			drawOutline(aabb, 0xFFFF6666);

			// GL11.glTranslated(playerPosition[X], playerPosition[Y], playerPosition[Z]);

			RenderParameters rp = new RenderParameters();
			rp.colorMultiplier.set(0xFFFFFF);
			rp.alpha.set(0x55);

			for (int i = fx; i <= sx; i++)
				for (int j = fy; j <= sy; j++)
					for (int k = fz; k <= sz; k++)
					{
						Block b = world.getBlock(i, j, k);
						if (!b.isAir(world, i, j, k))
						{
							startDrawing(GL11.GL_LINES);
							set(world, b, i, j, k, world.getBlockMetadata(i, j, k));
							prepare(TYPE_ISBRH_WORLD);
							drawShape(ShapePreset.Cube(), rp);
							clean();
							t.draw();
						}
					}

			GL11.glEnable(GL11.GL_TEXTURE_2D);

		}

	}

	public void drawBox(AxisAlignedBB aabb, int color)
	{
		// t.startDrawing(GL11.GL_LINE_STRIP);

		RenderParameters rp = new RenderParameters();
		rp.useTexture.set(false);
		rp.renderBounds.set(AxisAlignedBB.getBoundingBox(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ));
		rp.colorMultiplier.set(color & 0xFFFFFF);
		rp.alpha.set((color >> 24) & 0xFF);

		t.startDrawingQuads();
		drawShape(ShapePreset.Cube(), rp);
		t.draw();

	}

	public void drawOutline(AxisAlignedBB aabb, int color)
	{
		RenderParameters rp = new RenderParameters();
		rp.useTexture.set(false);
		rp.renderBounds.set(AxisAlignedBB.getBoundingBox(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ));
		rp.colorMultiplier.set(color & 0xFFFFFF);
		rp.alpha.set((color >> 24) & 0xFF);

		t.startDrawing(GL11.GL_LINE_STRIP);
		drawShape(ShapePreset.Cube(), rp);
		t.draw();
	}

}
