package com.kingcolton1.blockelevator.commands;

import net.minecraft.common.command.ICommandListener;
import net.minecraft.common.util.ChatColors;
import com.kingcolton1.blockelevator.BlockElevator;
import com.kingcolton1.blockelevator.BlocksList;
import net.minecraft.common.command.Command;
import net.minecraft.common.block.Block;

// NOTE: Block.blocksList is removed, apparently. We need to find another way to index the list of block IDs.
public class ElevatorBlocks extends Command {
    public ElevatorBlocks(){
        super("elevatorblocks", true, false);
    }

    @Override
    public void printHelpInformation(ICommandListener commandExecutor) {}

    @Override
    public String commandSyntax() {
        return ChatColors.YELLOW + "/elevatorblocks <add/remove> <block name or id>";
    }

    @Override
    public void onExecute(String[] args, ICommandListener commandExecutor) {
        BlocksList blocksList = new BlocksList();

        if (args.length <= 1){
            commandExecutor.broadcastMessage(ChatColors.GREEN + "Usage: " + commandSyntax());

            if (!BlockElevator.config.elevatorBlockIDs.isEmpty()) {
                commandExecutor.broadcastMessage(ChatColors.GREEN + "Elevator blocks:");
            } else{
                commandExecutor.broadcastMessage(ChatColors.YELLOW + "There are no elevator blocks!");
                commandExecutor.broadcastMessage(ChatColors.YELLOW +  "Type " + ChatColors.RESET + "/elevatorblocks add gold_block" + ChatColors.YELLOW + " to add one!");
            }

            for (int blockID : BlockElevator.config.elevatorBlockIDs) {
                commandExecutor.broadcastMessage(blocksList.getBlockName(blockID, 5) + ChatColors.GREEN + " (id " + ChatColors.RESET + blockID + ChatColors.GREEN + ")"); // .substring(5) removes "tile." from the beginning
            }
            return;
        }

        if (!commandExecutor.isOp()){
            commandExecutor.broadcastMessage(ChatColors.RED + "Operator-only command");
            return;
        }

        if (args.length < 3){
            commandExecutor.broadcastMessage(commandSyntax());
            return;
        }

        final String addOrRemove = args[1];
        final String blockNameOrID = args[2];

        if (addOrRemove.isEmpty() || blockNameOrID.isEmpty()){
            commandExecutor.broadcastMessage(ChatColors.RED + "You can't specify an empty action or block name/id");
            commandExecutor.broadcastMessage(ChatColors.RED + "You probably typed 2 spaces somewhere in the command!");
            return;
        }

        int blockID;
        try {
            blockID = Integer.parseInt(blockNameOrID);
        } catch(NumberFormatException e){
            try {
                blockID = Integer.parseInt(Block.getBlockByName(blockNameOrID)); // Why does this return an integer as a string?
            } catch(NumberFormatException e2){
                commandExecutor.broadcastMessage(ChatColors.RED + "Failed to find a block named \"" + ChatColors.RESET + blockNameOrID + ChatColors.RED + "\"");
                return;
            }
        }

        try {
            blocksList.checkBlockId(blockID);
        } catch (ArrayIndexOutOfBoundsException e) {
            commandExecutor.broadcastMessage(ChatColors.RED + "Invalid block ID " + ChatColors.RESET + blockNameOrID);
            return;
        }

        if (addOrRemove.equalsIgnoreCase("add")){
            if (BlockElevator.config.elevatorBlockIDs.contains(blockID)){
                commandExecutor.broadcastMessage(ChatColors.RED + "The block " + ChatColors.RESET + blocksList.getBlockName(blockID, 5) + ChatColors.RED + " (id " + ChatColors.RESET + blockID + ChatColors.RED + ") is already an elevator block");
                return;
            }

            BlockElevator.config.elevatorBlockIDs.add(blockID);
            commandExecutor.broadcastMessage(ChatColors.GREEN + "Added " + ChatColors.RESET + blocksList.getBlockName(blockID, 5) + ChatColors.GREEN + " (id " + ChatColors.RESET + blockID + ChatColors.GREEN + ") as an elevator block!");
        } else if (addOrRemove.equalsIgnoreCase("remove")){
            boolean removed = BlockElevator.config.elevatorBlockIDs.remove((Object)blockID);
            if (removed)
                commandExecutor.broadcastMessage(ChatColors.GREEN + "Removed " + ChatColors.RESET + blocksList.getBlockName(blockID, 5) + ChatColors.GREEN + " (id " + ChatColors.RESET + blockID + ChatColors.GREEN + ") as an elevator block!");
            else
                commandExecutor.broadcastMessage(ChatColors.YELLOW + "The block " + ChatColors.RESET + blocksList.getBlockName(blockID, 5) + ChatColors.YELLOW + " (id " + ChatColors.RESET + blockID + ChatColors.YELLOW + ") isn't an elevator block, nothing to remove");
        } else {
            commandExecutor.broadcastMessage(commandSyntax());
        }
    }
}
