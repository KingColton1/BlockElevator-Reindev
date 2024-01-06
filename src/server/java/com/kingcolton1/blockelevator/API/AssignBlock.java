package com.kingcolton1.blockelevator.API;

import net.minecraft.src.game.block.Block;

/**
 * Assigns an existing block.
 */
public class AssignBlock {
    private Block blockName;

    /**
     * Constructs a new AssignBlock with the given block name
     *
     * @param blockName The name of a block, must be Enum using Minecraft API directly.
     */
    public AssignBlock(Block blockName) {
        this.blockName = blockName;
    }

    /**
     * Converts block name to the block ID
     *
     * @param blockName The name of a block, must be Enum using Minecraft API directly.
     * 
     * @return Converted to ID from a given block name
     */
    private int convertMaterialToID(Block blockName) {
        this.blockName = blockName;
        int converted = this.blockName.blockID;

        return converted;
    }

    /**
     * Get the id of a converted block name
     * 
     * @return ID of a block
     */
    public int getID() {
        return convertMaterialToID(blockName);
    }

    /**
     * Get the name of a assigned block
     * 
     * @return Name of a block
     */
    public Block getBlockName() {
        return this.blockName;
    }
}
