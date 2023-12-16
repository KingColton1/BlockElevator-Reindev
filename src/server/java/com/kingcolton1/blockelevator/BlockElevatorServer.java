package com.kingcolton1.blockelevator;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.registry.*;
import com.kingcolton1.blockelevator.API.Listener;
import com.kingcolton1.blockelevator.API.Material;

public class BlockElevatorServer extends BlockElevator implements ServerMod {
    public static BlockElevatorBlock elevator;

    @Override
    public void onInit() {
        System.out.println("BlockElevator initialized");

        elevator = new BlockElevatorBlock(41, Material.blockGold);
    }
}
