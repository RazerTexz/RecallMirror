package com.razertexz.recallmirror.Items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class ItemRecallMirror extends CustomItem {
    public ItemRecallMirror() {
        super("recall_mirror", 1);
    }

    @Override
    public final void addInformation(final ItemStack stack, final World world, final List<String> list, final ITooltipFlag eh) {
        list.add("Returns you to your bed / world spawn.");
    }
    
    @Override
    public final ItemStack onItemUseFinish(final ItemStack stack, final World world, final EntityLivingBase entityLiving) {
        if (!world.isRemote && entityLiving instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) entityLiving;

            // 4 secs effects duration & 45 secs cooldown
            teleportTo(player, world, getSpawnLocation(player, world), 80, 900, 0);
        }
        return stack;
    }
}
