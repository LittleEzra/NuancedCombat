package com.feliscape.nuanced_combat.content.attachments;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public class SoaringData implements INBTSerializable<CompoundTag> {
    private boolean soaring;
    private int resetDelay;

    public boolean isSoaring(){
        return soaring;
    }

    public void startSoaring(){
        soaring = true;
        resetDelay = 10;
    }

    public void tick(LivingEntity entity){
        if (soaring && entity.onGround() && resetDelay == 0){
            soaring = false;
        } else if (resetDelay > 0){
            resetDelay--;
        }
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("soaring", soaring);
        tag.putInt("resetDelay", resetDelay);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {
        soaring = compoundTag.getBoolean("soaring");
        resetDelay = compoundTag.getInt("resetDelay");
    }
}
