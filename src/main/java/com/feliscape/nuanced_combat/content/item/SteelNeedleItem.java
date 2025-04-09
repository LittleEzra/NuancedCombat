package com.feliscape.nuanced_combat.content.item;

import com.feliscape.nuanced_combat.content.entity.projectile.SteelNeedle;
import com.feliscape.nuanced_combat.content.entity.projectile.ThrownIronNeedle;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SteelNeedleItem extends Item {

    public SteelNeedleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        if (!level.isClientSide){
            SteelNeedle steelNeedle = new SteelNeedle(level, player, itemstack.copyWithCount(1));
            steelNeedle.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0.0F);
            level.addFreshEntity(steelNeedle);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
