package com.feliscape.nuanced_combat.content.item;

import com.feliscape.nuanced_combat.content.component.PotionBundleContents;
import com.feliscape.nuanced_combat.registry.NuancedCombatComponents;
import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import com.feliscape.nuanced_combat.registry.NuancedCombatPotions;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

import java.util.List;

public class PotionBundleItem extends Item implements ProjectileItem {
    private static final List<Holder<Potion>> AVAILABLE_POTIONS = List.of(
            Potions.WEAKNESS,
            Potions.POISON,
            Potions.HARMING,
            Potions.SLOWNESS,
            NuancedCombatPotions.COMBUSTION
    );

    public PotionBundleItem(Properties properties) {
        super(properties);
    }

    public static ItemStack withContents(PotionBundleContents potionBundleContents) {
        ItemStack itemStack = new ItemStack(NuancedCombatItems.POTION_BUNDLE.get());
        itemStack.set(NuancedCombatComponents.POTION_BUNDLE_CONTENTS, potionBundleContents);
        return itemStack;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0xce2aef;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round((float)getUses(stack) * 13.0F / (float) getMaxUses(stack));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return stack.has(NuancedCombatComponents.POTION_BUNDLE_USES);
    }


    private static int getUses(ItemStack stack) {
        return Mth.clamp((Integer)stack.getOrDefault(NuancedCombatComponents.POTION_BUNDLE_USES, 0), 0, getMaxUses(stack));
    }

    private static int getMaxUses(ItemStack stack) {
        return (Integer)stack.getOrDefault(NuancedCombatComponents.MAX_POTION_BUNDLE_USES, 0);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (player.isShiftKeyDown() && itemstack.has(NuancedCombatComponents.POTION_BUNDLE_USES) && hand == InteractionHand.MAIN_HAND){
            if (getUses(itemstack) < getMaxUses(itemstack)){
                ItemStack offhandItemStack = player.getItemInHand(InteractionHand.OFF_HAND);
                if (offhandItemStack.is(Items.BLAZE_POWDER)){
                    offhandItemStack.consume(1, player);
                    int uses = getUses(itemstack);
                    itemstack.set(NuancedCombatComponents.POTION_BUNDLE_USES, Math.min(uses + 5, getMaxUses(itemstack)));
                    return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                }
            }
        }
        else if (!itemstack.has(NuancedCombatComponents.POTION_BUNDLE_USES) ||  getUses(itemstack) > 0){
            if (!level.isClientSide) {
                ThrownPotion thrownpotion = new ThrownPotion(level, player);
                thrownpotion.setItem(getRandomSplashPotion(itemstack, level.getRandom()));
                thrownpotion.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);
                level.addFreshEntity(thrownpotion);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            if (itemstack.has(NuancedCombatComponents.POTION_BUNDLE_USES) && !player.hasInfiniteMaterials()) {
                int uses = getUses(itemstack);
                itemstack.set(NuancedCombatComponents.POTION_BUNDLE_USES, uses - 1);
            }

            player.getCooldowns().addCooldown(this, 10);
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        ThrownPotion thrownpotion = new ThrownPotion(level, position.x(), position.y(), position.z());
        thrownpotion.setItem(getRandomSplashPotion(itemStack, level.getRandom()));
        return thrownpotion;
    }

    private ItemStack getRandomSplashPotion(ItemStack itemStack, RandomSource randomSource){
        if (itemStack.has(NuancedCombatComponents.POTION_BUNDLE_CONTENTS)){
            List<Holder<Potion>> potions = itemStack.get(NuancedCombatComponents.POTION_BUNDLE_CONTENTS).potions();
            return PotionContents.createItemStack(Items.SPLASH_POTION, potions.get(randomSource.nextInt(potions.size())));
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        tooltipComponents.add(Component.literal( getUses(stack) + "/" + getMaxUses(stack)).withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.empty());
        tooltipComponents.add(Component.translatable("item.nuanced_combat.potion_bundle.effects_header")
                .withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        PotionBundleContents contents = stack.get(NuancedCombatComponents.POTION_BUNDLE_CONTENTS);
        if (contents != null){
            contents.addToTooltip(context, tooltipComponents::add, tooltipFlag);
        }
    }
}
