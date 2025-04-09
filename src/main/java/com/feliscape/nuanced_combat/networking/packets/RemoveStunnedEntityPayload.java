package com.feliscape.nuanced_combat.networking.packets;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.UUID;

public record RemoveStunnedEntityPayload(int entityId) implements CustomPacketPayload {
    public static final Type<RemoveStunnedEntityPayload> TYPE =
            new Type<>(NuancedCombat.location("remove_stunned_entity"));

    public static final StreamCodec<ByteBuf, RemoveStunnedEntityPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            RemoveStunnedEntityPayload::entityId,
            RemoveStunnedEntityPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
