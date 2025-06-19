package com.kingcolton1.blockelevator.commands;

import net.minecraft.common.command.Command;
import net.minecraft.common.command.ICommandListener;
import net.minecraft.common.util.ChatColors;
import com.kingcolton1.blockelevator.BlockElevator;

public class Elevator extends Command {
    public Elevator() {
        super("elevator", true, false);
    }

    @Override
    public void printHelpInformation(ICommandListener commandExecutor) {}

    @Override
    public String commandSyntax() {
        return ChatColors.YELLOW + "/elevator <name> <value>";
    }

    @Override
    public void onExecute(String[] args, ICommandListener commandExecutor) {
        if (args.length <= 1){
            commandExecutor.broadcastMessage(ChatColors.GREEN + "Usage: " + commandSyntax());

            commandExecutor.broadcastMessage(ChatColors.GREEN + "BlockElevator config:");
            commandExecutor.broadcastMessage(BlockElevator.config.enabledConfigName + " = " + ChatColors.GRAY + BlockElevator.config.enabled);
            commandExecutor.broadcastMessage(BlockElevator.config.coolDownTicksConfigName + " = " + ChatColors.GRAY + BlockElevator.config.coolDownTicks);
            commandExecutor.broadcastMessage(BlockElevator.config.maxYStepConfigName + " = " + ChatColors.GRAY + BlockElevator.config.maxYStep);
            commandExecutor.broadcastMessage(BlockElevator.config.dYRequiredForJumpConfigName + " = " + ChatColors.GRAY + BlockElevator.config.dYRequiredForJump);
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

        String name = args[1];
        String value = args[2];

        if (name.isEmpty() || value.isEmpty()) {
            commandExecutor.broadcastMessage(ChatColors.RED + "You can't specify an empty name or value");
            commandExecutor.broadcastMessage(ChatColors.RED + "You probably typed 2 spaces somewhere in the command!");
            return;
        }

        try {
            final String isNow = ChatColors.GREEN + " is now " + ChatColors.GRAY;
            // Pretty messy
            if (name.equalsIgnoreCase(BlockElevator.config.enabledConfigName)){
                BlockElevator.config.enabled = Boolean.parseBoolean(value);
                commandExecutor.broadcastMessage(BlockElevator.config.enabledConfigName + isNow + BlockElevator.config.enabled);
            } else if (name.equalsIgnoreCase(BlockElevator.config.coolDownTicksConfigName)) {
                BlockElevator.config.coolDownTicks = Integer.parseInt(value);
                commandExecutor.broadcastMessage(BlockElevator.config.coolDownTicksConfigName + isNow + BlockElevator.config.coolDownTicks);
            } else if (name.equalsIgnoreCase(BlockElevator.config.maxYStepConfigName)) {
                if (Integer.parseInt(value) < 3){
                    commandExecutor.broadcastMessage(ChatColors.YELLOW + BlockElevator.config.maxYStepConfigName + " Can't be set lower than 3.");
                    return;
                }

                BlockElevator.config.maxYStep = Integer.parseInt(value);
                commandExecutor.broadcastMessage(BlockElevator.config.maxYStepConfigName + isNow + BlockElevator.config.maxYStep);
            } else if (name.equalsIgnoreCase(BlockElevator.config.dYRequiredForJumpConfigName)) {
                BlockElevator.config.dYRequiredForJump = Double.parseDouble(value);
                commandExecutor.broadcastMessage(BlockElevator.config.dYRequiredForJumpConfigName + isNow + BlockElevator.config.dYRequiredForJump);
            }
        } catch(NumberFormatException e){
            commandExecutor.broadcastMessage(ChatColors.RED + "Value is not a valid number");
        }
    }
}
