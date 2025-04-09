package com.feliscape.nuanced_combat.data.datagen.tag;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.data.damagetypes.NuancedCombatDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class NCDamageTypeTagGenerator extends DamageTypeTagsProvider {
    public NCDamageTypeTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NuancedCombat.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(DamageTypeTags.NO_IMPACT)
                .add(NuancedCombatDamageTypes.BLEEDING)
        ;
        this.tag(DamageTypeTags.NO_KNOCKBACK)
                .add(NuancedCombatDamageTypes.BLEEDING)
        ;
        this.tag(DamageTypeTags.BYPASSES_ARMOR)
                .add(NuancedCombatDamageTypes.BLEEDING)
        ;
    }
}
