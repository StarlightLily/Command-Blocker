package com.veil.commandblocker;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.minecraftforge.common.config.Configuration;

public class Config {
    public static Configuration config;
    private static Set<String> blockedCommandsSet;
    private static Set<String> exemptUsersSet;
    private static String denyMessage;
    private static boolean exemptOps;

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
        }

        loadConfig();
    }

    private static void loadConfig() {
        try {
            config.load();

            String[] blockedCommands = config.getStringList(
                "blockedCommands", // Property name
                "general", // Category
                new String[] {}, // Default values
                "A list of commands to block. These commands will be blocked when players try to execute them."
            );

            String[] exemptUsers = config.getStringList(
                "exemptUsers",
                "general",
                new String[] {},
                "A list of users by UUID exempt from these restrictions. You can find a user's UUID here: https://mcuuid.net"
            );

            denyMessage = config.getString(
                "denyMessage",
                "general",
                "§cYou don't have permission to use this command!",
                "The message to send to the player when they are not allowed to use a command. §a-f and §0-9 for different colors"
            );

            exemptOps = config.getBoolean(
                "exemptOps",
                "general",
                true,
                "Whether or not to exempt ops from these restrictions."
            );

            exemptUsersSet = new HashSet<>(Arrays.asList(exemptUsers));
            blockedCommandsSet = new HashSet<>(Arrays.asList(blockedCommands));
        } catch (Exception e) {
            System.err.println("Error loading CommandBlocker config file: " + e.getMessage());
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }

    public static Set<String> getBlockedCommands() {
        return blockedCommandsSet;
    }

    public static Set<String> getExemptUsers() {
        return exemptUsersSet;
    }

    public static String getDenyMessage() {
        return denyMessage;
    }
    public static boolean getExemptOps() { return exemptOps; }
}
