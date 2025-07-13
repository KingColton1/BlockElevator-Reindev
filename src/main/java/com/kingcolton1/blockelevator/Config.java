package com.kingcolton1.blockelevator;

import com.google.common.collect.Lists;
import com.fox2code.foxloader.config.ConfigEntry;
import net.minecraft.common.block.Blocks;
import java.util.List;

// Attempting to use new FoxLoader's builtin config so users can modify these configs from the menu directly,
// or in the server's config folder. So that way users don't get confused with the config file being in different location.

// ISSUE: Integer and double are basically slider in the FoxLoader's builtin config instead of the input box. Unsure if that's the limitation or not.
public class Config {
    @ConfigEntry(configName = "Enable Elevator")
    public boolean enableElevator = true;

    @ConfigEntry(configName = "Elevator Block ID")
    public List<Integer> elevatorBlockIDs = Lists.newArrayList(Blocks.GOLD_BLOCK.blockID);

    @ConfigEntry(configName = "Cooldown Ticks")
    public int coolDownTicks = 15;

    @ConfigEntry(configName = "Max Y-Height Step")
    public int maxYStep = 40;

    @ConfigEntry(configName = "dY-Height Required for Jump")
    public double dYRequiredForJump = 0.075;

    // For Elevator Command, see commands/Elevator.java
    public final String enabledConfigName = "enabled";
    public final String coolDownTicksConfigName = "cooldownticks";
    public final String maxYStepConfigName = "maxystep";
    public final String dYRequiredForJumpConfigName = "dyrequiredforjump";
}
