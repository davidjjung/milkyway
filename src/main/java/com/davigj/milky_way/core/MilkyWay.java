package com.davigj.milky_way.core;

import com.davigj.milky_way.core.data.server.tags.MWEntityTypeTagsProvider;
import com.teamabnormals.blueprint.common.world.storage.tracking.DataProcessors;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MilkyWay.MOD_ID)
public class MilkyWay {
    public static final String MOD_ID = "milky_way";
    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

    public static final TrackedData<Integer> BUCKET_TIMER = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().enablePersistence().build();
    public static final TrackedData<Integer> WORSE_BUCKET_TIMER = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().enablePersistence().build();
    public static final TrackedData<Integer> BOWL_TIMER = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().enablePersistence().build();
    public static final TrackedData<Integer> BOTTLE_TIMER = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().enablePersistence().build();

    public MilkyWay() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext context = ModLoadingContext.get();
        MinecraftForge.EVENT_BUS.register(this);

		REGISTRY_HELPER.register(bus);

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        bus.addListener(this::dataSetup);
        context.registerConfig(ModConfig.Type.COMMON, MWConfig.COMMON_SPEC);

        TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "bucket_timer"), BUCKET_TIMER);
        TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "worse_bucket_timer"), WORSE_BUCKET_TIMER);
        TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "bowl_timer"), BOWL_TIMER);
        TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "bottle_timer"), BOTTLE_TIMER);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

        });
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {

        });
    }

    private void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        boolean includeServer = event.includeServer();

        generator.addProvider(includeServer, new MWEntityTypeTagsProvider(generator, helper));
    }
}