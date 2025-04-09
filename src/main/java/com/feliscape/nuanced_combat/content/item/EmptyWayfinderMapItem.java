package com.feliscape.nuanced_combat.content.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ComplexItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;

public class EmptyWayfinderMapItem extends ComplexItem {
    public EmptyWayfinderMapItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.success(itemstack);
        } else {
            itemstack.consume(1, player);
            player.awardStat(Stats.ITEM_USED.get(this));
            player.level().playSound((Player)null, player, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, player.getSoundSource(), 1.0F, 1.0F);
            ItemStack map = MapItem.create(level, player.getBlockX(), player.getBlockZ(), (byte)0, true, false);
            if (itemstack.isEmpty()) {
                return InteractionResultHolder.consume(map);
            } else {
                if (!player.getInventory().add(map.copy())) {
                    player.drop(map, false);
                }

                return InteractionResultHolder.consume(itemstack);
            }
        }
    }
}
