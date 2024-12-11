package com.razertexz.recallmirror.Items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.item.IItemPropertyGetter;

public final class ItemNetherRecallMirror extends Item {
	public ItemNetherRecallMirror(final String name) {
		setTranslationKey(name).setMaxStackSize(1).setCreativeTab(CreativeTabs.TOOLS);
		setRegistryName(name);
        addPropertyOverride(new ResourceLocation("charged"), new IItemPropertyGetter() {
			@Override
            public final float apply(final ItemStack stack, final World world, final EntityLivingBase entity) {
            	final NBTTagCompound tag = stack.getTagCompound();
            	if (tag == null || !tag.getBoolean("used")) {
            		return 0F;
            	} else {
            		return 1F;
            	}
            }
        });
	}
	
	@Override	
    public final void addInformation(final ItemStack stack, final World world, final List<String> list, final ITooltipFlag whatisthis) {
        list.add("An upgraded version of the regular Recall Mirror.");
        list.add("Returns you to your bed / world spawn and back to where you used it.");
    }

	@Override
	public final void onCreated(final ItemStack stack, final World world, final EntityPlayer player) {
        stack.setTagCompound(new NBTTagCompound());
	}

    public final ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        final ItemStack stack = player.getHeldItem(hand);
		final NBTTagCompound tag = stack.getTagCompound();

		BlockPos target;
        if (!tag.getBoolean("used")) {
        	tag.setDouble("PosX", player.posX);
            tag.setDouble("PosY", player.posY);
            tag.setDouble("PosZ", player.posZ);

            target = player.getBedSpawnLocation(world, player.getBedLocation(0), player.isSpawnForced(0)).add(0.5, 1, 0.5);
			if (target == null) {
	        	target = world.getSpawnPoint();
	        }
			
            tag.setBoolean("used", true);
        } else {
        	target = new BlockPos(tag.getDouble("PosX"), tag.getDouble("PosY"), tag.getDouble("PosZ"));
        	tag.setBoolean("used", false);
        }

		// 30 secs cooldown & 5 secs effects duration
        ItemUtil.teleport(this, target, 600, 60, world, player);

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }
}
