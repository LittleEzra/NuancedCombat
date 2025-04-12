package com.feliscape.nuanced_combat.data.datagen.tag;

import com.feliscape.nuanced_combat.data.enchantments.NuancedCombatEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

public class NCEnchantmentTagGenerator extends EnchantmentTagsProvider {
    public NCEnchantmentTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(EnchantmentTags.TRADEABLE)
                .add(NuancedCombatEnchantments.STUNNING);
        this.tag(EnchantmentTags.ON_RANDOM_LOOT)
                .add(NuancedCombatEnchantments.STUNNING);
    }
}
