package com.davigj.milky_way.core.other;

import com.davigj.milky_way.core.MWConfig;
import com.davigj.milky_way.core.MilkyWay;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.davigj.milky_way.core.other.MWDataMapUtil.MILK_DATA;

@EventBusSubscriber(modid = MilkyWay.MOD_ID)
public class MWEvents {
    static final TrackedDataManager manager = TrackedDataManager.INSTANCE;

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onMilkSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        Entity target = event.getTarget();
        if (target instanceof LivingEntity living) {
            tryMilkEntity(event.getEntity(), living, event.getHand(), result -> {
                event.setCanceled(true);
                event.setCancellationResult(result);
            }, event);
        }
    }

    private static void tryMilkEntity(Player player, LivingEntity target, InteractionHand hand,
                                      Consumer<InteractionResult> result, PlayerInteractEvent.EntityInteractSpecific event) {
        if (target.isBaby()) return;

        Holder<EntityType<?>> holder = target.getType().builtInRegistryHolder();
        MWDataMapUtil.MilkData data = holder.getData(MILK_DATA);
        if (data == null) return;

        for (MWDataMapUtil.MilkData.ItemData itemData : data.items()) {
            if (ModList.get().isLoaded("cobblemon") && !player.level().isClientSide) {
                if (MWConstants.isCobblemon(target)) {
                    CompoundTag entityData = new CompoundTag();
                    target.save(entityData);

                    if (!entityData.contains("Pokemon")) break;

                    ListTag features = entityData.getCompound("Pokemon").getList("Features", 10);
                    boolean isMilkable = false;
                    for (int i = 0; i < features.size(); i++) {
                        CompoundTag feature = features.getCompound(i);
                        if (feature.contains("cobblemon:feature_id") && feature.getString("cobblemon:feature_id").equals("milkable")) {
                            if (feature.getBoolean("milkable")) {
                                isMilkable = true;
                            }
                        }
                    }
                    if (!isMilkable) break;
                }
            }

            if (!ModList.get().isLoaded(itemData.modLoaded())) continue;

            ItemStack held = player.getItemInHand(hand);
            String itemID = itemData.item();
            if (itemID.equals("milky_way:buckets")) {
                if (!held.is(MWItemTags.BUCKETS)) continue;
            } else if (!held.is(getCompatItem(itemID).get())) continue;

            byte substance = itemData.substanceIndex();
            int milkTime = parseMilkTimers(target, substance);

            if (milkTime > 0) {
                if (!player.getCooldowns().isOnCooldown(held.getItem())) {
                    player.getCooldowns().addCooldown(held.getItem(), 20);
                    player.swing(hand);
                    if (MWConfig.CLIENT.angryParticle.get()) {
                        target.level().addParticle(ParticleTypes.ANGRY_VILLAGER, target.getX(), target.getEyeY(), target.getZ(), 0, 0, 0);
                    }
                    if (target instanceof Mob mob) {
                        mob.playAmbientSound();
                    }
                }
                result.accept(InteractionResult.CONSUME);
            } else {
                setMilkTimers(target, substance, itemData.seconds() * 20);
            }
            break;
        }
    }

    @SubscribeEvent
    public static void entityTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        Map<Byte, Integer> timers = new HashMap<>(manager.getValue(entity, MilkyWay.MILK_TIMERS));

        boolean changed = false;
        for (Map.Entry<Byte, Integer> entry : timers.entrySet()) {
            int remaining = entry.getValue();
            if (remaining > 0) {
                timers.put(entry.getKey(), remaining - 1);
                changed = true;
            }
        }

        if (changed) {
            manager.setValue(entity, MilkyWay.MILK_TIMERS, timers);
        }
    }

    private static int parseMilkTimers(Entity entity, byte substanceIndex) {
        Map<Byte, Integer> timers = manager.getValue(entity, MilkyWay.MILK_TIMERS);
        return timers.getOrDefault(substanceIndex, 0);
    }

    private static void setMilkTimers(Entity entity, byte substanceIndex, int ticks) {
        Map<Byte, Integer> timers = new HashMap<>(manager.getValue(entity, MilkyWay.MILK_TIMERS));
        timers.put(substanceIndex, ticks);
        manager.setValue(entity, MilkyWay.MILK_TIMERS, timers);
    }

    private static Supplier<Item> getCompatItem(String fullId) {
        String[] parts = fullId.split(":");
        String modid = parts[0];
        String itemID = parts[1];
        ResourceLocation item = ResourceLocation.fromNamespaceAndPath(modid, itemID);
        return ModList.get().isLoaded(modid) ? () -> BuiltInRegistries.ITEM.get(item) : () -> null;
    }
}
