package com.veil.commandblocker;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.CommandEvent;

import java.util.Set;
import java.util.UUID;

public class CommandEventHandler {
    @SubscribeEvent
    public void onCommand(CommandEvent event) {
        String username = event.sender.getCommandSenderName();
        EntityPlayer player = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(username);
        if (player == null) {
            return;
        }

        boolean isOp = MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile());
        if (isOp) return;

        String commandName = event.command.getCommandName();
        Set<String> blockedCommands = Config.getBlockedCommands();
        UUID playerUUID = player.getUniqueID();

        if (blockedCommands.contains(commandName) && !Config.getBlockedCommands().contains(playerUUID.toString())) {
            String denyMessage = Config.getDenyMessage();
            event.sender.addChatMessage(new ChatComponentText(denyMessage));
            event.setCanceled(true);
        }
    }
}
