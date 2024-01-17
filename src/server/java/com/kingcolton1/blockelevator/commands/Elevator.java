package com.kingcolton1.blockelevator.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kingcolton1.blockelevator.BlockElevatorServer;

public class Elevator extends CommandCompat {
    public Elevator(){
        super("elevator", false);
    }

    @Override
    public String commandSyntax() {
        return ChatColors.YELLOW + "/elevator <name> <value>";
    }

    @Override
    public void onExecute(String[] args, NetworkPlayer commandExecutor) {
        if (args.length <= 1){
            commandExecutor.displayChatMessage(ChatColors.GREEN + "Usage: " + commandSyntax());

            commandExecutor.displayChatMessage(ChatColors.GREEN + "BlockElevator config:");
            commandExecutor.displayChatMessage(BlockElevatorServer.config.enabledConfigName + " = " + ChatColors.GRAY + BlockElevatorServer.config.enabled);
            commandExecutor.displayChatMessage(BlockElevatorServer.config.coolDownTicksConfigName + " = " + ChatColors.GRAY + BlockElevatorServer.config.coolDownTicks);
            commandExecutor.displayChatMessage(BlockElevatorServer.config.maxYStepConfigName + " = " + ChatColors.GRAY + BlockElevatorServer.config.maxYStep);
            commandExecutor.displayChatMessage(BlockElevatorServer.config.dYRequiredForJumpConfigName + " = " + ChatColors.GRAY + BlockElevatorServer.config.dYRequiredForJump);
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

        String name = args[1];
        String value = args[2];

        if (name.isEmpty() || value.isEmpty()) {
            commandExecutor.displayChatMessage(ChatColors.RED + "You can't specify an empty name or value");
            commandExecutor.displayChatMessage(ChatColors.RED + "You probably typed 2 spaces somewhere in the command!");
            return;
        }

        try {
            final String isNow = ChatColors.GREEN + " is now " + ChatColors.GRAY;
            // Pretty messy
            if (name.equalsIgnoreCase(BlockElevatorServer.config.enabledConfigName)){
                BlockElevatorServer.config.enabled = Boolean.parseBoolean(value);
                commandExecutor.displayChatMessage(BlockElevatorServer.config.enabledConfigName + isNow + BlockElevatorServer.config.enabled);
            } else if (name.equalsIgnoreCase(BlockElevatorServer.config.coolDownTicksConfigName)) {
                BlockElevatorServer.config.coolDownTicks = Integer.parseInt(value);
                commandExecutor.displayChatMessage(BlockElevatorServer.config.coolDownTicksConfigName + isNow + BlockElevatorServer.config.coolDownTicks);
            } else if (name.equalsIgnoreCase(BlockElevatorServer.config.maxYStepConfigName)) {
                if (Integer.parseInt(value) < 3){
                    commandExecutor.displayChatMessage(ChatColors.YELLOW + BlockElevatorServer.config.maxYStepConfigName + " Can't be set lower than 3.");
                    return;
                }

                BlockElevatorServer.config.maxYStep = Integer.parseInt(value);
                commandExecutor.displayChatMessage(BlockElevatorServer.config.maxYStepConfigName + isNow + BlockElevatorServer.config.maxYStep);
            } else if (name.equalsIgnoreCase(BlockElevatorServer.config.dYRequiredForJumpConfigName)) {
                BlockElevatorServer.config.dYRequiredForJump = Double.parseDouble(value);
                commandExecutor.displayChatMessage(BlockElevatorServer.config.dYRequiredForJumpConfigName + isNow + BlockElevatorServer.config.dYRequiredForJump);
            }
        } catch(NumberFormatException e){
            commandExecutor.displayChatMessage(ChatColors.RED + "Value is not a valid number");
        }
    }
}
