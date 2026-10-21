package com.davigj.milky_way.core.other;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class MWConstants {
    public static boolean isCobblemon(Entity entity) {
        return BuiltInRegistries.ENTITY_TYPE
                .getKey(entity.getType())
                .equals(ResourceLocation.fromNamespaceAndPath("cobblemon", "pokemon"));
    }
}
