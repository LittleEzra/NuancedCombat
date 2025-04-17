package com.feliscape.nuanced_combat.content.block.entity;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.registry.NuancedCombatBlockEntities;
import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.slf4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class HeavyLockerBlockEntity extends BlockEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private Set<UUID> currentPlayerUUIDs = new ObjectLinkedOpenHashSet<>();
    private Set<UUID> currentEnemyUUIDs = new ObjectLinkedOpenHashSet<>();
    private Set<Player> currentPlayers = new ObjectLinkedOpenHashSet<>();
    private boolean active;
    private int tickCount;

    public HeavyLockerBlockEntity(BlockPos pos, BlockState blockState) {
        super(NuancedCombatBlockEntities.HEAVY_LOCKER.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("currentPlayers", UUIDUtil.CODEC_LINKED_SET.encodeStart(registries.createSerializationContext(NbtOps.INSTANCE), currentPlayerUUIDs).getOrThrow());
        tag.put("currentEnemyUUIDs", UUIDUtil.CODEC_LINKED_SET.encodeStart(registries.createSerializationContext(NbtOps.INSTANCE), currentPlayerUUIDs).getOrThrow());
        tag.putBoolean("active", active);
        tag.putInt("tickCount", tickCount);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        DynamicOps<Tag> dynamicops = registries.createSerializationContext(NbtOps.INSTANCE);
        currentPlayerUUIDs.clear();
        UUIDUtil.CODEC_LINKED_SET.parse(dynamicops, tag.get("currentPlayers")).resultOrPartial(LOGGER::error).ifPresent(currentPlayerUUIDs::addAll);
        this.active = tag.getBoolean("active");
        this.tickCount = tag.getInt("tickCount");
        getCurrentPlayers();
    }

    private void getCurrentPlayers() {
        currentPlayers.clear();
        for (UUID uuid : currentPlayerUUIDs){
            var player = this.level.getPlayerByUUID(uuid);
            if (player != null)
                currentPlayers.add(player);
        }
    }

    public void tick(Level level, BlockPos blockPos, BlockState state){
        boolean wasActive = active;

        if (this.tickCount % 100 == 0){
            List<Player> players = level.getEntitiesOfClass(Player.class, AABB.unitCubeFromLowerCorner(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ())).inflate(8.0D));
            if (!players.isEmpty()){
                this.active = true;
                for (Player player : players){
                    currentPlayers.add(player);
                    currentPlayerUUIDs.add(player.getUUID());
                }
            }
        }

        if (active && !wasActive){
            spawnEnemies(1);
        }

        Vec3 position = blockPos.getCenter();

        if (this.active && this.tickCount % 20 == 0) {
            getCurrentPlayers();
            for (Player player : currentPlayers) {
                if (position.subtract(player.position()).length() > 24.0D) {
                    player.push(position.subtract(player.position()).normalize().scale(2.0D));
                    if (player instanceof ServerPlayer serverplayer) {
                        serverplayer.connection.send(new ClientboundSetEntityMotionPacket(serverplayer));
                    }
                }
            }
        }


        tickCount++;
    }

    private void spawnEnemies(int amount) {
        if (this.level == null) {
            LOGGER.warn("HeavyLocker trying to spawn enemies with no level");
            return;
        }

        if (!this.level.isClientSide) {
            for (int i = 0; i < amount; i++) {
                Zombie enemy = new Zombie(this.level);
                enemy.moveTo(this.getBlockPos().getCenter().add(0.0D, 1.0D, 0.0D));
                this.level.addFreshEntity(enemy);
            }
        }
    }
}
