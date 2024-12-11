package com.razertexz.recallmirror.Items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class ItemRecallMirror extends Item {
	public ItemRecallMirror(final String name) {
		setTranslationKey(name).setMaxStackSize(1).setCreativeTab(CreativeTabs.TOOLS);
		setRegistryName(name);
	}

	@Override
    public final void addInformation(final ItemStack stack, final World world, final List<String> list, final ITooltipFlag whatisthis) {
        list.add("Returns you to your bed / world spawn.");
    }
	
    public final ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        BlockPos target = player.getBedSpawnLocation(world, player.getBedLocation(0), player.isSpawnForced(0)).add(0.5, 1, 0.5);
        if (target == null) {
        	target = world.getSpawnPoint();
        }
    
        // 45 secs cooldown & 4 secs effects duration
        ItemUtil.teleport(this, target, 900, 80, world, player);

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
}
