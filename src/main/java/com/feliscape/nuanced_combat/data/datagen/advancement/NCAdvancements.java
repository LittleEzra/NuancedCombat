package com.feliscape.nuanced_combat.data.datagen.advancement;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NCAdvancements extends AdvancementProvider {
    public NCAdvancements(PackOutput output, ExistingFileHelper existingFileHelper, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, existingFileHelper, List.of(new NCNetherAdvancements()));
    }
}
