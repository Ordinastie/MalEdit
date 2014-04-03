package net.malisis.me;

import net.malisis.core.MalisisCore;
import net.minecraft.world.ChunkPosition;

public class Edit
{

	public ChunkPosition firstAnchor;
	public ChunkPosition secondAnchor;
	
	public Edit()
	{
		
		
	}
	
	/**
	 * Set the first or second anchor at <code>pos</code>. If an anchor is already present there, unset it
	 * @param pos
	 */
	public void setAnchor(ChunkPosition pos)
	{
		String s = "";
		if(firstAnchor == null)
		{
			s = String.format("First anchor set at %d, %d, %d", pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ); 
			firstAnchor = pos;
		}
		else if(firstAnchor.equals(pos))
		{
			s = String.format("First anchor unset at %d, %d, %d", pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
			firstAnchor = null;
		}
		else if(secondAnchor == null)
		{
			s = String.format("Second anchor set at %d, %d, %d", pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
			secondAnchor = pos;
		}
		else if (secondAnchor.equals(pos))
		{
			s = String.format("Second anchor unset at %d, %d, %d", pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);
			secondAnchor = null;
		}
		else
		{
			s = String.format("Both anchor unset");
			firstAnchor = secondAnchor = null;
		}
		
		MalisisCore.Message(s);
	}
	
}
