package com.feliscape.nuanced_combat.networking.packets;

import com.feliscape.nuanced_combat.NuancedCombat;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record AddStunnedEntityPayload(int entityId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<AddStunnedEntityPayload> TYPE =
            new CustomPacketPayload.Type<>(NuancedCombat.location("add_stunned_entity"));

    public static final StreamCodec<ByteBuf, AddStunnedEntityPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            AddStunnedEntityPayload::entityId,
            AddStunnedEntityPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
