package com.feliscape.nuanced_combat.client.data;

import com.feliscape.nuanced_combat.NuancedCombat;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StunnedEntityData {
    private static final ArrayList<Integer> stunnedEntityIds = Lists.newArrayList();

    public static void addEntity(int id){
        if (stunnedEntityIds.contains(id)){
            NuancedCombat.LOGGER.warn("Entity ID {} already included in stunned entities!", id);
            logList();
            return;
        }
        stunnedEntityIds.add(id);
        //logList();
    }

    public static void removeEntity(int id){
        if (!stunnedEntityIds.contains(id)){
            NuancedCombat.LOGGER.warn("Tried to remove non-existent entity ID {} from stunned entities", id);
            //logList();
            return;
        }
        //stunnedEntityIds.remove(id);
        for (int i = 0; i < stunnedEntityIds.size(); i++){
            if (stunnedEntityIds.get(i) == id){
                stunnedEntityIds.remove(i);
                break;
            }
        }
        //logList();
    }

    public static boolean isEntityStunned(int id){
        return stunnedEntityIds.contains(id);
    }
    private static void logList(){
        NuancedCombat.LOGGER.debug("StunnedEntityData START");
        for (int i : stunnedEntityIds){
            NuancedCombat.LOGGER.debug("{}", i);
        }
        NuancedCombat.LOGGER.debug("StunnedEntityData END");
    }
}
