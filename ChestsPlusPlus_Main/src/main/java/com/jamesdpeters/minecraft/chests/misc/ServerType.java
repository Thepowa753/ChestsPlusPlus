package com.jamesdpeters.minecraft.chests.misc;

import com.jamesdpeters.minecraft.chests.ChestsPlusPlus;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
public class ServerType {

    public enum Type {
        BUKKIT,
        SPIGOT,
        PAPER
    }

    @Getter
    private static Type type;

    public static boolean isPaperLike() {
        return getType() == Type.PAPER;
    }

    public static void init() {
        //Default to Bukkit.
        type = Type.BUKKIT;

        try {
            Class.forName("org.spigotmc.SpigotConfig");
            // If reached here class exists
            type = Type.SPIGOT;
        } catch (Exception ignored){}

        if (classExists("com.destroystokyo.paper.VersionHistoryManager$VersionData") ||
                classExists("io.papermc.paper.configuration.Configuration") ||
                classExists("org.purpurmc.purpur.PurpurConfig")) {
            type = Type.PAPER;
        }

        ChestsPlusPlus.PLUGIN.getLogger().info("Detected Server Type: "+getType());
    }

    private static boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
