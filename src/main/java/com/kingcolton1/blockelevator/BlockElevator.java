package com.kingcolton1.blockelevator;

import com.fox2code.foxloader.registry.CommandRegistry;
import com.fox2code.foxloader.loader.Mod;
import com.kingcolton1.blockelevator.commands.Elevator;
import com.kingcolton1.blockelevator.commands.ElevatorBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BlockElevator extends Mod {
    private static final Logger log = LoggerFactory.getLogger(BlockElevator.class);
    public static String loggingPrefix = "[BlockElevator] ";
    public static final Config config = new Config();
    //public static final String blockElevatorBasePath = "mods/BlockElevator/";
    //public static final String configFilename = blockElevatorBasePath + "config.txt";

    @Override
    public void onPreInit() {
        log.info(loggingPrefix + "Initializing...");
        CommandRegistry.registerCommand(new Elevator());
        CommandRegistry.registerCommand(new ElevatorBlocks());
    }

    @Override
    public void onInit() {
        // If the file isn't found, e.g., when the mod is first installed, this will use the default values
        //config.loadFromFile(configFilename);
        log.info(loggingPrefix + "Initialized! Enjoy :)");
    }

    /*
    public void onPostInit() {
        // Make sure the mods / BlockElevator directory exists
        Path modsPath = Paths.get(blockElevatorBasePath);
        try {
            Files.createDirectory(modsPath);
        } catch (IOException e){
            if (!(e instanceof FileAlreadyExistsException)) {
                log.error(blockElevatorBasePath + "was not found, unable to store mod data");
                e.printStackTrace();
                return;
            }
        }
        config.writeToFile(configFilename);
    }*/
}
