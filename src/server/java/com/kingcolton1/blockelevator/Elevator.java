package com.kingcolton1.blockelevator;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import net.minecraft.src.game.entity.player.EntityPlayer;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.game.level.World;
import net.minecraft.src.game.block.Block;

public class Elevator {
	public static String scanBeforeJump(World world, int x, int y, int z, EntityPlayer player) {
		for (int y2 = y+3; y2 < Math.min(y + BlockElevatorServer.config.maxYStep + 1, world.highestY); y2++) {
			if (BlockElevatorServer.config.elevatorBlockIDs.contains(world.getBlockId(x, y2, z))){
				if (checkForAirAndBlock(world, x, y2, z)) {
					teleport(x + 0.5, y2+1, z+0.5, player);
					world.playAuxSFX(2020, x, y2, z, 0); // random.pop
					return "success";
				} else {
					return "blocked";
				}
			} else {
				continue;
			}
		}
		return "noBlock";
	}

	public static String scanBeforeSneak(World world, int x, int y, int z, EntityPlayer player) {
		for (int y2 = y-1; y2 > Math.max(y - BlockElevatorServer.config.maxYStep - 1, 0); y2--) {
			int blockID = world.getBlockId(x, y2, z);
			if (BlockElevatorServer.config.elevatorBlockIDs.contains(blockID)){
				if (checkForAirAndBlock(world, x, y2, z)) {
					teleport(x + 0.5, y2+1, z + 0.5, player);
					world.playAuxSFX(900, x, y2, z, blockID); // Step sound of the block below
					return "success";
				} else {
					return "blocked";
				}
			} else {
				continue;
			}
		}
		return "noBlock";
	}

    public static void jump(World world, int x, int y, int z, EntityPlayer player, NetworkPlayer playerChat) {
		String scanBeforeJump = scanBeforeJump(world, x, y, z, player);
		
		if (scanBeforeJump == "success") {
			return;
		} else if (scanBeforeJump == "blocked") {
			playerChat.displayChatMessage(ChatColors.RED + "Something is blocking a elevator block above you!");
			return;
		} else {
			playerChat.displayChatMessage(ChatColors.RED + "There is no elevator block above you. Try shift to go down.");
			return;
		}
	}

	public static void sneak(World world, int x, int y, int z, EntityPlayer player, NetworkPlayer playerChat) {
		String scanBeforeSneak = scanBeforeSneak(world, x, y, z, player);

		if (scanBeforeSneak == "success") {
			return;
		} else if (scanBeforeSneak == "blocked") {
			playerChat.displayChatMessage(ChatColors.RED + "Something is blocking a elevator block below you!");
			return;
		} else {
			playerChat.displayChatMessage(ChatColors.RED + "There is no elevator block below you. Try jump to go up.");
			return;
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

	public static boolean checkForAirAndBlock(World world, int x, int y2, int z) {
		boolean airBlockCheckTop = world.isAirBlock(x, y2 + 2, z);
		boolean airBlockCheckBot = world.isAirBlock(x, y2 + 1, z);
		int getBlockIdCoordTop = world.getBlockId(x, y2 + 2, z);
		int getBlockIdCoordBot = world.getBlockId(x, y2 + 1, z);

		int[] listOfNonBlockingBlock = {Block.signWall.blockID, Block.signPost.blockID, Block.button.blockID, Block.trapdoor.blockID, Block.torch.blockID, Block.stickyTorch.blockID};
		int iTop = 0;
		int iBot = 0;

		for (int i : listOfNonBlockingBlock) {
			if (getBlockIdCoordTop == i) {
				iTop = i;
				break;
			}
		}

		for (int i : listOfNonBlockingBlock) {
			if (getBlockIdCoordBot == i) {
				iBot = i;
				break;
			}
		}

		return (airBlockCheckBot && airBlockCheckTop) || (getBlockIdCoordTop == iTop && airBlockCheckTop) || (getBlockIdCoordBot == iBot && airBlockCheckBot) || (getBlockIdCoordTop == iTop && getBlockIdCoordBot == iBot);
    }
}
