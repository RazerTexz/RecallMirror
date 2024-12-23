package com.razertexz.recallmirror.Items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class ItemPortalScroll extends Item {
	public ItemPortalScroll(final String name) {
		setTranslationKey(name).setMaxStackSize(40).setCreativeTab(CreativeTabs.TOOLS);
		setRegistryName(name);
	}

	@Override
    public final void addInformation(final ItemStack stack, final World world, final List<String> list, final ITooltipFlag whatisthis) {
        list.add("Returns you to your bed / world spawn.");
        list.add("NOTE: Single use.");
    }

	@Override
	public final ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
		if (!world.isRemote) {
	        // 60 secs cooldown & 5 secs effects duration
	        ItemUtil.teleport(this, ItemUtil.getSpawnLocation(world, player), 1200, 100, world, player);

			final ItemStack stack = player.getHeldItem(hand);
	        stack.shrink(1);
	        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
        } else {
        	return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
        }
    }
}