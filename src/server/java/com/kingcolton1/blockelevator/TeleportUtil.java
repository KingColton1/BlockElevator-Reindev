package com.kingcolton1.blockelevator;

import java.util.LinkedList;

import com.kingcolton1.blockelevator.API.Block;
import com.kingcolton1.blockelevator.API.BlockFace;
import com.kingcolton1.blockelevator.API.Location;
import com.kingcolton1.blockelevator.API.Material;
import com.kingcolton1.blockelevator.API.Player;
import com.kingcolton1.blockelevator.API.Vector;
import com.kingcolton1.blockelevator.API.World;

public class TeleportUtil {
    public static boolean checkForTeleportSpace(Location loc) {
		final Block block = loc.getBlock();
		final Material mat = block.getType();
		if (mat.isSolid()) {
			return false;
		}
		final Block above = block.getRelative(BlockFace.UP);
		if (above.getType().isSolid()) {
			return false;
		}
		return true;
	}

	private static boolean tryToTeleportVertically(Player player, Location location, String reason) {
		Location loc = location.clone();
		loc.setX(Math.floor(loc.getX()) + 0.500000D);
		loc.setY(Math.floor(loc.getY()) + 0.02D);
		loc.setZ(Math.floor(loc.getZ()) + 0.500000D);
		final Location baseLoc = loc.clone();
		final World world = baseLoc.getWorld();
		// Check if teleportation here is viable
		boolean performTeleport = checkForTeleportSpace(loc);
		if (!performTeleport) {
			loc.setY(loc.getY() + 1.000000D);
			performTeleport = checkForTeleportSpace(loc);
		}
		if (performTeleport) {
			player.setVelocity(new Vector());
			player.teleport(loc);
		}
		loc = baseLoc.clone();
		// Create a sliding window of block types and track how many of those
		//  are solid. Keep fetching the block below the current block to move down.
		int air_count = 0;
		LinkedList<Material> air_window = new LinkedList<>();
		loc.setY((float)world.getMaxHeight() - 2);
		Block block = world.getBlockAt(loc);
		for (int i = 0; i < 4; ++i) {
			Material block_mat = block.getType();
			if (!block_mat.isSolid()) {
				++air_count;
			}
			air_window.addLast(block_mat);
			block = block.getRelative(BlockFace.DOWN);
		}
		// Now that the window is prepared, scan down the Y-axis.
		while (block.getY() >= block.getWorld().getMinHeight() + 1) {
			Material blockMat = block.getType();
			if (blockMat.isSolid()) {
				if (air_count == 4) {
					player.setVelocity(new Vector());
					loc = block.getLocation();
					loc.setX(Math.floor(loc.getX()) + 0.500000D);
					loc.setY(loc.getY() + 1.02D);
					loc.setZ(Math.floor(loc.getZ()) + 0.500000D);
					player.teleport(loc);
				}
			} else {
				++air_count;
			}
			air_window.addLast(blockMat);
			if (!air_window.removeFirst().isSolid()) {
				--air_count;
			}
			block = block.getRelative(BlockFace.DOWN);
		}
		return false;
	}
}
