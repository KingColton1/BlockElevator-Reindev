package com.kingcolton1.blockelevator.API;

public interface Block extends Metadatable {
    Block getRelative(int modX, int modY, int modZ);
    Block getRelative(BlockFace face);
    Block getRelative(BlockFace face, int distance);
    /**
     * Gets the type of this block
     *
     * @return block type
     */
    Material getType();

    /**
     * Sets the type of this block
     *
     * @param type Material to change this block to
     */
    void setType(Material type);

    /**
     * Gets the world which contains this Block
     *
     * @return World containing this block
     */
    World getWorld();

    /**
     * Gets the x-coordinate of this block
     *
     * @return x-coordinate
     */
    int getX();

    /**
     * Gets the y-coordinate of this block
     *
     * @return y-coordinate
     */
    int getY();

    /**
     * Gets the z-coordinate of this block
     *
     * @return z-coordinate
     */
    int getZ();

    /**
     * Gets the Location of the block
     *
     * @return Location of block
     */
    Location getLocation();

    /**
     * Stores the location of the block in the provided Location object.
     * <p>
     * If the provided Location is null this method does nothing and returns
     * null.
     *
     * @return The Location object provided or null
     */
    Location getLocation(Location loc);
}
