package com.kingcolton1.blockelevator.API;

import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.network.ChatColors;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.game.level.World;

public class ElevatorBlock extends Block {
    public ElevatorBlock(int id, Material material) {
        super(id, material);
    }

	//public static AssignBlock api;
	//public static NetworkPlayer chat;

    public static void jump(World world, int x, int y, int z, EntityPlayer player) {
		final int maxYStep = 40; // This is the highest you can reach a successive platform above you

		for (int y2 = y+3; y2 < Math.min(y + maxYStep + 1, world.highestY); y2++){
			if (world.getBlockId(x, y2, z) == 41){
				teleport(x + 0.5, y2+1, z+0.5, player);
				return;
			}
		}
	}
	public static void sneak(World world, int x, int y, int z, EntityPlayer player) {
		final int maxYStep = 40; // This is the lowest you can reach a platform below you

		for (int y2 = y-1; y2 > Math.max(y - maxYStep - 1, 0); y2--){
			if (world.getBlockId(x, y2, z) == 41){
				teleport(x + 0.5, y2+1, z + 0.5, player);
				return;
			}
		}
	}

	public static void teleport(double x, double y, double z, EntityPlayer player) {
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			playerMP.playerNetServerHandler.teleportTo(x, y, z, playerMP.rotationYaw, playerMP.rotationPitch);
		} else if (player instanceof EntityPlayer) {
			player.setPosition(x, y + player.height, z);
		}
	}
}
