package com.feliscape.nuanced_combat.data.datagen.recipe;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.item.ExplosiveArrowItem;
import com.feliscape.nuanced_combat.content.recipe.ExplosiveArrowRecipe;
import com.feliscape.nuanced_combat.content.recipe.PotionBundleRecipe;
import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.FireworkRocketRecipe;

import java.util.concurrent.CompletableFuture;

public class NCRecipeProvider extends RecipeProvider {

    public NCRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ExplosiveArrowItem.forStrength(3))
                .requires(Items.ARROW)
                .requires(Items.GUNPOWDER)
                .unlockedBy(getHasName(Items.GUNPOWDER), has(Items.GUNPOWDER))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, NuancedCombatItems.WINGED_ARROW.get(), 4)
                .pattern(" # ")
                .pattern("F/F")
                .pattern(" F ")
                .define('#', Items.AMETHYST_SHARD)
                .define('/', Items.STICK)
                .define('F', Items.FEATHER)
                .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, NuancedCombatItems.PRISMARINE_ARROW.get(), 4)
                .pattern("#")
                .pattern("/")
                .pattern("F")
                .define('#', Items.PRISMARINE_SHARD)
                .define('/', Items.STICK)
                .define('F', Items.FEATHER)
                .unlockedBy(getHasName(Items.PRISMARINE_SHARD), has(Items.PRISMARINE_SHARD))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, NuancedCombatItems.BOOMERANG.get())
                .pattern("PPI")
                .pattern("  P")
                .pattern("  P")
                .define('P', ItemTags.PLANKS)
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(recipeOutput);
        /*ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, NuancedCombatItems.POTION_BUNDLE)
                .pattern(" S ")
                .pattern("ooo")
                .pattern(" B ")
                .define('S', Items.STRING)
                .define('B', Items.BLAZE_POWDER)
                .define('o', Items.POTION)
                .unlockedBy("has_potion", has(Items.POTION))
                .save(recipeOutput);*/

        //SpecialRecipeBuilder.special(ExplosiveArrowRecipe::new).save(recipeOutput, NuancedCombat.location("explosive_arrow"));
        SpecialRecipeBuilder.special(PotionBundleRecipe::new).save(recipeOutput, NuancedCombat.location("potion_bundle"));
    }
}