package com.feliscape.nuanced_combat.util;

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
}
