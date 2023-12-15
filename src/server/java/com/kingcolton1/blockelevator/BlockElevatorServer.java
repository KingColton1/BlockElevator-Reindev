package com.kingcolton1.blockelevator;

import com.fox2code.foxloader.loader.ServerMod;

public class BlockElevatorServer extends BlockElevator implements ServerMod {
    @Override
    public void onInit() {
        System.out.println("BlockElevator initialized");
    }
}
