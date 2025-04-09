package com.feliscape.nuanced_combat.content.item;

import com.feliscape.nuanced_combat.content.entity.projectile.ExplosiveArrow;
import com.feliscape.nuanced_combat.registry.NuancedCombatComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExplosiveArrowItem extends ArrowItem {
    public ExplosiveArrowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
        return new ExplosiveArrow(level, shooter, ammo.copyWithCount(1), weapon);
    }

    public static int getStrength(ItemStack itemStack){
        var power = itemStack.get(NuancedCombatComponents.POWER);
        return power == null ? 1 : power;
    }

    public static void setStrength(ItemStack pStack, int power) {
        pStack.set(NuancedCombatComponents.POWER, power);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        var power = stack.get(NuancedCombatComponents.POWER);
        if (power != null) {
            tooltipComponents.add(Component.translatable("item.nuanced_combat.explosive_arrow.power", power)
                    .withStyle(ChatFormatting.GRAY));
        }
    }
}
