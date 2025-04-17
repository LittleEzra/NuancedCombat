package com.feliscape.nuanced_combat.content.block.entity.heavy_locker;

import net.minecraft.util.StringRepresentable;

public enum HeavyLockerState implements StringRepresentable {
    INACTIVE("inactive"),
    WAITING_FOR_PLAYERS("waiting_for_players"),
    SPAWNING("spawning"),
    IDLE("idle"),
    WAITING_UNTIL_NEXT_WAVE("waiting_until_next_wave");

    private final String name;

    HeavyLockerState(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
