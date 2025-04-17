package com.feliscape.nuanced_combat.content.entity.projectile;

import com.feliscape.nuanced_combat.registry.NuancedCombatEntityTypes;
import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import javax.annotation.Nullable;
import java.util.List;

public class PrismarineArrow extends AbstractArrow {
    public PrismarineArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public PrismarineArrow(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(NuancedCombatEntityTypes.PRISMARINE_ARROW.get(), owner, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    protected float getWaterInertia() {
        return 0.96F;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(NuancedCombatItems.PRISMARINE_ARROW.get());
    }
}
