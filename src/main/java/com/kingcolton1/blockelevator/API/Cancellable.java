package com.kingcolton1.blockelevator.API;

public interface Cancellable {
    public boolean isCancelled();
    public void setCancelled(boolean cancel);
}