package com.kingcolton1.blockelevator.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kingcolton1.blockelevator.BlockElevatorServer;
import net.minecraft.src.game.block.Block;

public class ElevatorBlocks extends CommandCompat {
    public ElevatorBlocks(){
        super("elevatorblocks", false);
    }

    @Override
    public String commandSyntax() {
        return ChatColors.YELLOW + "/elevatorblocks <add/remove> <block name or id>";
    }

    @Override
    public void onExecute(String[] args, NetworkPlayer commandExecutor) {
        if (args.length <= 1){
            commandExecutor.displayChatMessage(ChatColors.GREEN + "Usage: " + commandSyntax());

            if (!BlockElevatorServer.config.elevatorBlockIDs.isEmpty()) {
                commandExecutor.displayChatMessage(ChatColors.GREEN + "Elevator blocks:");
            } else{
                commandExecutor.displayChatMessage(ChatColors.YELLOW + "There are no elevator blocks!");
                commandExecutor.displayChatMessage(ChatColors.YELLOW +  "Type " + ChatColors.RESET + "/elevatorblocks add gold_block" + ChatColors.YELLOW + " to add one!");
            }

            for (int blockID : BlockElevatorServer.config.elevatorBlockIDs)
                commandExecutor.displayChatMessage(Block.blocksList[blockID].getBlockName().substring(5) + ChatColors.GREEN + " (id " + ChatColors.RESET + blockID + ChatColors.GREEN + ")"); // .substring(5) removes "tile." from the beginning

            return;
        }

        if (!commandExecutor.isOperator()){
            commandExecutor.displayChatMessage(ChatColors.RED + "Operator-only command");
            return;
        }

        if (args.length < 3){
            commandExecutor.displayChatMessage(commandSyntax());
            return;
        }

        final String addOrRemove = args[1];
        final String blockNameOrID = args[2];

        if (addOrRemove.isEmpty() || blockNameOrID.isEmpty()){
            commandExecutor.displayChatMessage(ChatColors.RED + "You can't specify an empty action or block name/id");
            commandExecutor.displayChatMessage(ChatColors.RED + "You probably typed 2 spaces somewhere in the command!");
            return;
        }

        int blockID;
        try {
            blockID = Integer.parseInt(blockNameOrID);
        } catch(NumberFormatException e){
            try {
                blockID = Integer.parseInt(Block.getBlockByName(blockNameOrID)); // Why does this return an integer as a string?
            } catch(NumberFormatException e2){
                commandExecutor.displayChatMessage(ChatColors.RED + "Failed to find a block named \"" + ChatColors.RESET + blockNameOrID + ChatColors.RED + "\"");
                return;
            }
        }

        Block block;
        try {
            block = Block.blocksList[blockID];
        } catch (ArrayIndexOutOfBoundsException e) {
            commandExecutor.displayChatMessage(ChatColors.RED + "Invalid block ID " + ChatColors.RESET + blockNameOrID);
            return;
        }

        if (addOrRemove.equals("add")){
            if (BlockElevatorServer.config.elevatorBlockIDs.contains(blockID)){
                commandExecutor.displayChatMessage(ChatColors.RED + "The block " + ChatColors.RESET + block.getBlockName().substring(5) + ChatColors.RED + " (id " + ChatColors.RESET + blockID + ChatColors.RED + ") is already an elevator block");
                return;
            }

            BlockElevatorServer.config.elevatorBlockIDs.add(blockID);
            commandExecutor.displayChatMessage(ChatColors.GREEN + "Added " + ChatColors.RESET + block.getBlockName().substring(5) + ChatColors.GREEN + " (id " + ChatColors.RESET + blockID + ChatColors.GREEN + ") as an elevator block!");
        } else if (addOrRemove.equals("remove")){
            boolean removed = BlockElevatorServer.config.elevatorBlockIDs.remove((Object)blockID);
            if (removed)
                commandExecutor.displayChatMessage(ChatColors.GREEN + "Removed " + ChatColors.RESET + block.getBlockName().substring(5) + ChatColors.GREEN + " (id " + ChatColors.RESET + blockID + ChatColors.GREEN + ") as an elevator block!");
            else
                commandExecutor.displayChatMessage(ChatColors.YELLOW + "The block " + ChatColors.RESET + block.getBlockName().substring(5) + ChatColors.YELLOW + " (id " + ChatColors.RESET + blockID + ChatColors.YELLOW + ") isn't an elevator block, nothing to remove");
        } else {
            commandExecutor.displayChatMessage(commandSyntax());
        }
    }
}
