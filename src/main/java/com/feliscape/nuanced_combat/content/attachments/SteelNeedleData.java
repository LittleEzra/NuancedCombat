package com.feliscape.nuanced_combat.content.attachments;

import com.feliscape.nuanced_combat.data.damagetypes.NuancedCombatDamageSources;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public class SteelNeedleData implements INBTSerializable<CompoundTag> {
    private int needles;
    private int needleDecay;

    public void addNeedle(){
        if (needles < 10) {
            needles++;
            needleDecay = 0;
        }
    }
    public void removeNeedle(){
        if (needles > 0)
            needles--;
    }
    public void tick(LivingEntity entity){
        if (needles > 0){
            needleDecay++;

            if (entity.tickCount % 30 - Math.min(needles * 2, 20) == 0)
                entity.hurt(NuancedCombatDamageSources.bleeding(entity.level()), 1.0F + needles * 0.5F);

            if (needleDecay > 600) {
                removeNeedle();
                needleDecay = 0;
            }
        }
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("needles", needles);
        tag.putInt("needleDecay", needleDecay);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {
        needles = compoundTag.getInt("needles");
        needleDecay = compoundTag.getInt("needleDecay");
    }
}
