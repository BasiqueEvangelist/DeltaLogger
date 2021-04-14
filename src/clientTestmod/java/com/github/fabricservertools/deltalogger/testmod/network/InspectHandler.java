package com.github.fabricservertools.deltalogger.testmod.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public final class InspectHandler {
    private InspectHandler() {

    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(new Identifier("deltalogger", "inspect"), (client, handler, buf, responseSender) -> {
            int transactionsCount = buf.readVarInt();
            for (int i = 0; i < transactionsCount; i++) {
                String playerName = buf.readString();
                long time = buf.readLong();
                int count = buf.readInt();
                String itemType = buf.readString();

                client.player.sendMessage(new LiteralText(String.format("Got transaction: playername=%s, time=%d, count=%d, itemType=%s", playerName, time, count, itemType)), false);
            }

            int placementsCount = buf.readVarInt();
            for (int i = 0; i < placementsCount; i++) {
                BlockPos pos = buf.readBlockPos();
                boolean placed = buf.readBoolean();
                String playerName = buf.readString();
                long time = buf.readLong();
                String blockType = buf.readString();
                String state = buf.readString();

                client.player.sendMessage(new LiteralText(String.format("Got placement: pos=%s, placed=%s, playerName=%s, time=%d, blockType=%s, state=%s", pos, placed, playerName, time, blockType, state)), false);
            }
        });
    }

    public static void sendInspectPacket(BlockPos position, short limit) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(position);
        buf.writeShort(limit);

        ClientPlayNetworking.send(new Identifier("deltalogger", "inspect"), buf);
    }
}
