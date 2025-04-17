package com.feliscape.nuanced_combat.content.entity.projectile;

import com.feliscape.nuanced_combat.data.damagetypes.NuancedCombatDamageSources;
import com.feliscape.nuanced_combat.registry.NuancedCombatEntityTypes;
import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import com.feliscape.nuanced_combat.util.MathUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class Boomerang extends AbstractArrow {
    double spin;
    double oSpin;

    int returnDelay;

    private static final int RETURN_DELAY_TIME = 20;

    public Boomerang(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        returnDelay = RETURN_DELAY_TIME;
    }
    public Boomerang(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(NuancedCombatEntityTypes.BOOMERANG.get(), owner, level, pickupItemStack, firedFromWeapon);
        returnDelay = RETURN_DELAY_TIME;
    }
    public Boomerang(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(NuancedCombatEntityTypes.BOOMERANG.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
        returnDelay = RETURN_DELAY_TIME;
    }

    @Override
    public void tick() {
        oSpin = spin;

        super.tick();

        if (!inGround){
            spin = (spin + 35F) % 360F;
        }

        if (returnDelay > 0) {
            returnDelay--;
        } else{
            var owner = this.getOwner();
            if (owner != null) {
                Vec3 ownerPosition = new Vec3(owner.getX(), owner.getY(0.5D), owner.getZ());
                Vec3 targetVelocity = ownerPosition.subtract(this.position()).normalize().scale(1.0D);
                Vec3 velocity = MathUtil.moveToward(this.getDeltaMovement(), targetVelocity, 0.1D);
                this.setDeltaMovement(velocity.x, velocity.y, velocity.z);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (this.getOwner() instanceof LivingEntity living && !living.hasLineOfSight(this)) return;
        this.inGround = false;
        returnDelay = 0;
        this.setDeltaMovement(this.getDeltaMovement().reverse());
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        float damage = 6.0F;
        Entity owner = this.getOwner();
        if (entity == owner) return;

        DamageSource damagesource = NuancedCombatDamageSources.boomerang(this, (Entity)(owner == null ? this : owner));
        if (this.level() instanceof ServerLevel serverlevel) {
            damage = EnchantmentHelper.modifyDamage(serverlevel, this.getWeaponItem(), entity, damagesource, damage);
        }

        if (entity.hurt(damagesource, damage)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (this.level() instanceof ServerLevel serverlevel1) {
                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverlevel1, entity, damagesource, this.getWeaponItem());
            }

            if (entity instanceof LivingEntity livingentity) {
                this.doKnockback(livingentity, damagesource);
                this.doPostHurtEffects(livingentity);
            }
        }
        if (returnDelay > 0) {
            returnDelay = 0;
            this.setDeltaMovement(this.getDeltaMovement().reverse());
        }
    }

    @Override
    public void playerTouch(Player entity) {
        if (!this.level().isClientSide && ((this.inGround || this.isNoPhysics()) && this.shakeTime <= 0) || (returnDelay <= 0 && entity == this.getOwner())) {
            if (this.tryPickup(entity)) {
                entity.take(this, 1);
                this.discard();
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putDouble("spin", this.getSpin());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSpin(compound.getDouble("spin"));
    }

    public double getSpin() {
        return spin;
    }

    public double getOldSpin() {
        return oSpin;
    }

    public void setSpin(double spin) {
        this.spin = spin;
    }

    @Override
    protected double getDefaultGravity() {
        return 0.0D;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(NuancedCombatItems.BOOMERANG.get());
    }

    @Override
    protected boolean tryPickup(Player player) {
        return super.tryPickup(player);
    }
}
