package com.kingcolton1.blockelevator;

import com.fox2code.foxloader.loader.ServerMod;
import com.kingcolton1.blockelevator.BlockElevatorServer;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.game.level.World;

public class Elevator {
    public static void jump(World world, int x, int y, int z, EntityPlayer player) {
		for (int y2 = y+3; y2 < Math.min(y + BlockElevatorServer.config.maxYStep + 1, world.highestY); y2++){
			if (BlockElevatorServer.config.elevatorBlockIDs.contains(world.getBlockId(x, y2, z))){
				teleport(x + 0.5, y2+1, z+0.5, player);
				world.playAuxSFX(2020, x, y2, z, 0); // random.pop
				return;
			}
		}
	}
	public static void sneak(World world, int x, int y, int z, EntityPlayer player) {
		for (int y2 = y-1; y2 > Math.max(y - BlockElevatorServer.config.maxYStep - 1, 0); y2--){
			int blockID = world.getBlockId(x, y2, z);
			if (BlockElevatorServer.config.elevatorBlockIDs.contains(blockID)){
				teleport(x + 0.5, y2+1, z + 0.5, player);
				world.playAuxSFX(900, x, y2, z, blockID); // Step sound of the block below
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