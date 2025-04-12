package com.feliscape.nuanced_combat.data.datagen.tag;

import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class NCItemTagGenerator extends ItemTagsProvider {
    public NCItemTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.ARROWS)
                .add(NuancedCombatItems.EXPLOSIVE_ARROW.get());

        // Enchantable
        this.tag(ItemTags.DURABILITY_ENCHANTABLE)
                .add(NuancedCombatItems.WAVEHAMMER.get());
    }
}
