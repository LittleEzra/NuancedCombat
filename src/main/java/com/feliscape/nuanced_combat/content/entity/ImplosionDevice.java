package com.feliscape.nuanced_combat.content.entity;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.registry.NuancedCombatEntityTypes;
import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import com.feliscape.nuanced_combat.registry.NuancedCombatSoundEvents;
import com.feliscape.nuanced_combat.util.RandomUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.List;

public class ImplosionDevice extends Projectile {
    private static final EntityDataAccessor<Integer> DATA_CHARGE = SynchedEntityData.defineId(ImplosionDevice.class, EntityDataSerializers.INT);
    private ItemStack pickupItemStack;
    private boolean isCreative;
    private boolean isOnGround;

    public ImplosionDevice(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public ImplosionDevice(Level level, LivingEntity shooter, ItemStack pickupItemStack) {
        this(level, shooter.getX(), shooter.getEyeY() - 0.10000000149011612, shooter.getZ(), pickupItemStack);
        this.setOwner(shooter);
        this.pickupItemStack = pickupItemStack;
        this.isCreative = shooter.hasInfiniteMaterials();
    }

    public ImplosionDevice(Level level, double x, double y, double z, ItemStack pickupItemStack) {
        super(NuancedCombatEntityTypes.IMPLOSION_DEVICE.get(), level);
        this.setPos(x, y, z);
        this.pickupItemStack = pickupItemStack;
    }

    public boolean shouldRenderAtSqrDistance(double distance) {
        double d0 = this.getBoundingBox().getSize() * 4.0;
        if (Double.isNaN(d0)) {
            d0 = 4.0;
        }

        d0 *= 64.0;
        return distance < d0 * d0;
    }

    @Override
    protected void applyGravity() {
        if ((this.getCharge() > 0 && this.getCharge() < 310) || this.onGround()) return;
        super.applyGravity();
    }

    @Override
    public void tick() {
        super.tick();

        handleCharge();

        this.applyGravity();

        if (this.isOnGround) this.setDeltaMovement(getDeltaMovement().x, 0.0D, this.getDeltaMovement().z);
        Vec3 deltaMovement = this.getDeltaMovement();
        Vec3 position = this.position();
        Vec3 nextPosition = position.add(deltaMovement);

        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, hitresult)) {
            if (hitresult.getType() == HitResult.Type.BLOCK)
                nextPosition = hitresult.getLocation();
            this.hitTargetOrDeflectSelf(hitresult);
        } else{
            this.isOnGround = false;
        }


