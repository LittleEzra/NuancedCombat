package com.feliscape.nuanced_combat.content.item;

import com.feliscape.nuanced_combat.content.entity.projectile.ExplosiveArrow;
import com.feliscape.nuanced_combat.content.entity.projectile.WingedArrow;
import com.feliscape.nuanced_combat.registry.NuancedCombatComponents;
import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WingedArrowItem extends ArrowItem {
    public WingedArrowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
        return new WingedArrow(level, shooter, ammo.copyWithCount(1), weapon);
    }
}
