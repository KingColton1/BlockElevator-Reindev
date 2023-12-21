package com.kingcolton1.blockelevator;

import com.fox2code.foxloader.loader.ServerMod;
import net.minecraft.src.game.block.Block;

import com.kingcolton1.blockelevator.API.AssignBlock;

public class BlockElevatorServer extends BlockElevator implements ServerMod {
    public static AssignBlock elevator;

    @Override
    public void onInit() {
        System.out.println("BlockElevator initialized");

        elevator = new AssignBlock(Block.blockGold);
        System.out.println(elevator.registerBlockId());
    }
}
