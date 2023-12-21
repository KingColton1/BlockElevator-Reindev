package com.kingcolton1.blockelevator.API;

import com.fox2code.foxloader.registry.RegisteredBlock;
import net.minecraft.src.game.block.Block;

public class AssignBlock {
    private int id = 0;
    private Block material;
    private RegisteredBlock registeredBlock;

    public AssignBlock(Block material) {
        this.material = material;
    }

    private int convertMaterialToID(Block material) {
        this.material = material;
        int converted = this.material.blockID;
        this.id = converted;

        return converted;
    }

    public int getID() {
        return convertMaterialToID(material);
    }

    public Block getMaterial() {
        return this.material;
    }

    public int registerBlockId() {
        int id = getID();
        id = registeredBlock.getRegisteredBlockId();
        return id;
    }
}
