package com.feliscape.nuanced_combat.content.recipe;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.registry.NuancedCombatComponents;
import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import com.feliscape.nuanced_combat.registry.NuancedCombatRecipeSerializers;
import com.feliscape.nuanced_combat.registry.NuancedCombatRecipeTypes;
import com.google.gson.JsonObject;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class ExplosiveArrowRecipe extends CustomRecipe {
    private static final Ingredient ARROW_INGREDIENT = Ingredient.of(Items.ARROW);
    private static final Ingredient GUNPOWDER_INGREDIENT = Ingredient.of(Items.GUNPOWDER);

    public ExplosiveArrowRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        int arrows = 0;
        int gunpowder = 0;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemstack = input.getItem(i);
            if (!itemstack.isEmpty()) {
                if (ARROW_INGREDIENT.test(itemstack)) {
                    ++arrows;
                    if (arrows > 2) {
                        return false;
                    }

                } else if (GUNPOWDER_INGREDIENT.test(itemstack)) {
                    ++gunpowder;
                    if (gunpowder > 3) {
                        return false;
                    }
                }
            }
        }

        return arrows == 2 && gunpowder > 0;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider provider) {
        ItemStack itemStack = new ItemStack(NuancedCombatItems.EXPLOSIVE_ARROW.get());
        int gunpowder = 0;

        for(int j = 0; j < input.size(); ++j) {
            ItemStack itemStack1 = input.getItem(j);
            if (!itemStack1.isEmpty()) {
                if (GUNPOWDER_INGREDIENT.test(itemStack1)) {
                    ++gunpowder;
                }
            }
        }
        itemStack.set(NuancedCombatComponents.POWER, gunpowder);
        itemStack.setCount(2);
        return itemStack;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return new ItemStack(NuancedCombatItems.EXPLOSIVE_ARROW.get());
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return NuancedCombatRecipeSerializers.EXPLOSIVE_ARROW.get();
    }

}
