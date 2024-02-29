package com.davigj.milky_way.core.data.server.tags;

import com.davigj.milky_way.core.MilkyWay;
import com.davigj.milky_way.core.other.MWConstants;
import com.davigj.milky_way.core.other.MWEntityTypeTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MWEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public MWEntityTypeTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MilkyWay.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(MWEntityTypeTags.BUCKET_MILKABLE)
                .addOptional(MWConstants.COW);
        this.tag(MWEntityTypeTags.WORSE_BUCKET_MILKABLE).addOptional(MWConstants.YAK).addOptional(MWConstants.MOOBLOOM)
                .addOptional(MWConstants.MOOSHROOM).addOptional(MWConstants.GEEP).addOptional(MWConstants.GOAT);
        this.tag(MWEntityTypeTags.BOWL_MILKABLE).addOptional(MWConstants.MOOSHROOM);
        this.tag(MWEntityTypeTags.BOTTLE_MILKABLE).addOptional(MWConstants.COW);

        this.tag(MWEntityTypeTags.POKEMON).addOptional(MWConstants.POKEMON);
    }
}
