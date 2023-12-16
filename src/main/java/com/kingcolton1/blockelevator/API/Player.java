package com.kingcolton1.blockelevator.API;

public interface Player {
    public boolean teleport(Location location);
    public Vector getVelocity(Vector velocity);
    public void setVelocity(Vector velocity);
    public Location getLocation();
    public Location getLocation(Location loc);
}
