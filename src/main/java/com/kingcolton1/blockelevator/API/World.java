package com.kingcolton1.blockelevator.API;

public interface World {
    public Block getBlockAt(int x, int y, int z);
    public Block getBlockAt(Location location);
    public int getMaxHeight();
    public int getMinHeight();
}
