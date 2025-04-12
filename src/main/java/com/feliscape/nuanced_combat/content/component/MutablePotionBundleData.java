package com.feliscape.nuanced_combat.content.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class MutablePotionBundleData {
    public static final Codec<MutablePotionBundleData> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                Codec.INT.fieldOf("uses").forGetter(data -> data.uses),
                Codec.INT.fieldOf("maxUses").forGetter(data -> data.maxUses),
                Codec.INT.fieldOf("currentPotion").forGetter(data -> data.currentPotion)
            ).apply(inst, MutablePotionBundleData::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, MutablePotionBundleData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            d -> d.uses,
            ByteBufCodecs.INT,
            d -> d.maxUses,
            ByteBufCodecs.INT,
            d -> d.currentPotion,
            MutablePotionBundleData::new
    );

    int uses;
    int maxUses;
    int currentPotion;

    public MutablePotionBundleData(int uses, int maxUses, int currentPotion) {
        this.uses = uses;
        this.maxUses = maxUses;
        this.currentPotion = currentPotion;
    }
}
