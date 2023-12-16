package com.kingcolton1.blockelevator;

import com.kingcolton1.blockelevator.API.Material;

public class BlockElevatorBlock {
    private int id = 0;
    private Material material;

    public BlockElevatorBlock(int id, Material material) {
        this.id = id;
        this.material = material;
    }

    public int getID() {
        return this.id;
    }

    public Material getMaterial() {
        return this.material;
    }
}
