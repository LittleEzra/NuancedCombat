package com.feliscape.nuanced_combat.content.entity.projectile;

import com.feliscape.nuanced_combat.content.item.ExplosiveArrowItem;
import com.feliscape.nuanced_combat.data.damagetypes.NuancedCombatDamageSources;
import com.feliscape.nuanced_combat.registry.NuancedCombatDataAttachments;
import com.feliscape.nuanced_combat.registry.NuancedCombatEntityTypes;
import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import javax.annotation.Nullable;

public class SteelNeedle extends AbstractArrow {
    public SteelNeedle(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public SteelNeedle(Level level, LivingEntity owner, ItemStack pickupItemStack) {
        super(NuancedCombatEntityTypes.STEEL_NEEDLE.get(), owner, level, pickupItemStack, null);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (result.getEntity() instanceof LivingEntity living){
            living.hurt(NuancedCombatDamageSources.ironNeedle(this.getOwner(), this), 2.0F);
            living.getData(NuancedCombatDataAttachments.STEEL_NEEDLES).addNeedle();
            this.discard();
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(NuancedCombatItems.STEEL_NEEDLE.get());
    }
}
