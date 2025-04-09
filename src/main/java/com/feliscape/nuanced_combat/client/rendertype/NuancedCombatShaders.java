package com.feliscape.nuanced_combat.client.rendertype;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NuancedCombatShaders {
    private static final List<ShaderInstance> PROGRAMS = new ArrayList();

    public static ShaderInstance rendertypeGhostEntity;

    public static void reloadShaders(ResourceProvider resourceProvider){
        rendertypeGhostEntity = register(resourceProvider, "rendertype_ghost_entity", DefaultVertexFormat.POSITION);
    }

    public static List<ShaderInstance> getProgramsToPreload() {
        return PROGRAMS;
    }
    private static ShaderInstance register(ResourceProvider resourceProvider, String name, VertexFormat vertexFormat) {
        try {
            ShaderInstance shaderInstance = new ShaderInstance(resourceProvider, NuancedCombat.location("core/" + name), vertexFormat);
            PROGRAMS.add(shaderInstance);
            return shaderInstance;
        } catch (IOException exception){
            NuancedCombat.LOGGER.debug(exception.getMessage());
        }
        return null;
    }
}
