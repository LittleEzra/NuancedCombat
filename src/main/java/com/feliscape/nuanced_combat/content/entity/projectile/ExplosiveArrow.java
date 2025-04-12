package com.feliscape.nuanced_combat.content.entity.projectile;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.item.ExplosiveArrowItem;
import com.feliscape.nuanced_combat.content.world.OwnedExplosionDamageCalculator;
import com.feliscape.nuanced_combat.registry.NuancedCombatEntityTypes;
import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import net.minecraft.core.HolderSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;

public class ExplosiveArrow extends AbstractArrow {
    private final Predicate<Entity> damageEntityPredicate = (entity) -> entity != ExplosiveArrow.this.getOwner();

    public final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR =
            new OwnedExplosionDamageCalculator(false, true, Optional.of(1.5F), damageEntityPredicate, Optional.empty());
    public final ExplosionDamageCalculator MULTISHOT_EXPLOSION_DAMAGE_CALCULATOR =
            new OwnedExplosionDamageCalculator(false, true, Optional.of(0.5F), damageEntityPredicate, Optional.empty());

    boolean isFromMultishot;

    public ExplosiveArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ExplosiveArrow(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(NuancedCombatEntityTypes.EXPLOSIVE_ARROW.get(), owner, level, pickupItemStack, firedFromWeapon);
        if (pickupItemStack.is(NuancedCombatItems.EXPLOSIVE_ARROW))
            this.power = ExplosiveArrowItem.getStrength(pickupItemStack);

        var lookup = level.registryAccess().lookup(Registries.ENCHANTMENT);
        isFromMultishot = lookup.filter(enchantmentRegistryLookup -> firedFromWeapon != null && firedFromWeapon.getEnchantmentLevel(enchantmentRegistryLookup.getOrThrow(Enchantments.MULTISHOT)) > 0).isPresent();
    }

    protected int power = 1;

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("power", power);
        pCompound.putBoolean("isFromMultishot", isFromMultishot);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.power = pCompound.getInt("power");
        this.isFromMultishot = pCompound.getBoolean("isFromMultishot");
    }

    public void tick() {
        super.tick();
        this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!level().isClientSide()){
            Entity entity = result.getEntity();
            /*Vec3 intendedExplosionLocation = new Vec3(this.getX(), entity.getY(), this.getZ());
            Vec3 toArrow = intendedExplosionLocation.subtract(entity.position()).normalize();*/
            this.level().explode(
                this,
                Explosion.getDefaultDamageSource(level(), this),
                isFromMultishot ? MULTISHOT_EXPLOSION_DAMAGE_CALCULATOR : EXPLOSION_DAMAGE_CALCULATOR,
                this.getX(),
                (entity.getY() + this.getY()) / 2.0D,
                    this.getZ(),
                (float)this.power,
                false,
                Level.ExplosionInteraction.NONE);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        NuancedCombat.LOGGER.debug(pickup.toString());

        if (!level().isClientSide()){
            this.level().explode(
                    this,
                    Explosion.getDefaultDamageSource(level(), this),
                    isFromMultishot ? MULTISHOT_EXPLOSION_DAMAGE_CALCULATOR : EXPLOSION_DAMAGE_CALCULATOR,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    (float)this.power,
                    false,
                    Level.ExplosionInteraction.NONE);
            this.discard();
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        ItemStack itemStack = new ItemStack(NuancedCombatItems.EXPLOSIVE_ARROW.get());
        ExplosiveArrowItem.setStrength(itemStack, this.power);

        return itemStack;
    }
}
