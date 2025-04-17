package com.feliscape.nuanced_combat.util;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class MathUtil {
    public static double wrapRadians(double value) {
        double d0 = value % Math.TAU;
        if (d0 >= Math.PI) {
            d0 -= Math.TAU;
        }

        if (d0 < -Math.PI) {
            d0 += Math.TAU;
        }

        return d0;
    }

    public static double moveToward(double from, double to, double maxDelta){
        if (Math.abs(to - from) < maxDelta){
            return to;
        } else{
            return from + Mth.sign(to - from) * maxDelta;
        }
    }
    public static Vec3 moveToward(Vec3 from, Vec3 to, double maxDelta){
        if (from.distanceTo(to) < maxDelta){
            return to;
        } else{
            return from.add(to.subtract(from).normalize().scale(maxDelta));
        }
    }
}
