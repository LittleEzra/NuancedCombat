package com.feliscape.nuanced_combat.data.datagen.recipe;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.recipe.ExplosiveArrowRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.world.item.crafting.FireworkRocketRecipe;

import java.util.concurrent.CompletableFuture;

public class NCRecipeProvider extends RecipeProvider {

    public NCRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        SpecialRecipeBuilder.special(ExplosiveArrowRecipe::new).save(recipeOutput, NuancedCombat.location("explosive_arrow"));
    }
}