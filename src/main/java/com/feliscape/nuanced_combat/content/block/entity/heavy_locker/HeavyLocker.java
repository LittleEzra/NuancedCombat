package com.feliscape.nuanced_combat.content.block.entity.heavy_locker;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.entity.trialspawner.PlayerDetector;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawner;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class HeavyLocker {

    public Codec<HeavyLocker> codec(){
        return RecordCodecBuilder.create(inst ->
                inst.group(
                        SpawnData.CODEC.fieldOf("next_spawn_data").forGetter(locker -> locker.nextSpawnData),
                        UUIDUtil.CODEC_SET.fieldOf("current_mobs").forGetter(locker -> locker.currentMobs)
                        )
                        .apply(inst, (nextSpawnData, currentMobs) ->
                                new HeavyLocker(this.entitySelector, this.playerDetector, nextSpawnData, currentMobs))
        );
    }

    private PlayerDetector playerDetector;
    private final PlayerDetector.EntitySelector entitySelector;

    private static final SpawnData ZOMBIE = new SpawnData();

    protected final Set<UUID> currentMobs = Set.of();
    @Nullable
    protected SpawnData nextSpawnData;

    public HeavyLocker(PlayerDetector.EntitySelector entitySelector, PlayerDetector playerDetector, SpawnData nextSpawnData, Set<UUID> currentMobs) {
        this.currentMobs.addAll(currentMobs);
        this.nextSpawnData = nextSpawnData;
        this.entitySelector = entitySelector;
        this.playerDetector = playerDetector;
    }

    public PlayerDetector getPlayerDetector() {
        return this.playerDetector;
    }

    public PlayerDetector.EntitySelector getEntitySelector() {
        return this.entitySelector;
    }

    public void spawnEnemy(Level level, SpawnData spawnData){
        CompoundTag compoundtag = spawnData.entityToSpawn();
        ListTag listtag = compoundtag.getList("Pos", 6);
        Optional<EntityType<?>> optional = EntityType.by(compoundtag);
    }
}
