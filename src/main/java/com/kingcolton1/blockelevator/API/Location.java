package com.kingcolton1.blockelevator.API;

public class Location implements Cloneable {
    private World world;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;

    public Location(final World world, final double x, final double y, final double z) {
        this(world, x, y, z, 0, 0);
    }

    public Location(final World world, final double x, final double y, final double z, final float yaw, final float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public Block getBlock() {
        return world.getBlockAt(this);
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public int getBlockX() {
        return locToBlock(x);
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public int getBlockY() {
        return locToBlock(y);
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getZ() {
        return z;
    }

    public int getBlockZ() {
        return locToBlock(z);
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getPitch() {
        return pitch;
    }

    public Location add(Location vec) {
        if (vec == null || vec.getWorld() != getWorld()) {
            throw new IllegalArgumentException("Cannot add Locations of differing worlds");
        }

        x += vec.x;
        y += vec.y;
        z += vec.z;
        return this;
    }

    public Location add(Vector vec) {
        this.x += vec.getX();
        this.y += vec.getY();
        this.z += vec.getZ();
        return this;
    }

    public Location add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public Location clone() {
        try {
            return (Location) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    /**
     * Safely converts a double (location coordinate) to an int (block
     * coordinate)
     *
     * @param loc Precise coordinate
     * @return Block coordinate
     */
    public static int locToBlock(double loc) {
        return NumberConversions.floor(loc);
    }
}
