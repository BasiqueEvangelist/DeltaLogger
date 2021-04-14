package com.github.fabricservertools.deltalogger.network;

import com.github.fabricservertools.deltalogger.beans.Placement;
import com.github.fabricservertools.deltalogger.beans.Transaction;
import com.github.fabricservertools.deltalogger.dao.DAO;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class InspectPacket {
    public static final Identifier INSPECT_PACKET = new Identifier("deltalogger", "inspect");
    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver(INSPECT_PACKET, ((server, player, handler, buf, responseSender) -> {
            if (!player.hasPermissionLevel(3) && !Permissions.check(player, "deltalogger.inspect") && Permissions.check(player, "deltalogger.all")) return;
            BlockPos pos = buf.readBlockPos();
            short limit = buf.readShort();
            PacketByteBuf response = PacketByteBufs.create();
            // What the actual fuck? - BasiqueEvangelist
            // if(player.getEntityWorld().getBlockEntity(pos) != null) {

            List<Transaction> transactions = DAO.transaction.getTransactionsAt(player.getEntityWorld().getRegistryKey().getValue(), pos, limit);
            response.writeVarInt(transactions.size());
            for (Transaction transaction : transactions) {
                NetworkUtils.writeToBuf(transaction, response);
            }
            List<Placement> placements = DAO.block.getLatestPlacementsAt(player.getEntityWorld().getRegistryKey().getValue(), pos, 0, limit);
            response.writeVarInt(placements.size());
            for (Placement placement : placements) {
                NetworkUtils.writeToBuf(placement, response);
            }

            responseSender.sendPacket(INSPECT_PACKET, response);
        }));
    }
}
