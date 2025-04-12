package com.feliscape.nuanced_combat.networking.packets;

import com.feliscape.nuanced_combat.NuancedCombat;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record UpdateDrowsyShaderPayload(boolean hasEffect) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<UpdateDrowsyShaderPayload> TYPE =
            new CustomPacketPayload.Type<>(NuancedCombat.location("update_drowsy_shader"));

    public static final StreamCodec<ByteBuf, UpdateDrowsyShaderPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            UpdateDrowsyShaderPayload::hasEffect,
            UpdateDrowsyShaderPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
