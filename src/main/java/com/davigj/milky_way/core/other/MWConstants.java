package com.davigj.milky_way.core.other;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.world.entity.Entity;

public class MWConstants {
    public static boolean isCobblemon(Entity entity) {
        return entity instanceof PokemonEntity;
    }
}
