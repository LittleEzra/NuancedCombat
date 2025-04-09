package com.feliscape.nuanced_combat.content.entity.projectile;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.data.damagetypes.NuancedCombatDamageSources;
import com.feliscape.nuanced_combat.registry.NuancedCombatEntityTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ThrownIronNeedle extends Projectile {
    @Nullable
    private Entity stuckInEntity;
    private static final EntityDataAccessor<Integer> DATA_HIT_ENTITY = SynchedEntityData.defineId(ThrownIronNeedle.class, EntityDataSerializers.INT);

    public ThrownIronNeedle(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownIronNeedle(Level level, LivingEntity shooter) {
        super(NuancedCombatEntityTypes.IRON_NEEDLE.get(), level);
        this.setOwner(shooter);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_HIT_ENTITY, 0);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.stuckInEntity != null){
            if (!this.stuckInEntity.isRemoved() && this.stuckInEntity.level().dimension() == this.level().dimension()) {
                if (this.tickCount % 10 == 0)
                    this.stuckInEntity.hurt(NuancedCombatDamageSources.bleeding(level()), 1F);
                this.setPos(this.stuckInEntity.getX(), this.stuckInEntity.getY(0.8), this.stuckInEntity.getZ());
            } else {
                this.setHitEntity((Entity)null);
            }
        }

    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        Entity owner = this.getOwner();
        if (this.stuckInEntity != null) return;

        DamageSource damagesource = NuancedCombatDamageSources.ironNeedle((Entity)(owner != null ? owner : this), this);
        int damage = 4;

        if (entity.hurt(damagesource, (float)damage)){
            this.setHitEntity(entity);
        }
    }

    @Nullable
    public Player getPlayerOwner() {
        Entity entity = this.getOwner();
        return entity instanceof Player ? (Player)entity : null;
    }

    @Override
    public boolean canUsePortal(boolean allowPassengers) {
        return false;
    }

    /*public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity p_entity) {
        Entity entity = this.getOwner();
        return new ClientboundAddEntityPacket(this, p_entity, entity == null ? this.getId() : entity.getId());
    }

    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        if (this.getPlayerOwner() == null) {
            int i = packet.getData();
            NuancedCombat.LOGGER.error("Failed to recreate fishing hook on client. {} (id: {}) is not a valid owner.", this.level().getEntity(i), i);
            this.kill();
        }

    }*/

    private void setHitEntity(@Nullable Entity stuckInEntity) {
        this.stuckInEntity = stuckInEntity;
        this.getEntityData().set(DATA_HIT_ENTITY, stuckInEntity == null ? 0 : stuckInEntity.getId() + 1);
    }
}