        if (getCharge() > 100 && getCharge() <= 300) {
            double offsetChargeScale = (double) (this.getCharge() - 100) / 200D;
            double range = 4.0D + offsetChargeScale * 4.0D;
            for (int i = 0; i < (4 + Mth.floor(offsetChargeScale * 8.0D)); i++) {
                Vec3 pos = RandomUtil.randomPositionOnSphere(this.level().getRandom(), range).add(this.position());
                Vec3 speed = this.position().subtract(pos);
                this.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                        pos.x, pos.y, pos.z,
                        speed.x, speed.y, speed.z);
            }
        }
        if (getCharge() == 300){
            for (int i = 0; i < 30; i++) {
                Vec3 speed = RandomUtil.randomPositionOnSphere(this.level().getRandom(), 1D).scale(0.5D);
                this.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                        this.getX(), this.getY(), this.getZ(),
                        speed.x, speed.y, speed.z);
            }
        }

        if (this.hasPhysics()) {
            this.updateRotation();
            this.setDeltaMovement(this.getDeltaMovement().scale(0.99D));
        }

        this.setPos(nextPosition);
        this.checkInsideBlocks();
    }

    private void handleCharge() {

        int charge = this.getCharge();

        if (charge > -1 && charge < 310) {

            if (charge == 0){
                this.playSound(NuancedCombatSoundEvents.IMPLOSION_DEVICE_CHARGING.get());
            }
            if (charge == 300){
                this.playSound(NuancedCombatSoundEvents.IMPLOSION_DEVICE_DISCHARGE.get());
            }

            charge++;

            double chargeScale = (double) (charge) / 300D;
            double t = easeOut(Math.min(chargeScale * 2.0D, 1.0D));
            this.setDeltaMovement(0.0D, -0.1D * (1.0D - t), 0.0D);

            if (charge > 100 && charge <= 300) {
                double offsetChargeScale = (double) (charge - 100) / 200D;
                double range = 4.0D + offsetChargeScale * 4.0D;
                double power = charge == 300 ? 0.5D : Math.max(0.1D, offsetChargeScale * 0.7D);

                List<Entity> entities = this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(range),
                        entity -> {
                            if (entity instanceof ImplosionDevice) return false;
                            if (entity instanceof Player player && player.getAbilities().flying) return false;
                            return entity.distanceTo(this) <= range;
                        });

                for (Entity entity : entities) {
                    Vec3 direction = this.position().subtract(entity.position()).normalize();
                    double distanceScale = (entity.distanceTo(this) / range + 1.0D) / 2.0D;
                    if (charge == 300) {
                        distanceScale = 1.0D;
                    }

                    entity.push(direction.x * power * distanceScale, direction.y * power * distanceScale, direction.z * power * distanceScale);
                }
            }

            this.setCharge(charge);
        }
    }

    private boolean hasPhysics() {
        return !(this.getCharge() > 0 && this.getCharge() < 310);
    }

    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(NuancedCombatItems.IMPLOSION_DEVICE.get());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.put("item", this.pickupItemStack.save(this.registryAccess()));
        compound.putInt("charge", this.getCharge());
        compound.putBoolean("isCreative", this.isCreative);
        compound.putBoolean("isOnGround", this.isOnGround);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (compound.contains("item", 10)) {
            this.setPickupItemStack(ItemStack.parse(this.registryAccess(), compound.getCompound("item")).orElse(this.getDefaultPickupItem()));
        } else {
            this.setPickupItemStack(this.getDefaultPickupItem());
        }
        this.setCharge((compound.getInt("charge")));
        this.isCreative = compound.getBoolean("isCreative");
        this.isOnGround = compound.getBoolean("isOnGround");
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (!this.ownedBy(player) || (this.getCharge() > 0 && this.getCharge() < 310)) return InteractionResult.PASS;

        ItemStack pickupStack = this.pickupItemStack == null || this.pickupItemStack.isEmpty() ? this.getDefaultPickupItem() : this.pickupItemStack;

        if (!isCreative) {
            ItemEntity itementity = this.spawnAtLocation(pickupStack, this.getBbHeight() * 0.5F);
            if (itementity != null) {
                itementity.setTarget(player.getUUID());
            }
        }
        this.discard();
        return InteractionResult.sidedSuccess(this.level().isClientSide());
    }

    @Override
    public float getPickRadius() {
        return 0.1F;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    protected double getDefaultGravity() {
        return 0.03;
    }

    protected void setPickupItemStack(ItemStack pickupItemStack) {
        if (!pickupItemStack.isEmpty()) {
            this.pickupItemStack = pickupItemStack;
        } else {
            this.pickupItemStack = this.getDefaultPickupItem();
        }
    }

    private double easeOut(double x) {
        return -Math.pow(x - 1.0D, 3.0D) + 1.0D;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (this.getCharge() < 0)
            this.setCharge(0);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.isOnGround = result.getDirection() == Direction.UP;
    }

    /*@Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        Vec3 deltaMovement = getDeltaMovement();
        if (result.getDirection() == Direction.WEST || result.getDirection() == Direction.EAST){
            this.setDeltaMovement(0.0D, deltaMovement.y, deltaMovement.z);
        } else if (result.getDirection() == Direction.NORTH || result.getDirection() == Direction.SOUTH){
            this.setDeltaMovement(deltaMovement.x, deltaMovement.y, 0.0D);
        } else if (result.getDirection() == Direction.DOWN || result.getDirection() == Direction.UP){
            this.setDeltaMovement(deltaMovement.x, 0.0D, deltaMovement.y);
        }
    }*/

    @Override
    public boolean isColliding(BlockPos pos, BlockState state) {
        return super.isColliding(pos, state);
    }

    @Override
    public boolean isPushable() {
        return hasPhysics();
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return hasPhysics();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_CHARGE, -1);
    }

    public int getCharge(){
        return this.entityData.get(DATA_CHARGE);
    }

    public void setCharge(int charge){
        this.entityData.set(DATA_CHARGE, charge);
    }
}
