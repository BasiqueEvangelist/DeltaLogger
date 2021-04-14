package com.github.fabricservertools.deltalogger.network;

import com.github.fabricservertools.deltalogger.SQLUtils;
import com.github.fabricservertools.deltalogger.beans.MobGrief;
import com.github.fabricservertools.deltalogger.beans.Placement;
import com.github.fabricservertools.deltalogger.beans.Transaction;
import com.github.fabricservertools.deltalogger.beans.TransactionPos;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class NetworkUtils {
    public static void writeToBuf(Transaction transaction, PacketByteBuf buf) {
        buf.writeString(transaction.getPlayerName());
        buf.writeLong(SQLUtils.getInstantFromDBTimeString(transaction.getTime()).getEpochSecond());
        buf.writeInt(transaction.getCount());
        buf.writeString(transaction.getItemType());
    }

    public static void writeToBuf(Placement placement, PacketByteBuf buf) {
        buf.writeLong(BlockPos.asLong(placement.getX(), placement.getY(), placement.getZ()));
        buf.writeBoolean(placement.getPlaced());
        buf.writeString(placement.getPlayerName());
        buf.writeLong(SQLUtils.getInstantFromDBTimeString(placement.getTime()).getEpochSecond());
        buf.writeString(placement.getBlockType());
        buf.writeString(placement.getState());
    }

    public static void writeToBuf(MobGrief grief, PacketByteBuf buf) {
        buf.writeLong(BlockPos.asLong(grief.getX(), grief.getY(), grief.getZ()));
        buf.writeString(grief.getEntityType());
        buf.writeString(grief.getTarget());
        buf.writeString(grief.getDimension());
        buf.writeLong(SQLUtils.getInstantFromDBTimeString(grief.getTime()).getEpochSecond());
    }

    public static void writeToBuf(TransactionPos transactionPos, PacketByteBuf buf) {
        buf.writeBlockPos(transactionPos.getPos());
        buf.writeString(transactionPos.getPlayerName());
        buf.writeLong(SQLUtils.getInstantFromDBTimeString(transactionPos.getTime()).getEpochSecond());
        buf.writeInt(transactionPos.getCount());
        buf.writeString(transactionPos.getItemType());
    }
}
