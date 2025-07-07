package com.kingcolton1.blockelevator;

import com.google.common.collect.Lists;
import com.fox2code.foxloader.config.ConfigEntry;
import com.fox2code.foxloader.config.ConfigMenu;
import net.minecraft.common.block.Blocks;
import java.util.List;

// Attempting to use new FoxLoader's builtin config so users can modify these configs from the menu directly,
// or in the server's config folder. So that way users don't get confused with the config file being in different location.
public class Config {
    @ConfigEntry(configName = "Enable Elevator")
    public boolean enableElevator = true;

    @ConfigEntry(configName = "Elevator Block ID")
    public List<Integer> elevatorBlockIDs = Lists.newArrayList(Blocks.GOLD_BLOCK.blockID);

    @ConfigEntry(configName = "Cooldown Ticks")
    public Integer coolDownTicks = 15;

    @ConfigEntry(configName = "Max Y-Height Step")
    public Integer maxYStep = 40;

    @ConfigEntry(configName = "dY-Height Required for Jump")
    public Double dYRequiredForJump = 0.075;

    // For Elevator Command, see commands/Elevator.java
    public final String enabledConfigName = "enabled";
    public final String coolDownTicksConfigName = "cooldownticks";
    public final String maxYStepConfigName = "maxystep";
    public final String dYRequiredForJumpConfigName = "dyrequiredforjump";
}
