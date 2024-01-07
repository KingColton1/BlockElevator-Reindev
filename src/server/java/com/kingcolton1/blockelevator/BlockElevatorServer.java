package com.kingcolton1.blockelevator;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kingcolton1.blockelevator.commands.ElevatorBlocks;
import com.kingcolton1.blockelevator.commands.Elevator;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BlockElevatorServer extends BlockElevator implements ServerMod {
    public static Config config = new Config();
    public static final String blockElevatorBasePath = "mods/BlockElevator/";
    public static final String configFilename = blockElevatorBasePath + "config.txt";

    @Override
    public void onInit() {
        // If the file isn't found e.g. when the mod is first installed, this will use the default values
        config.loadFromFile(configFilename);

        CommandCompat.registerCommand(new Elevator());
        CommandCompat.registerCommand(new ElevatorBlocks());

        ServerMod.getGameInstance().log(BlockElevator.loggingPrefix + "Initialized");
    }

    @Override
    public void onServerStop(NetworkPlayer.ConnectionType connectionType) {
        // Make sure mods/BlockElevator directory exists
        Path modsPath = Paths.get(blockElevatorBasePath);
        try {
            Files.createDirectory(modsPath);
        } catch (IOException e){
            if (!(e instanceof FileAlreadyExistsException)) {
                System.err.println(blockElevatorBasePath + " was not found, unable to store mod data");
                e.printStackTrace();
                return;
            }
        }

        config.writeToFile(configFilename);
    }
}
