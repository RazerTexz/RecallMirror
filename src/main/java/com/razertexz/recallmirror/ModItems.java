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
public final class ModItems {
    private static Item portalScroll;
    private static Item recallMirror;
    private static Item netherRecallMirror;

    public static final void init() {
        portalScroll = new ItemPortalScroll();
        recallMirror = new ItemRecallMirror();
        netherRecallMirror = new ItemNetherRecallMirror();
    }
    
    @SubscribeEvent
    public static final void registerItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(portalScroll, recallMirror, netherRecallMirror);
    }

    @SubscribeEvent
    public static final void registerRenders(final ModelRegistryEvent event) {
        registerRender(portalScroll);
        registerRender(recallMirror);
        registerRender(netherRecallMirror);
        ModelLoader.setCustomModelResourceLocation(netherRecallMirror, 0, new ModelResourceLocation(netherRecallMirror.getRegistryName(), "charged"));
    }

    private static final void registerRender(final Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}