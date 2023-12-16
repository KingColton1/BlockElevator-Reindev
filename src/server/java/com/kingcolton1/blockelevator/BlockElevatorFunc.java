package com.kingcolton1.blockelevator;

import com.kingcolton1.blockelevator.API.Block;
import com.kingcolton1.blockelevator.API.BlockFace;
import com.kingcolton1.blockelevator.API.Listener;
import com.kingcolton1.blockelevator.API.Location;
import com.kingcolton1.blockelevator.API.Material;
import com.kingcolton1.blockelevator.API.Player;
import com.kingcolton1.blockelevator.API.PlayerJumpEvent;
import com.kingcolton1.blockelevator.API.PlayerToggleSneakEvent;


public class BlockElevatorFunc implements Listener {
    public BlockElevatorBlock elevatorBlock;

    private boolean doTeleport(Block source, Player player, int y) {
        Block target = source.getWorld().getBlockAt(source.getX(), y, source.getZ());
        if (target.getType() != elevatorBlock.getMaterial()) {
            return false;
        }
        if (!TeleportUtil.checkForTeleportSpace(target.getRelative(BlockFace.UP).getLocation())) {
            return false;
        }
        Location adjustedLocation = target.getLocation().clone();
        adjustedLocation.add(0.5, 1.02, 0.5);
        adjustedLocation.setYaw(player.getLocation().getYaw());
        adjustedLocation.setPitch(player.getLocation().getPitch());
        player.teleport(adjustedLocation);
        return true;
    }

    public void elevatorBlockSneak(PlayerToggleSneakEvent eventSneak) {
        Block below = eventSneak.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
        if (below.getType() != elevatorBlock.getMaterial()) {
            return;
        }
        if (!eventSneak.isSneaking()) {
            return;
        }
        for (int y = (below.getY() - 1); y > below.getWorld().getMinHeight(); y--) {
            if (doTeleport(below, eventSneak.getPlayer(), y)) {
                return;
            }
        }

        //String message = String.format("No %s block to teleport you down to. Jump to teleport up instead.", this.elevatorBlock);

        //eventSneak.getPlayer().sendMessage(Component.text(message).color(NamedTextColor.RED));
    }

    public void elevatorBlockJump(PlayerJumpEvent eventJump) {
        Block below = eventJump.getFrom().getBlock().getRelative(BlockFace.DOWN);
        if (below.getType() != elevatorBlock.getMaterial()) {
            return;
        }
        for (int y = below.getY() + 1; y <= below.getWorld().getMaxHeight(); y++) {
            if (doTeleport(below, eventJump.getPlayer(), y)) {
                return;
            }
        }

        //String message = String.format("No %s block to teleport you up to. Sneak to teleport down instead.", this.elevatorBlock);

        //eventJump.getPlayer().sendMessage(Component.text(message).color(NamedTextColor.RED));
    }
}
