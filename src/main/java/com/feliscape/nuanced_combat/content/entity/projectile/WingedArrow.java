package com.feliscape.nuanced_combat.content.entity.projectile;

import com.feliscape.nuanced_combat.content.item.ExplosiveArrowItem;
import com.feliscape.nuanced_combat.registry.NuancedCombatEntityTypes;
import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class WingedArrow extends AbstractArrow {
    private int bouncesLeft = 3;

    public WingedArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public WingedArrow(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(NuancedCombatEntityTypes.WINGED_ARROW.get(), owner, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("bouncesLeft", bouncesLeft);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.bouncesLeft = compound.getInt("bouncesLeft");
    }

    @Override
    public byte getPierceLevel() {
        return (byte)(bouncesLeft) < super.getPierceLevel() ? super.getPierceLevel() : (byte)(bouncesLeft);
    }

    @Override
    public boolean isNoGravity() {
        return super.isNoGravity() || (this.getDeltaMovement().length() > 0.5D && bouncesLeft >= 3);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (bouncesLeft <= 0) return;
        Entity target = result.getEntity();
        Entity owner = this.getOwner();
        DamageSource damagesource = this.damageSources().arrow(this, (Entity)(owner != null ? owner : this));
        if (!this.level().isClientSide()) {

            List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(8.0D),
                    entity -> {
                        if (entity == owner || entity == target) return false;
                        if (!canHitEntity(entity)) return false;
                        if (!entity.hasLineOfSight(this)) return false;
                        return !entity.isInvulnerableTo(damagesource) && target.distanceTo(entity) <= 8.0D;
                    });

            if (entities.size() == 0){
                this.discard();
                return;
            }

            LivingEntity closestEntity = null;
            double bestDistance = 10000000D;
            for (LivingEntity entity : entities) {
                double distance = target.distanceTo(entity);
                if (distance < bestDistance) {
                    closestEntity = entity;
                    bestDistance = distance;
                }
            }

            if (closestEntity == null){
                this.discard();
                return;
            }

            double x = closestEntity.getX() - this.getX();
            double y = closestEntity.getY(0.75D) - this.getY();
            double z = closestEntity.getZ() - this.getZ();
            this.shoot(x, y, z, (float) this.getDeltaMovement().length() * 0.75F, 0.1F);
            bouncesLeft--;
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(NuancedCombatItems.WINGED_ARROW.get());
    }
}
