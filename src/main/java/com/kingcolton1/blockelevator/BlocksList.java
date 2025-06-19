package com.kingcolton1.blockelevator;

import net.minecraft.common.block.Block;
import net.minecraft.common.block.Blocks;

// Reimplementation of block.blockLists[], which is removed in FoxLoader 2.0.
public class BlocksList {
    public String getBlockName(int blockId, int subString) {
        for (Block entry : Blocks.BLOCKS_LIST) {
            if (entry.blockID == blockId) {
                return entry.getBlockName().substring(subString);
            }
        }
        return "Unknown Block";
    }

    public void checkBlockId(int blockId) {
        for (Block entry : Blocks.BLOCKS_LIST) {
            if (entry.blockID == blockId) {
                return;
            }
            else {
                throw new ArrayIndexOutOfBoundsException("Invalid block ID " + blockId);
            }
        }
    }
}
