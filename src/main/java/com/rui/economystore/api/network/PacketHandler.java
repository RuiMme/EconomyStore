package com.rui.economystore.api.network;

import com.rui.economystore.api.network.handler.client.PacketSyncClientHandler;
import com.rui.economystore.api.network.handler.server.PacketSyncServerHandler;
import com.rui.economystore.api.network.messages.client.StatEconomySyncClientMessage;
import com.rui.economystore.api.network.messages.server.StatEconomySyncServerMessage;
import com.rui.economystore.api.stat.StatBase;
import com.rui.economystore.api.stat.Stats;
import com.rui.economystore.api.stat.stats.StatEconomy;
import com.rui.economystore.libs.LibMisc;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

public class PacketHandler {
    private static final String PROTOCOL = "1";
    public static final SimpleChannel HANDLER = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(LibMisc.MOD_ID, "main"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    public static void init() {
        int id = 0;
        // sync client economy
        HANDLER.registerMessage(id++, StatEconomySyncClientMessage.class, StatEconomySyncClientMessage::writeBytes,
                buffer -> { StatEconomySyncClientMessage message = new StatEconomySyncClientMessage();message.readBytes(buffer);return message;},
                PacketSyncClientHandler::acceptEconomySyncClient, Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        // sync server economy
        HANDLER.registerMessage(id++, StatEconomySyncServerMessage.class, StatEconomySyncServerMessage::writeBytes,
                buffer -> { StatEconomySyncServerMessage message = new StatEconomySyncServerMessage();message.readBytes(buffer);return message;},
                PacketSyncServerHandler::acceptEconomySyncServer);
    }

    public static void sendToServer(Object msg) {
        HANDLER.sendToServer(msg);
    }

    public static void sendToClient(ServerPlayerEntity player, Object msg) {
        HANDLER.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    public static void sendAllToClient(ServerPlayerEntity player) {
        sendToClient(player,new StatEconomySyncClientMessage(StatBase.stats.indexOf(Stats.ECONOMY), DataHelper.getPlayerEconomy(player)));
    }
}
