package com.kingcolton1.blockelevator.API;

import net.minecraft.src.game.block.Block;

/**
 * Assigns an existing block.
 */
public class AssignBlock {
    private final Block blockName;
    private int blockID;

    /**
     * Constructs a new AssignBlock with the given block name
     *
     * @param blockName The name of a block, must be Enum using Minecraft API directly.
     */
    public AssignBlock(Block blockName) {
        this.blockName = blockName;
    }

    /**
     * Get the id of a converted block name (Currently not working properly)
     * 
     * @return ID of a block
     */
    public int getID() {
        blockID = blockName.blockID;
        return blockID;
    }

    /**
     * Get the name of a assigned block
     * 
     * @return Name of a block
     */
    public Block getBlockName() {
        return blockName;
    }
}
