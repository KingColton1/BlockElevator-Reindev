package com.kingcolton1.blockelevator.API;

public interface EventExecutor {
    public void execute(Listener listener, Event event) throws EventException;
}