package com.razertexz.recallmirror;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = Main.MODID,
    name = Main.NAME,
    version = Main.VERSION
)
public final class Main {
    public static final String MODID = "recallmirror";
    public static final String NAME = "Recall Mirror";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public final void preinit(final FMLPreInitializationEvent preinit) {
        ModItems.init();
    }
}
