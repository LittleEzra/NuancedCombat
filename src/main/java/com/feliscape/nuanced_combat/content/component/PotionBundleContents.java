package com.feliscape.nuanced_combat.content.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.*;
import java.util.function.Consumer;

public record PotionBundleContents(List<Holder<Potion>> potions) implements TooltipProvider {
    public static final PotionBundleContents EMPTY = new PotionBundleContents(List.of());
    public static final PotionBundleContents DEFAULT = new PotionBundleContents(List.of(Potions.WEAKNESS, Potions.POISON, Potions.SLOWNESS));

    public static final Codec<PotionBundleContents> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Potion.CODEC.listOf().fieldOf("potions").forGetter(PotionBundleContents::potions)).apply(instance, PotionBundleContents::new);
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, PotionBundleContents> STREAM_CODEC = StreamCodec.composite(
                    ByteBufCodecs.collection(ArrayList::new, Potion.STREAM_CODEC),
                    PotionBundleContents::potions,
                    PotionBundleContents::new
            );

    public PotionBundleContents(List<Holder<Potion>> potions) {
        this.potions = potions;
    }

    public List<MobEffectInstance> getAllEffects() {
        ArrayList<MobEffectInstance> list = new ArrayList<>();
        for (var potion : potions){
            list.addAll(potion.value().getEffects());
        }
        return list;
    }

    public static int getColor(Iterable<MobEffectInstance> effects) {
        return getColorOptional(effects).orElse(-13083194);
    }

    public static OptionalInt getColorOptional(Iterable<MobEffectInstance> effects) {
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        Iterator var5 = effects.iterator();

        while(var5.hasNext()) {
            MobEffectInstance mobeffectinstance = (MobEffectInstance)var5.next();
            if (mobeffectinstance.isVisible()) {
                int i1 = ((MobEffect)mobeffectinstance.getEffect().value()).getColor();
                int j1 = mobeffectinstance.getAmplifier() + 1;
                i += j1 * FastColor.ARGB32.red(i1);
                j += j1 * FastColor.ARGB32.green(i1);
                k += j1 * FastColor.ARGB32.blue(i1);
                l += j1;
            }
        }

        return l == 0 ? OptionalInt.empty() : OptionalInt.of(FastColor.ARGB32.color(i / l, j / l, k / l));
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        var effects = getAllEffects();

        MutableComponent mutablecomponent;
        Holder<?> holder;
        for(Iterator<MobEffectInstance> iterator = effects.iterator(); iterator.hasNext(); consumer.accept(mutablecomponent.withStyle(((MobEffect)holder.value()).getCategory().getTooltipFormatting()))) {
            MobEffectInstance mobeffectinstance = (MobEffectInstance)iterator.next();

            mutablecomponent = Component.translatable(mobeffectinstance.getDescriptionId());
            holder = mobeffectinstance.getEffect();
            if (mobeffectinstance.getAmplifier() > 0) {
                mutablecomponent = Component.translatable("potion.withAmplifier", new Object[]{mutablecomponent, Component.translatable("potion.potency." + mobeffectinstance.getAmplifier())});
            }

            if (!mobeffectinstance.endsWithin(20)) {
                mutablecomponent = Component.translatable("potion.withDuration", new Object[]{mutablecomponent, MobEffectUtil.formatDuration(mobeffectinstance, 1.0F, tooltipContext.tickRate())});
            }
        }

    }
}
