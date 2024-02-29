package com.davigj.milky_way.core.data.server.tags;

import com.davigj.milky_way.core.MilkyWay;
import com.davigj.milky_way.core.other.MWConstants;
import com.davigj.milky_way.core.other.MWEntityTypeTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MWEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public MWEntityTypeTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MilkyWay.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(MWEntityTypeTags.BUCKET_MILKABLE)
                .add(EntityType.COW);
        this.tag(MWEntityTypeTags.WORSE_BUCKET_MILKABLE).addOptional(MWConstants.YAK).addOptional(MWConstants.MOOBLOOM)
                .add(EntityType.MOOSHROOM).addOptional(MWConstants.GEEP).add(EntityType.GOAT);
        this.tag(MWEntityTypeTags.BOWL_MILKABLE).add(EntityType.MOOSHROOM);
        this.tag(MWEntityTypeTags.BOTTLE_MILKABLE).add(EntityType.COW);

        this.tag(MWEntityTypeTags.POKEMON).addOptional(MWConstants.POKEMON);
    }
}
