package com.feliscape.nuanced_combat.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.SpriteSet;

public class ThroughsightFlameParticle extends BaseAshSmokeParticle {
    protected ThroughsightFlameParticle(ClientLevel level, double x, double y, double z, float xSeedMultiplier, float ySpeedMultiplier, float zSpeedMultiplier, double xSpeed, double ySpeed, double zSpeed, float quadSizeMultiplier, SpriteSet sprites, float rColMultiplier, int lifetime, float gravity, boolean hasPhysics) {
        super(level, x, y, z, xSeedMultiplier, ySpeedMultiplier, zSpeedMultiplier, xSpeed, ySpeed, zSpeed, quadSizeMultiplier, sprites, rColMultiplier, lifetime, gravity, hasPhysics);
    }
}
