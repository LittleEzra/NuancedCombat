package com.feliscape.nuanced_combat.data.enchantments;

import com.feliscape.nuanced_combat.NuancedCombat;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.Optional;

public class NuancedCombatEnchantments {
    public static final ResourceKey<Enchantment> STUNNING = createKey("stunning");
    public static void bootstrap(BootstrapContext<Enchantment> context){
        HolderGetter<Item> itemHolderGetter = context.lookup(Registries.ITEM);
        context.register(STUNNING,
                new Enchantment(
                        Component.translatable("enchantment.nuanced_combat.stunning"),
                        new Enchantment.EnchantmentDefinition(
                                itemHolderGetter.getOrThrow(Tags.Items.TOOLS_SHIELD),
                                Optional.empty(),
                                30,
                                1,
                                new Enchantment.Cost(4, 2),
                                new Enchantment.Cost(5, 3),
                                4,
                                List.of(EquipmentSlotGroup.OFFHAND, EquipmentSlotGroup.MAINHAND)
                        ),
                        HolderSet.empty(),
                        DataComponentMap.builder()
                                .build()
                        )
        );

    }

    private static ResourceKey<Enchantment> createKey(String name){
        return ResourceKey.create(Registries.ENCHANTMENT, NuancedCombat.location(name));
    }
}
