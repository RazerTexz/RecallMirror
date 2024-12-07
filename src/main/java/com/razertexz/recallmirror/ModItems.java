package com.razertexz.recallmirror;

import com.razertexz.recallmirror.Items.ItemNetherRecallMirror;
import com.razertexz.recallmirror.Items.ItemPortalScroll;
import com.razertexz.recallmirror.Items.ItemRecallMirror;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber()
public class ModItems {
	static Item portalScroll;
	static Item recallMirror;
	static Item netherRecallMirror;
	static Item netherRecallMirrorCharged;

	public static void init() {
		portalScroll = new ItemPortalScroll("portal_scroll");
		recallMirror = new ItemRecallMirror("recall_mirror");
		netherRecallMirror = new ItemNetherRecallMirror("nether_recall_mirror");
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(portalScroll, recallMirror, netherRecallMirror);
	}

	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
		registerRender(portalScroll);
		registerRender(recallMirror);
		registerRender(netherRecallMirror);
		ModelLoader.setCustomModelResourceLocation(netherRecallMirror, 0, new ModelResourceLocation(netherRecallMirror.getRegistryName(), "charged"));
	}

	private static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}