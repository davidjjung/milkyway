package com.davigj.milky_way.core.other;

import com.davigj.milky_way.core.MilkyWay;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MWItemTags {
    public static final TagKey<Item> BUCKETS = itemTag("buckets");

    private static TagKey<Item> itemTag(String name) {
        return TagUtil.itemTag(MilkyWay.MOD_ID, name);
    }
}
