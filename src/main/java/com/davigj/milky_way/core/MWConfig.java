package com.davigj.milky_way.core;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class MWConfig {
//    static final ModConfigSpec COMMON_SPEC;
//    public static final MWConfig.Common COMMON;

    static final ModConfigSpec CLIENT_SPEC;
    public static final MWConfig.Client CLIENT;

//    public static class Common {
//        Common (ModConfigSpec.Builder builder) {
//            builder.push("changes");
//            builder.pop();
//        }
//    }

    public static class Client {
        public final ModConfigSpec.ConfigValue<Boolean> angryParticle;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("client");
            angryParticle = builder.comment("Mobs let off an angry particle to indicate the cooldown to impatient players").define("Angry particle", true);
            builder.pop();
        }
    }


    static {
//        Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(MWConfig.Common::new);
//        COMMON_SPEC = specPair.getRight();
//        COMMON = specPair.getLeft();

        Pair<Client, ModConfigSpec> clientSpecPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }
}
