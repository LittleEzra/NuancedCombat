package com.feliscape.nuanced_combat.data.datagen.advancement;

import com.feliscape.nuanced_combat.NuancedCombat;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class NCAdventureAdvancements implements AdvancementProvider.AdvancementGenerator {
    private Component titleComponent(String name){
        return Component.translatable("advancements.%s.%s.title".formatted(NuancedCombat.MOD_ID, name));
    }
    private Component descriptionComponent(String name){
        return Component.translatable("advancements.%s.%s.description".formatted(NuancedCombat.MOD_ID, name));
    }

    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {

    }
}
