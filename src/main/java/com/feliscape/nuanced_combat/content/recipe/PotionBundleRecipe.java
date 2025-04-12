package com.feliscape.nuanced_combat.content.recipe;

import com.feliscape.nuanced_combat.content.component.PotionBundleContents;
import com.feliscape.nuanced_combat.content.item.PotionBundleItem;
import com.feliscape.nuanced_combat.registry.NuancedCombatRecipeSerializers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;

public class PotionBundleRecipe extends CustomRecipe {
    private static final Ingredient POTION_INGREDIENT = Ingredient.of(Items.POTION);

    public PotionBundleRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        boolean hasString = false;
        int potions = 0;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemstack = input.getItem(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.is(Items.STRING)) {
                    if (hasString) return false;
                    hasString = true;
                } else if (itemstack.is(Items.POTION)){
                    if (potions == 3) return false;
                    potions++;
                }
            }
        }
        return potions == 3 && hasString;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider provider) {
        ArrayList<Holder<Potion>> potions = new ArrayList<>();

        for (int i = 0; i < input.size(); i++){
            ItemStack itemStack = input.getItem(i);
            if(itemStack.is(Items.POTION)){
                if (itemStack.has(DataComponents.POTION_CONTENTS)){
                    var potion = itemStack.get(DataComponents.POTION_CONTENTS).potion();
                    potion.ifPresent(potions::add);
                }
            }
        }

        return PotionBundleItem.withContents(new PotionBundleContents(potions));
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 4;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NuancedCombatRecipeSerializers.POTION_BUNDLE.get();
    }
}
