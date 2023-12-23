package com.kingcolton1.blockelevator;

import com.fox2code.foxloader.loader.ServerMod;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;

import com.kingcolton1.blockelevator.API.AssignBlock;
import com.kingcolton1.blockelevator.API.ElevatorBlock;

public class BlockElevatorServer extends BlockElevator implements ServerMod {
    public static AssignBlock elevator;
    public static Block elevatorBlock;

    @Override
    public void onInit() {
        System.out.println("BlockElevator initialized");

        elevator = new AssignBlock(Block.blockGold);

        // I require assistance with this part as I am trying to initialize whatever block that is used in line 18.
        // It is supposed to be dynamic instead of hardcode so it allows server owner to choose a block to be used
        // as an elevator they want.
        // See more in KingColton1/BlockElevator/API/ElevatorBlock.java for another assistance task at line 23.
        Block.blocksList[elevator.getID()] = null;
        Block.blocksList[elevator.getID()] = elevatorBlock = new ElevatorBlock(elevator.getID(), Material.iron);
    }
}
