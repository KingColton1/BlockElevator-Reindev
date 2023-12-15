package com.kingcolton1.blockelevator;

import com.fox2code.foxloader.loader.ServerMod;
import com.kingcolton1.BlockElevatorFunc;

import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;

public class BlockElevatorServer extends BlockeElvator implements ServerMod {
    @Override
    public void onInit() {
        System.out.println("BlockElevator initialized");
        Block.blocksList[41] = null;
        Block.blocksList[41] = new BlockElevatorFunc(41, Material.iron);
    }
}
