package com.feliscape.nuanced_combat.content.item;

import com.feliscape.nuanced_combat.content.entity.projectile.PrismarineArrow;
import com.feliscape.nuanced_combat.content.entity.projectile.WingedArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class PrismarineArrowItem extends ArrowItem {
    public PrismarineArrowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
        return new PrismarineArrow(level, shooter, ammo.copyWithCount(1), weapon);
    }
}
