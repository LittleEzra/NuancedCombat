package com.feliscape.nuanced_combat.content.block;

import com.feliscape.nuanced_combat.content.block.entity.HeavyLockerBlockEntity;
import com.feliscape.nuanced_combat.registry.NuancedCombatBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class HeavyLockerBlock extends BaseEntityBlock {
    public static final MapCodec<HeavyLockerBlock> CODEC = simpleCodec(HeavyLockerBlock::new);

    public HeavyLockerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new HeavyLockerBlockEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()){
            return null;
        }

        return createTickerHelper(blockEntityType, NuancedCombatBlockEntities.HEAVY_LOCKER.get(),
                ((level1, pos, state1, blockEntity) -> blockEntity.tick(level1, pos, state1)));
    }
}
