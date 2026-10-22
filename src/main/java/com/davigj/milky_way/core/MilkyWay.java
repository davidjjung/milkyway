package com.davigj.milky_way.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import java.util.HashMap;
import java.util.Map;

import static com.davigj.milky_way.core.other.MWDataMapUtil.MILK_DATA;

@Mod(MilkyWay.MOD_ID)
public class MilkyWay {
    public static final String MOD_ID = "milky_way";
    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);
    private static final Codec<Byte> BYTE_KEY_CODEC = Codec.STRING.comapFlatMap(
            key -> {
                try {
                    return DataResult.success(Byte.parseByte(key));
                } catch (NumberFormatException e) {
                    return DataResult.error(() -> "Invalid byte key: " + key);
                }
            },
            String::valueOf
    );

    private static final Codec<Map<Byte, Integer>> MILK_TIMERS_CODEC =
            Codec.unboundedMap(BYTE_KEY_CODEC, Codec.INT);

    public static final TrackedData<Map<Byte, Integer>> MILK_TIMERS =
            TrackedData.Builder.<Map<Byte, Integer>>create(
                    new StreamCodec<>() {
                        @Override
                        public void encode(RegistryFriendlyByteBuf buf, Map<Byte, Integer> value) {
                            buf.writeVarInt(value.size());
                            for (Map.Entry<Byte, Integer> entry : value.entrySet()) {
                                buf.writeByte(entry.getKey());
                                buf.writeVarInt(entry.getValue());
                            }
                        }

                        @Override
                        public Map<Byte, Integer> decode(RegistryFriendlyByteBuf buf) {
                            int size = buf.readVarInt();
                            Map<Byte, Integer> map = new HashMap<>(size);
                            for (int i = 0; i < size; i++) {
                                byte key = buf.readByte();
                                int val = buf.readVarInt();
                                map.put(key, val);
                            }
                            return map;
                        }
                    },
                    () -> new HashMap<>(Map.of((byte) 0, 0))
            ).enableSaving(MILK_TIMERS_CODEC.fieldOf("MilkTimers")).build();

    public MilkyWay(IEventBus bus, ModContainer container) {

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        bus.addListener(this::dataSetup);
        bus.addListener(this::registerDataMapTypes);

//        container.registerConfig(ModConfig.Type.COMMON, MWConfig.COMMON_SPEC);
        container.registerConfig(ModConfig.Type.CLIENT, MWConfig.CLIENT_SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        TrackedDataManager.INSTANCE.registerData(MilkyWay.location("milk_timers"), MILK_TIMERS);
    }

    private void clientSetup(FMLClientSetupEvent event) {
    }

    private void dataSetup(GatherDataEvent event) {
//        DataGenerator generator = event.getGenerator();
//        PackOutput packOutput = generator.getPackOutput();
//        ExistingFileHelper helper = event.getExistingFileHelper();
//        boolean includeServer = event.includeServer();
//        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
//
//        generator.addProvider(includeServer, new MWEntityTypeTagsProvider(packOutput, lookupProvider, helper));
    }

    private void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(MILK_DATA);
    }

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}