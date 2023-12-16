package com.kingcolton1.blockelevator.API;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

public class PlayerJumpEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    @NotNull private Location from;
    @NotNull private Location to;

    public PlayerJumpEvent(@NotNull final Player player, @NotNull final Location from, @NotNull final Location to) {
        super(player);
        this.from = from;
        this.to = to;
    }

    public boolean isCancelled() {
        return cancel;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @NotNull
    public Location getFrom() {
        return from;
    }

    public void setFrom(@NotNull Location from) {
        validateLocation(from);
        this.from = from;
    }

    @NotNull
    public Location getTo() {
        return to;
    }

    private void validateLocation(Location loc) {
        Preconditions.checkArgument(loc != null, "Cannot use null location!");
        Preconditions.checkArgument(loc.getWorld() != null, "Cannot use location with null world!");
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}