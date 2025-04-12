package com.feliscape.nuanced_combat.content.item;

import com.feliscape.nuanced_combat.registry.NuancedCombatSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class WavehammerItem extends Item {
    public WavehammerItem(Properties properties) {
        super(properties);
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    public static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 7.0D,
                        AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -3D,
                        AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        if (livingEntity instanceof ServerPlayer player) {
            int charge = this.getUseDuration(stack, livingEntity) - timeLeft;

            if (charge < 20) return;

            level.levelEvent(2013, player.getOnPos(), 750);
            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.MACE_SMASH_GROUND, player.getSoundSource(), 1.0F, 1.0F);

            double range = 3.0D + Mth.clamp((double) charge / 60D, 0.0D, 1.0D) * 5.0D;
            List<LivingEntity> enemiesToHurt = level.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(range, 0.5D, range),
                    (entity) -> entity != livingEntity && !entity.isAlliedTo(livingEntity));

            for (LivingEntity toHurt : enemiesToHurt) {
                Vec3 direction = toHurt.position().subtract(livingEntity.position());
                if (direction.length() <= 4.0D) {
                    toHurt.hurt(level.damageSources().mobAttack(livingEntity), 8.0F);
                    //double knockback = 0.3D * (4.0D - direction.length());
                    //toHurt.push(direction.x * knockback, 0.5D, direction.z * knockback);
                    knockback(level, player, toHurt);
                }
            }
            stack.hurtAndBreak(1, livingEntity, EquipmentSlot.MAINHAND);
            player.getCooldowns().addCooldown(this, 60);
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        int charge = this.getUseDuration(stack, livingEntity) - remainingUseDuration;
        if (charge == 20){
            level.playSound((Player) null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                    NuancedCombatSoundEvents.ITEM_CHARGED.get(), livingEntity.getSoundSource(), 1.0F, 1.0F);
        }
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (isTooDamagedToUse(itemstack)) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }


    private static void knockback(Level level, Player player, LivingEntity entity) {
        Vec3 vec3 = entity.position().subtract(player.position());
        double d0 = getKnockbackPower(player, entity);
        Vec3 vec31 = vec3.normalize().scale(d0);
        if (d0 > 0.0) {
            entity.push(vec31.x, 0.699999988079071, vec31.z);
            if (entity instanceof ServerPlayer) {
                ServerPlayer serverplayer = (ServerPlayer)entity;
                serverplayer.connection.send(new ClientboundSetEntityMotionPacket(serverplayer));
            }
        }
    }

    private static double getKnockbackPower(Player player, LivingEntity entity) {
        double distance = player.distanceTo(entity);
        return (4.0D - distance) * 0.699999988079071 * (1.0 - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
    }

    private static boolean isTooDamagedToUse(ItemStack stack) {
        return stack.getDamageValue() >= stack.getMaxDamage() - 1;
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }
}
