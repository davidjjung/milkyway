package com.davigj.milky_way.core.other;

import com.davigj.milky_way.core.MilkyWay;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class MWEntityTypeTags {
    public static final TagKey<EntityType<?>> BUCKET_MILKABLE = entityTypeTag("bucket_milkable");
    public static final TagKey<EntityType<?>> WORSE_BUCKET_MILKABLE = entityTypeTag("worse_bucket_milkable");
    public static final TagKey<EntityType<?>> BOWL_MILKABLE = entityTypeTag("bowl_milkable");
    public static final TagKey<EntityType<?>> BOTTLE_MILKABLE = entityTypeTag("bottle_milkable");

    public static final TagKey<EntityType<?>> POKEMON = entityTypeTag("pokemon");

    private static TagKey<EntityType<?>> entityTypeTag(String name) {
        return TagUtil.entityTypeTag(MilkyWay.MOD_ID, name);
    }
}
