package com.razertexz.recallmirror.Items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class ItemPortalScroll extends CustomItem {
    public ItemPortalScroll() {
        super("portal_scroll", 40);
    }

    @Override
    public final void addInformation(final ItemStack stack, final World world, final List<String> list, final ITooltipFlag eh) {
        list.add("Returns you to your bed / world spawn.");
        list.add("NOTE: Single use.");
    }

    @Override
    public final ItemStack onItemUseFinish(final ItemStack stack, final World world, final EntityLivingBase entityLiving) {
        if (!world.isRemote && entityLiving instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) entityLiving;

            // 5 secs effects duration & 1 min cooldown
            if (teleportTo(player, world, getSpawnLocation(player, world), 100, 1200, 0)) {
                stack.shrink(1);
            }
        }
        return stack;
    }
}