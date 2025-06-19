package com.kingcolton1.blockelevator.commands;

import net.minecraft.common.command.ICommandListener;
import net.minecraft.common.util.ChatColors;
import net.minecraft.common.entity.player.EntityPlayer;
import com.kingcolton1.blockelevator.BlockElevator;
import net.minecraft.common.command.Command;
import net.minecraft.common.block.Block;

// NOTE: Block.blocksList is removed, apparently. We need to find another way to index the list of block IDs.
public class ElevatorBlocks extends Command {
    public ElevatorBlocks(){
        super("elevatorblocks", true, false);
    }

    @Override
    public void printHelpInformation(ICommandListener commandExecutor) {

    }

    @Override
    public String commandSyntax() {
        return ChatColors.YELLOW + "/elevatorblocks <add/remove> <block name or id>";
    }

    public void onExecute(String[] args, EntityPlayer commandExecutor) {
        if (args.length <= 1){
            commandExecutor.addChatMessage(ChatColors.GREEN + "Usage: " + commandSyntax());

            if (!BlockElevator.config.elevatorBlockIDs.isEmpty()) {
                commandExecutor.addChatMessage(ChatColors.GREEN + "Elevator blocks:");
            } else{
                commandExecutor.addChatMessage(ChatColors.YELLOW + "There are no elevator blocks!");
                commandExecutor.addChatMessage(ChatColors.YELLOW +  "Type " + ChatColors.RESET + "/elevatorblocks add gold_block" + ChatColors.YELLOW + " to add one!");
            }

            //for (int blockID : BlockElevator.config.elevatorBlockIDs)
            //    commandExecutor.addChatMessage(Block.blocksList[blockID].getBlockName().substring(5) + ChatColors.GREEN + " (id " + ChatColors.RESET + blockID + ChatColors.GREEN + ")"); // .substring(5) removes "tile." from the beginning

            return;
        }

        if (!commandExecutor.isOp()){
            commandExecutor.addChatMessage(ChatColors.RED + "Operator-only command");
            return;
        }

        if (args.length < 3){
            commandExecutor.addChatMessage(commandSyntax());
            return;
        }

        final String addOrRemove = args[1];
        final String blockNameOrID = args[2];

        if (addOrRemove.isEmpty() || blockNameOrID.isEmpty()){
            commandExecutor.addChatMessage(ChatColors.RED + "You can't specify an empty action or block name/id");
            commandExecutor.addChatMessage(ChatColors.RED + "You probably typed 2 spaces somewhere in the command!");
            return;
        }

        int blockID;
        try {
            blockID = Integer.parseInt(blockNameOrID);
        } catch(NumberFormatException e){
            try {
                blockID = Integer.parseInt(Block.getBlockByName(blockNameOrID)); // Why does this return an integer as a string?
            } catch(NumberFormatException e2){
                commandExecutor.addChatMessage(ChatColors.RED + "Failed to find a block named \"" + ChatColors.RESET + blockNameOrID + ChatColors.RED + "\"");
                return;
            }
        }

        /*
        Block block;
        try {
            block = Block.blocksList[blockID];
        } catch (ArrayIndexOutOfBoundsException e) {
            commandExecutor.displayChatMessage(ChatColors.RED + "Invalid block ID " + ChatColors.RESET + blockNameOrID);
            return;
        }*/

        if (addOrRemove.equalsIgnoreCase("add")){
            if (BlockElevator.config.elevatorBlockIDs.contains(blockID)){
                //commandExecutor.addChatMessage(ChatColors.RED + "The block " + ChatColors.RESET + block.getBlockName().substring(5) + ChatColors.RED + " (id " + ChatColors.RESET + blockID + ChatColors.RED + ") is already an elevator block");
                return;
            }

            BlockElevator.config.elevatorBlockIDs.add(blockID);
            //commandExecutor.addChatMessage(ChatColors.GREEN + "Added " + ChatColors.RESET + block.getBlockName().substring(5) + ChatColors.GREEN + " (id " + ChatColors.RESET + blockID + ChatColors.GREEN + ") as an elevator block!");
        } else if (addOrRemove.equalsIgnoreCase("remove")){
            boolean removed = BlockElevator.config.elevatorBlockIDs.remove((Object)blockID);
            //if (removed)
            //    commandExecutor.addChatMessage(ChatColors.GREEN + "Removed " + ChatColors.RESET + block.getBlockName().substring(5) + ChatColors.GREEN + " (id " + ChatColors.RESET + blockID + ChatColors.GREEN + ") as an elevator block!");
            //else
            //    commandExecutor.addChatMessage(ChatColors.YELLOW + "The block " + ChatColors.RESET + block.getBlockName().substring(5) + ChatColors.YELLOW + " (id " + ChatColors.RESET + blockID + ChatColors.YELLOW + ") isn't an elevator block, nothing to remove");
        } else {
            commandExecutor.addChatMessage(commandSyntax());
        }
    }
}
