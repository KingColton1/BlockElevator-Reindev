package com.kingcolton1.blockelevator;

import com.google.common.collect.Lists;
import net.minecraft.common.block.Block;
import net.minecraft.common.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Config {
    private static final Logger log = LoggerFactory.getLogger(Config.class);
    public boolean enabled = true;
    public List<Integer> elevatorBlockIDs = Lists.newArrayList(Blocks.GOLD_BLOCK.blockID);
    public Integer coolDownTicks = 15;
    public Integer maxYStep = 40;
    public Double dYRequiredForJump = 0.075;

    public final String enabledConfigName = "enabled";
    public final String elevatorBlocksConfigName = "elevatorblocks";
    public final String coolDownTicksConfigName = "cooldownticks";
    public final String maxYStepConfigName = "maxystep";
    public final String dYRequiredForJumpConfigName = "dyrequiredforjump";

    public void writeToFile(final String filename){
        try{
            FileWriter fileWriter = new FileWriter(filename);

            fileWriter.write(enabledConfigName + "=" + enabled + "\n\n");

            fileWriter.write(elevatorBlocksConfigName + "=");
            for (Integer blockID : elevatorBlockIDs)
                fileWriter.write(blockID + ",");
            fileWriter.write("\n\n");
            
            fileWriter.write(coolDownTicksConfigName + "=" + coolDownTicks + "\n\n");
            fileWriter.write(maxYStepConfigName + "=" + maxYStep + "\n\n");
            fileWriter.write(dYRequiredForJumpConfigName + "=" + dYRequiredForJump + "\n");

            fileWriter.close();
        } catch (IOException e) {
            log.warn(BlockElevator.loggingPrefix + "Failed to write config file to " + filename);
        }
    }

    public void loadFromFile(final String filename){
        Scanner scanner;
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
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
                log.warn(BlockElevator.loggingPrefix + "Failed to find '=' separator on line " + lineNum + " in config file " + filename);
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
                        /*if (blockID >= Block.blocksList.length){
                            log.warn("{}Ignoring invalid block ID {} found on line {}, at element index {} in config file {}", BlockElevator.loggingPrefix, blockID, lineNum, i, filename);
                            continue;
                        }*/
                    } catch(NumberFormatException e){
                        log.warn(BlockElevator.loggingPrefix + "Failed to parse integer on line " + lineNum + " at element index " + i + " in config file " + filename);
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
    }
}
