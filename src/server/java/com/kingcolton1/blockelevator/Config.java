package com.kingcolton1.blockelevator;

import com.fox2code.foxloader.loader.ServerMod;
import com.google.common.collect.Lists;
import net.minecraft.src.game.block.Block;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Config {
/*    public boolean enabled = true;
    public List<Integer> elevatorBlockIDs = new ArrayList<>();
    public Integer coolDownTicks = null;
    public Integer maxYStep = null;
    public Double dYRequiredForJump = null;*/

    public boolean enabled = true;
    public List<Integer> elevatorBlockIDs = Lists.newArrayList(Block.blockGold.blockID);
    public Integer coolDownTicks = 15;
    public Integer maxYStep = 40;
    public Double dYRequiredForJump = 0.075;

    private final List<Integer> defaultElevatorBlockIDs = Lists.newArrayList(Block.blockGold.blockID);
    private final Integer defaultCoolDownTicks = 15;
    private final Integer defaultMaxYStep = 40;
    private final Double defaultDYRequiredForJump = 0.075;

    public final String enabledConfigName = "enabled";
    public final String elevatorBlocksConfigName = "elevatorblocks";
    public final String coolDownTicksConfigName = "cooldownticks";
    public final String maxYStepConfigName = "maxystep";
    public final String dYRequiredForJumpConfigName = "dyrequiredforjump";

    public void writeToFile(final String filename){
        try{
            FileWriter fileWriter = new FileWriter(filename);

            fileWriter.write(enabledConfigName + "=" + enabled + "\n");

            fileWriter.write(elevatorBlocksConfigName + "=");
            for (Integer blockID : elevatorBlockIDs)
                fileWriter.write(blockID + ",");
            fileWriter.write("\n");

            fileWriter.write(coolDownTicksConfigName + "=" + coolDownTicks + "\n");
            fileWriter.write(maxYStepConfigName + "=" + maxYStep + "\n");
            fileWriter.write(dYRequiredForJumpConfigName + "=" + dYRequiredForJump + "\n");

            fileWriter.close();
        } catch (IOException e) {
            ServerMod.getGameInstance().logWarning(BlockElevator.loggingPrefix + "Failed to write config file to " + filename);
        }
    }

    public void loadFromFile(final String filename){
        Scanner scanner;
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            // Config not found, let's use the default values
            /*elevatorBlockIDs = defaultElevatorBlockIDs;
            coolDownTicks = defaultCoolDownTicks;
            maxYStep = defaultMaxYStep;
            dYRequiredForJump = defaultDYRequiredForJump;*/
            return;
        }

        int lineNum = 0;
        while (scanner.hasNextLine()){
            ++lineNum;
            String line = scanner.nextLine();

            // Let's allow lines starting with a hash to be comments
            if (line.startsWith("#") || line.isEmpty())
                continue;

            int separatorIndex = line.indexOf("=");
            if (separatorIndex == -1){
                ServerMod.getGameInstance().logWarning(BlockElevator.loggingPrefix + "Failed to find '=' separator on line " + lineNum + " in config file " + filename);
                continue;
            }

            String value = line.substring(separatorIndex + 1).trim();

            if (line.startsWith(elevatorBlocksConfigName + "=")){
                String[] elevatorBlockIDsFromLine = value.split(",");
                for (int i = 0; i < elevatorBlockIDsFromLine.length; i++){
                    int blockID;
                    try {
                        blockID = Integer.parseInt(elevatorBlockIDsFromLine[i]);
                        // Let's filter out any invalid block IDs because they can crash clients through the Packet61SoundFX packet
                        if (blockID >= Block.blocksList.length){
                            ServerMod.getGameInstance().logWarning(BlockElevator.loggingPrefix + "Ignoring invalid block ID " + blockID + " found on line " + lineNum + ", at element index " + i + " in config file " + filename);
                            continue;
                        }
                    } catch(NumberFormatException e){
                        ServerMod.getGameInstance().logWarning(BlockElevator.loggingPrefix + "Failed to parse integer on line " + lineNum + ", at element index " + i + " in config file " + filename);
                        continue;
                    }

                    if (!elevatorBlockIDs.contains(blockID))
                        elevatorBlockIDs.add(blockID);
                }
            } else if (line.startsWith(coolDownTicksConfigName + "=")){
                coolDownTicks = Integer.parseInt(value);
            } else if (line.startsWith(maxYStepConfigName + "=")){
                maxYStep = Integer.parseInt(value);
            } else if (line.startsWith(dYRequiredForJumpConfigName + "=")){
                dYRequiredForJump = Double.parseDouble(value);
            } else if (line.startsWith(enabledConfigName + "=")){
                enabled = Boolean.parseBoolean(value);
            }
        }

        // For any values unspecified, let's use the default values
        /*if (elevatorBlockIDs.isEmpty()) elevatorBlockIDs = defaultElevatorBlockIDs;
        if (coolDownTicks == null)      coolDownTicks = defaultCoolDownTicks;
        if (maxYStep == null)           maxYStep = defaultMaxYStep;
        if (dYRequiredForJump == null)  dYRequiredForJump = defaultDYRequiredForJump;*/
    }
}
