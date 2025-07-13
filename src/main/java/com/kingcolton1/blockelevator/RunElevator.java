package com.kingcolton1.blockelevator;

import net.minecraft.common.util.ChatColors;
import net.minecraft.common.entity.player.EntityPlayer;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.client.player.EntityPlayerSP;
import net.minecraft.common.world.World;
import net.minecraft.common.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunElevator {
	private static final Logger log = LoggerFactory.getLogger(RunElevator.class);

	public static String scanBeforeJump(World world, int x, int y, int z, EntityPlayer player) {
		for (int y2 = y + 3; y2 < Math.min(y + BlockElevator.config.maxYStep + 1, world.highestY); y2++) {
			if (BlockElevator.config.elevatorBlockIDs.contains(world.getBlockId(x, y2, z))){
				if (checkForAirAndBlock(world, x, y2, z)) {
					teleport(x + 0.5, y2 + 3, z + 0.5, player);
					world.playAuxSFX(2020, x, y2, z, 0); // random.pop
					return "success";
				} else {
					return "blocked";
				}
			}
		}
		return "noBlock";
	}

	public static String scanBeforeSneak(World world, int x, int y, int z, EntityPlayer player) {
		for (int y2 = y - 1; y2 > Math.max(y - BlockElevator.config.maxYStep - 1, 0); y2--) {
			int blockID = world.getBlockId(x, y2, z);

			if (BlockElevator.config.elevatorBlockIDs.contains(blockID)){
				if (checkForAirAndBlock(world, x, y2, z)) {
					teleport(x + 0.5, y2 + 3, z + 0.5, player);
					world.playAuxSFX(900, x, y2, z, blockID); // Step sound of the block below
					return "success";
				} else {
					return "blocked";
				}
			}
		}
		return "noBlock";
	}

    public static void jump(World world, int x, int y, int z, EntityPlayer player) {
		String scanBeforeJump = scanBeforeJump(world, x, y, z, player);

		if (scanBeforeJump.equals("success")) {
			return;
		} else if (scanBeforeJump.equals("blocked")) {
			player.addChatMessage(ChatColors.RED + "Something is blocking a elevator block above you!");
		} else {
			player.addChatMessage(ChatColors.RED + "There is no elevator block above you. Try shift to go down.");
		}
	}

	public static void sneak(World world, int x, int y, int z, EntityPlayer player) {
		String scanBeforeSneak = scanBeforeSneak(world, x, y, z, player);

		if (scanBeforeSneak.equals("success")) {
			return;
		} else if (scanBeforeSneak.equals("blocked")) {
			player.addChatMessage(ChatColors.RED + "Something is blocking a elevator block below you!");
		} else {
			player.addChatMessage(ChatColors.RED + "There is no elevator block below you. Try jump to go up.");
		}
	}

	public static void teleport(double x, double y, double z, EntityPlayer player) {
		if (checkServerOrClient(player).equals("server")) {
			log.info("Multiplayer? (EntityPlayerMP)");
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			playerMP.playerNetServerHandler.teleportTo(x, y, z, playerMP.rotationYaw, playerMP.rotationPitch);
		} else if (checkServerOrClient(player).equals("client")) {
			log.info("Single Player? (EntityPlayerSP)");
			EntityPlayerSP playerSP = (EntityPlayerSP)player;
			playerSP.teleportTo(x, y, z, playerSP.rotationYaw, playerSP.rotationPitch);
		}
	}

	public static String checkServerOrClient(EntityPlayer player) {
		if (player instanceof EntityPlayerMP) {
			return "server";
		} else if (player instanceof EntityPlayerSP) {
			return "client";
		}
		return "null";
	}

	public static boolean checkForAirAndBlock(World world, int x, int y2, int z) {
		boolean airBlockCheckTop = world.isAirBlock(x, y2 + 2, z);
		boolean airBlockCheckBot = world.isAirBlock(x, y2 + 1, z);
		int getBlockIdCoordTop = world.getBlockId(x, y2 + 2, z);
		int getBlockIdCoordBot = world.getBlockId(x, y2 + 1, z);

		int[] listOfNonBlockingBlock = {
				Blocks.WALL_SIGN.blockID,
				Blocks.SIGN_POST.blockID,
				Blocks.BRIMSTONE_BUTTON.blockID,
				Blocks.AZURE_BUTTON.blockID,
				Blocks.CHERRY_BUTTON.blockID,
				Blocks.EBONY_BUTTON.blockID,
				Blocks.FIR_BUTTON.blockID,
				Blocks.CRIMSON_BUTTON.blockID,
				Blocks.OAK_BUTTON.blockID,
				Blocks.SPRUCE_BUTTON.blockID,
				Blocks.STONE_BUTTON.blockID,
				Blocks.THISTLEWOOD_BUTTON.blockID,
				Blocks.THISTLEWOOD_TRAPDOOR.blockID,
				Blocks.AZURE_TRAPDOOR.blockID,
				Blocks.CHERRY_TRAPDOOR.blockID,
				Blocks.EBONY_TRAPDOOR.blockID,
				Blocks.FIR_TRAPDOOR.blockID,
				Blocks.CRIMSON_TRAPDOOR.blockID,
				Blocks.OAK_TRAPDOOR.blockID,
				Blocks.SPRUCE_TRAPDOOR.blockID,
				Blocks.IRON_TRAPDOOR.blockID,
				Blocks.TORCH.blockID,
				Blocks.STICKY_TORCH.blockID,
				Blocks.TORCH_PRICKLE.blockID,
				Blocks.TORCH_PRICKLE_FRUITING.blockID,
				Blocks.LEVER.blockID
		};
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

		return (airBlockCheckBot && airBlockCheckTop) || (getBlockIdCoordTop == iTop && airBlockCheckBot) || (getBlockIdCoordBot == iBot && airBlockCheckTop) || (getBlockIdCoordTop == iTop && getBlockIdCoordBot == iBot);
    }
}
