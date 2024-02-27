package com.davigj.milky_way.core.other;

import com.davigj.milky_way.core.MWConfig;
import com.davigj.milky_way.core.MilkyWay;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MilkyWay.MOD_ID)
public class MWEvents {
    static TrackedDataManager manager = TrackedDataManager.INSTANCE;

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onMilk(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Entity target = event.getTarget();

        handleBuckets(player, target, event.getHand(), MWEntityTypeTags.BUCKET_MILKABLE, MilkyWay.BUCKET_TIMER, event, MWConfig.COMMON.bucketTimer.get());
        handleBuckets(player, target, event.getHand(), MWEntityTypeTags.WORSE_BUCKET_MILKABLE, MilkyWay.WORSE_BUCKET_TIMER, event, MWConfig.COMMON.worseBucketTimer.get());
        handleMilkingEvent(player, target, event.getHand(), MWEntityTypeTags.BOWL_MILKABLE, MilkyWay.BOWL_TIMER, event, Items.BOWL, MWConfig.COMMON.bowlTimer.get());
        handleMilkingEvent(player, target, event.getHand(), MWEntityTypeTags.BOTTLE_MILKABLE, MilkyWay.BOTTLE_TIMER, event, Items.GLASS_BOTTLE, MWConfig.COMMON.bottleTimer.get());

        if (!target.getType().is(MWEntityTypeTags.POKEMON)) {return;}

        if (ModList.get().isLoaded("cobblemon")) {
            CompoundTag entityData = new CompoundTag();
            target.save(entityData);
            if (entityData.contains("Pokemon") && entityData.getCompound("Pokemon").contains("milkable")) {
                if (entityData.getCompound("Pokemon").getBoolean("milkable")) {
                    if (player.getItemInHand(event.getHand()).is(MWItemTags.BUCKETS)) {
                        if (manager.getValue(target, MilkyWay.BUCKET_TIMER) == 0) {
                            manager.setValue(target, MilkyWay.BUCKET_TIMER, MWConfig.COMMON.bucketTimer.get() * 20);
                        } else {
                            event.setCanceled(true);
                            player.swing(event.getHand());
                            if (MWConfig.COMMON.angryParticle.get()) {
                                target.level.addParticle(ParticleTypes.ANGRY_VILLAGER, target.getX(), target.getEyeY() + 0.1, target.getZ(),
                                        10, 3, 10);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void handleMilkingEvent(Player player, Entity target, InteractionHand hand, TagKey<EntityType<?>> entityTag,
                                           TrackedData<Integer> timerValue, PlayerInteractEvent.EntityInteract event, Item item, int timer) {
        if (player.getItemInHand(hand).getItem().equals(item) &&
                target.getType().is(entityTag)) {
            if (manager.getValue(target, timerValue) == 0) {
                manager.setValue(target, timerValue, timer * 20);
            } else {
                event.setCanceled(true);
                player.swing(event.getHand());
                if (MWConfig.COMMON.angryParticle.get()) {
                    target.level.addParticle(ParticleTypes.ANGRY_VILLAGER, target.getX(), target.getEyeY(), target.getZ(),
                            10, 3, 10);
                }
            }
        }
    }

    private static void handleBuckets(Player player, Entity target, InteractionHand hand, TagKey<EntityType<?>> entityTag,
                                      TrackedData<Integer> timerValue, PlayerInteractEvent.EntityInteract event, int timer) {
        if (player.getItemInHand(hand).is(MWItemTags.BUCKETS) &&
                target.getType().is(entityTag)) {
            if (manager.getValue(target, timerValue) == 0) {
                manager.setValue(target, timerValue, timer * 20);
            } else {
                event.setCanceled(true);
                player.swing(event.getHand());
                if (MWConfig.COMMON.angryParticle.get()) {
                    target.level.addParticle(ParticleTypes.ANGRY_VILLAGER, target.getX(), target.getEyeY(), target.getZ(),
                            10, 3, 10);
                }
            }
        }
    }


    @SubscribeEvent
    public static void entityTick(LivingEvent.LivingTickEvent event) {
        Entity target = event.getEntity();

        handleMilkTick(target, MWEntityTypeTags.BUCKET_MILKABLE, MilkyWay.BUCKET_TIMER);
        handleMilkTick(target, MWEntityTypeTags.WORSE_BUCKET_MILKABLE, MilkyWay.WORSE_BUCKET_TIMER);
        handleMilkTick(target, MWEntityTypeTags.BOWL_MILKABLE, MilkyWay.BOWL_TIMER);
        handleMilkTick(target, MWEntityTypeTags.BOTTLE_MILKABLE, MilkyWay.BOTTLE_TIMER);

        if (!target.getType().is(MWEntityTypeTags.POKEMON)) {return;}

        if (ModList.get().isLoaded("cobblemon")) {
            CompoundTag entityData = new CompoundTag();
            target.save(entityData);
            if (entityData.contains("Pokemon") && entityData.getCompound("Pokemon").contains("milkable")) {
                if (entityData.getCompound("Pokemon").getBoolean("milkable")) {
                    int timer = manager.getValue(target, MilkyWay.BUCKET_TIMER);
                    if (timer > 0) {
                        manager.setValue(target, MilkyWay.BUCKET_TIMER, timer - 1);
                    }
                }
            }
        }
    }

    private static void handleMilkTick(Entity target, TagKey<EntityType<?>> entityTag, TrackedData<Integer> timerValue) {
        if (target.getType().is(entityTag) && !target.level.isClientSide) {
            int timer = manager.getValue(target, timerValue);
            if (timer > 0) {
                manager.setValue(target, timerValue, timer - 1);
            }
        }
    }
}