package com.feliscape.nuanced_combat.util;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public class RandomUtil {
    public static Vec3 randomPositionOnSphere(RandomSource source, double radius){
        // From https://extremelearning.com.au/how-to-generate-uniformly-random-points-on-n-spheres-and-n-balls/
        double u = source.nextDouble();
        double v = source.nextDouble();
        double theta = 2 * Math.PI * u;
        double phi = Math.acos(2.0D * v - 1.0D);

        double x = Math.sin(theta) * Math.cos(phi);
        double y = Math.sin(theta) * Math.sin(phi);
        double z = Math.cos(theta);
        return new Vec3(x * radius, y * radius, z * radius);
    }
}
