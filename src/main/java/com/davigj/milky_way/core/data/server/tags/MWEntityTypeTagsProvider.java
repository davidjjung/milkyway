package com.davigj.milky_way.core.data.server.tags;

import com.davigj.milky_way.core.MilkyWay;
import com.davigj.milky_way.core.other.MWConstants;
import com.davigj.milky_way.core.other.MWEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class MWEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public MWEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MilkyWay.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(MWEntityTypeTags.BUCKET_MILKABLE)
                .add(EntityType.COW);
        this.tag(MWEntityTypeTags.WORSE_BUCKET_MILKABLE).add(EntityType.MOOSHROOM).add(EntityType.GOAT)
                .addOptional(MWConstants.YAK).addOptional(MWConstants.MOOBLOOM).addOptional(MWConstants.GEEP);
        this.tag(MWEntityTypeTags.BOWL_MILKABLE).add(EntityType.MOOSHROOM);
//        this.tag(MWEntityTypeTags.BOTTLE_MILKABLE).add(EntityType.COW);

        this.tag(MWEntityTypeTags.POKEMON).addOptional(MWConstants.POKEMON);
    }
}
