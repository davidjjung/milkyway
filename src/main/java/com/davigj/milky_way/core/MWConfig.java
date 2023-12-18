package com.davigj.milky_way.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class MWConfig {
    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Integer> bucketTimer;
        public final ForgeConfigSpec.ConfigValue<Integer> worseBucketTimer;
        public final ForgeConfigSpec.ConfigValue<Integer> bowlTimer;
        public final ForgeConfigSpec.ConfigValue<Integer> bottleTimer;
        public final ForgeConfigSpec.ConfigValue<Boolean> angryParticle;

        Common (ForgeConfigSpec.Builder builder) {
            builder.push("changes");
            bucketTimer = builder.comment("Amount of seconds before a mob's bucketed product replenishes").define("Bucket timer", 600);
            worseBucketTimer = builder.comment("Amount of seconds before a mob's bucketed product replenishes, for slower producers").define("Worse bucket timer", 1200);
            bowlTimer = builder.comment("Amount of seconds before a mob's bowled product replenishes").define("Bowl timer", 600);
            bottleTimer = builder.comment("Amount of seconds before a mob's bottled product replenishes").define("Bottle timer", 200);
            angryParticle = builder.comment("Mobs let off an angry particle to indicate the cooldown to impatient players").define("Angry particle", true);
            builder.pop();
        }
    }

    static final ForgeConfigSpec COMMON_SPEC;
    public static final MWConfig.Common COMMON;


    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(MWConfig.Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
