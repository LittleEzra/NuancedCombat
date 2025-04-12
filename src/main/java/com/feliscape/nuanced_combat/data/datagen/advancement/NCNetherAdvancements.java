package com.feliscape.nuanced_combat.data.datagen.advancement;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.registry.NuancedCombatMobEffects;
import com.feliscape.nuanced_combat.registry.NuancedCombatPotions;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.EffectsChangedTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class NCNetherAdvancements implements AdvancementProvider.AdvancementGenerator {
    private Component titleComponent(String name){
        return Component.translatable("advancements.%s.%s.title".formatted(NuancedCombat.MOD_ID, name));
    }
    private Component descriptionComponent(String name){
        return Component.translatable("advancements.%s.%s.description".formatted(NuancedCombat.MOD_ID, name));
    }

    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
        AdvancementHolder drinkCombustionPotion = Advancement.Builder.advancement()
                .parent(AdvancementSubProvider.createPlaceholder("minecraft:nether/brew_potion"))
                .display(PotionContents.createItemStack(Items.POTION, NuancedCombatPotions.COMBUSTION),
                        titleComponent("drink_combustion_potion"),
                        descriptionComponent("drink_combustion_potion"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        true
                )
                .addCriterion("drink_combustion_potion",
                        ConsumeItemTrigger.TriggerInstance.usedItem(
                                ItemPredicate.Builder.item()
                                        .of(Items.POTION)
                                        .hasComponents(
                                                DataComponentPredicate.builder()
                                                        .expect(DataComponents.POTION_CONTENTS,
                                                                PotionContents.EMPTY.withPotion(NuancedCombatPotions.COMBUSTION))
                                                        .build()
                                        )
                        )
                        )
                .save(consumer, NuancedCombat.location("nether/drink_combustion_potion"), existingFileHelper);
    }
}
