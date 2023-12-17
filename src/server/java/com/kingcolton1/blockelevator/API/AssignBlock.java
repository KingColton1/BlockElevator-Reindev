package com.kingcolton1.blockelevator.API;

import net.minecraft.src.game.block.Block;

public class AssignBlock {
    private int id = 0;
    private Block material;

    public AssignBlock(int id, Block material) {
        this.id = id;
        this.material = material;
    }

    public int getID() {
        return this.id;
    }

    public Block getMaterial() {
        return this.material;
    }
}
