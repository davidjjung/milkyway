package com.davigj.milky_way.core.other;

import com.davigj.milky_way.core.MilkyWay;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MWDataMapUtil {
    private final static Logger LOGGER = LogManager.getLogger(MilkyWay.MOD_ID);

    public record MilkData(List<ItemData> items) {
        public static final Codec<MilkData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.list(ItemData.CODEC).fieldOf("items").forGetter(MilkData::items)
        ).apply(instance, MilkData::new));

        public record ItemData(String item, int seconds, String modLoaded, byte substanceIndex) {
            public static final Codec<ItemData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("item").forGetter(ItemData::item),
                    Codec.INT.fieldOf("seconds").forGetter(ItemData::seconds),
                    Codec.STRING.optionalFieldOf("mod_loaded", "minecraft").forGetter(ItemData::modLoaded),
                    Codec.BYTE.optionalFieldOf("milk_type", (byte) 0).forGetter(ItemData::substanceIndex)
            ).apply(instance, ItemData::new));
        }
    }

    public static final DataMapType<EntityType<?>, MilkData> MILK_DATA = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath("milky_way", "milk_harvest"), Registries.ENTITY_TYPE, MilkData.CODEC
    ).build();
}
