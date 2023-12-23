package com.kingcolton1.blockelevator.API;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.game.level.World;

public class ElevatorBlock extends Block {
    public ElevatorBlock(int id, Material material) {
        super(id, material);
    }

	public static AssignBlock api;
	public static String test;

    public static void jump(World world, int x, int y, int z, EntityPlayer player) {
		int counter = 2;
		for (int y2 = y+1; y2 < 255; y2++) {
			if (counter > 0){
				counter--;
                
                // Unfortunately ReIndev doesn't include world.getBlock() for some reason, making it more difficult
                // to work with finding coordinate of a specific block.
                // ReIndev developers, please add world.getBlock()!
                // Currently I am having exceedingly trouble with "world.getBlockId" parts
                // as they seemingly don't respond to any of the event.
                // I can't use ElevatorBlock as it extends to Block but is NOT compatible with world.getBlockId.
                // world.getBlock WOULD be compatible with Block instead of this workaround that isn't even working!
				if (world.getBlockId(x, y2, z) == api.getID()) {
					return;
				}
			}
			if (world.getBlockId(x, y2, z) == api.getID()) {
				teleport(x+0.5, y2+1, z+0.5, player);
				break;
			}
		}
	}
	public static void sneak(World world, int x, int y, int z, EntityPlayer player) {
		int counter = 2;
		for (int y2 = y-1; y2 > 0; y2--) {
			if (counter > 0) {
				counter--;
				if (world.getBlockId(x, y2, z) == api.getID()) {
					return;
				}
			}
			if (world.getBlockId(x, y2, z) == api.getID()) {
				teleport(x+0.5, y2+1, z+0.5, player);
				break;
			}
		}
	}

	public static void teleport(double x, double y, double z, EntityPlayer player) {
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			playerMP.playerNetServerHandler.teleportTo(x, y, z, 0, 0);
		} else if (player instanceof EntityPlayer) {
			EntityPlayer playerSP = (EntityPlayer)player;
			playerSP.setPosition(x, y + playerSP.height, z);
		}
	}
}
