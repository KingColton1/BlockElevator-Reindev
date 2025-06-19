package com.kingcolton1.blockelevator.commands;

import net.minecraft.common.command.Command;
import net.minecraft.common.command.ICommandListener;
import net.minecraft.common.util.ChatColors;
import net.minecraft.common.entity.player.EntityPlayer;
import com.kingcolton1.blockelevator.BlockElevator;

public class Elevator extends Command {
    public Elevator() {
        super("elevator", true, false);
    }

    @Override
    public void printHelpInformation(ICommandListener commandExecutor) {

    }

    @Override
    public String commandSyntax() {
        return ChatColors.YELLOW + "/elevator <name> <value>";
    }

    public void onExecute(String[] args, EntityPlayer commandExecutor) {
        if (args.length <= 1){
            commandExecutor.addChatMessage(ChatColors.GREEN + "Usage: " + commandSyntax());

            commandExecutor.addChatMessage(ChatColors.GREEN + "BlockElevator config:");
            commandExecutor.addChatMessage(BlockElevator.config.enabledConfigName + " = " + ChatColors.GRAY + BlockElevator.config.enabled);
            commandExecutor.addChatMessage(BlockElevator.config.coolDownTicksConfigName + " = " + ChatColors.GRAY + BlockElevator.config.coolDownTicks);
            commandExecutor.addChatMessage(BlockElevator.config.maxYStepConfigName + " = " + ChatColors.GRAY + BlockElevator.config.maxYStep);
            commandExecutor.addChatMessage(BlockElevator.config.dYRequiredForJumpConfigName + " = " + ChatColors.GRAY + BlockElevator.config.dYRequiredForJump);
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

        String name = args[1];
        String value = args[2];

        if (name.isEmpty() || value.isEmpty()) {
            commandExecutor.addChatMessage(ChatColors.RED + "You can't specify an empty name or value");
            commandExecutor.addChatMessage(ChatColors.RED + "You probably typed 2 spaces somewhere in the command!");
            return;
        }

        try {
            final String isNow = ChatColors.GREEN + " is now " + ChatColors.GRAY;
            // Pretty messy
            if (name.equalsIgnoreCase(BlockElevator.config.enabledConfigName)){
                BlockElevator.config.enabled = Boolean.parseBoolean(value);
                commandExecutor.addChatMessage(BlockElevator.config.enabledConfigName + isNow + BlockElevator.config.enabled);
            } else if (name.equalsIgnoreCase(BlockElevator.config.coolDownTicksConfigName)) {
                BlockElevator.config.coolDownTicks = Integer.parseInt(value);
                commandExecutor.addChatMessage(BlockElevator.config.coolDownTicksConfigName + isNow + BlockElevator.config.coolDownTicks);
            } else if (name.equalsIgnoreCase(BlockElevator.config.maxYStepConfigName)) {
                if (Integer.parseInt(value) < 3){
                    commandExecutor.addChatMessage(ChatColors.YELLOW + BlockElevator.config.maxYStepConfigName + " Can't be set lower than 3.");
                    return;
                }

                BlockElevator.config.maxYStep = Integer.parseInt(value);
                commandExecutor.addChatMessage(BlockElevator.config.maxYStepConfigName + isNow + BlockElevator.config.maxYStep);
            } else if (name.equalsIgnoreCase(BlockElevator.config.dYRequiredForJumpConfigName)) {
                BlockElevator.config.dYRequiredForJump = Double.parseDouble(value);
                commandExecutor.addChatMessage(BlockElevator.config.dYRequiredForJumpConfigName + isNow + BlockElevator.config.dYRequiredForJump);
            }
        } catch(NumberFormatException e){
            commandExecutor.addChatMessage(ChatColors.RED + "Value is not a valid number");
        }
    }
}
