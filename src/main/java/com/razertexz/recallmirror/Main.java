package com.razertexz.recallmirror;

import com.razertexz.recallmirror.ModItems;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
	modid = Main.MODID,
	name = Main.NAME,
	version = Main.VERSION
)
public class Main {
	public static final String MODID = "recallmirror";
	public static final String NAME = "Recall Mirror";
	public static final String VERSION = "1.0";

	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent preinit) {
		ModItems.init();
	}
}
