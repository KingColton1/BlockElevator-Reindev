package com.kingcolton1.blockelevator;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.game.level.World;
import net.minecraft.src.server.physics.AxisAlignedBB;

public class BlockElevatorFunc extends Block {
    public BlockElevatorFunc(int id, Material material) {
        super(id, material);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(int x, int y, int z) {
        float f = 0.075F;
        return AxisAlignedBB.getBoundingBoxFromPool(x, y, z, x + 1, (float)(y + 1) - f, z + 1);
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public static Block blockName = Block.blocksList[41];

    public static void jump(World world, int x, int y, int z, EntityPlayerMP player) {
		for (int y2 = y + 1; y2 < 255; y2++) {
            if (world.getBlockId(x, y, z) == blockName.blockID) {
                player.playerNetServerHandler.teleportTo(x + 0.5, y2 + 1, z + 0.5, player.rotationYaw, player.rotationPitch);
				break;
            }

			else if (world.getBlockId(x, y2, z) != 0) {
				break;
			}
		}
	}

    public static void sneak(World world, int x, int y, int z, EntityPlayerMP player) {
        if (!player.isSneaking()) {
            return;
        }

		for (int y2 = y - 1; y2 > 0; y2--) {
			if (world.getBlockId(x, y2, z) == blockName.blockID) {
				player.playerNetServerHandler.teleportTo(x + 0.5, y2 + 1, z + 0.5, player.rotationYaw, player.rotationPitch);
				break;
			}
			else if (world.getBlockId(x, y2, z) != 0) {
				break;
			}
		}
	}
}
