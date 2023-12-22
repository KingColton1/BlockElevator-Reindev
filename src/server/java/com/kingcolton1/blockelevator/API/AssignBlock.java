package com.kingcolton1.blockelevator.API;

import net.minecraft.src.game.block.Block;

public class AssignBlock {
    private Block material;

    public AssignBlock(Block material) {
        this.material = material;
    }

    private int convertMaterialToID(Block material) {
        this.material = material;
        int converted = this.material.blockID;

        return converted;
    }

    public int getID() {
        return convertMaterialToID(material);
    }

    public Block getMaterial() {
        return this.material;
    }
}
