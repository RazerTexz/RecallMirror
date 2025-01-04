package com.razertexz.recallmirror.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.World;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.init.SoundEvents;
import net.minecraft.init.MobEffects;
import net.minecraft.init.Blocks;

public abstract class CustomItem extends Item {
    private final Style style = new Style().setColor(TextFormatting.GREEN);

    protected CustomItem(final String name, final int maxStackSize) {
        setTranslationKey(name).setMaxStackSize(maxStackSize).setCreativeTab(CreativeTabs.TOOLS);
        setRegistryName(name);
    }

    public abstract void addInformation(final ItemStack stack, final World world, final List<String> list, final ITooltipFlag eh);
    public abstract ItemStack onItemUseFinish(final ItemStack stack, final World world, final EntityLivingBase entityLiving);
    
    @Override
    public final int getMaxItemUseDuration(final ItemStack stack) {
        return 60; // 3 secs
    }

    @Override
    public final ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        if (!world.isRemote) {
            player.setActiveHand(hand);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        } else {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
        }
    }

    @Override
    public final void onUsingTick(final ItemStack stack, final EntityLivingBase entityLiving, final int count) {
        if (entityLiving instanceof EntityPlayer) {
            ((EntityPlayer) entityLiving).sendStatusMessage(new TextComponentString(String.format("Hold to charge: %.1f", (float) count / 20)).setStyle(style), true);
        }
    }

    protected final boolean teleportTo(final EntityPlayer player, final World world, final BlockPos targetPos, final int effectsDuration, final int itemCooldown, final int targetDimension) {
        if (!world.isChunkGeneratedAt(targetPos.getX() >> 4, targetPos.getZ() >> 4)) {
            return false;
        }

        if (player.dimension != targetDimension) {
            player.changeDimension(targetDimension);
        }

        player.setPositionAndUpdate(targetPos.getX(), targetPos.getY(), targetPos.getZ());

        // Apply cooldown
        player.getCooldownTracker().setCooldown(this, itemCooldown);

        // Apply blindness, slowness and broken armor (increase damage taken) effect
        player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, effectsDuration, 0));
        player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, effectsDuration, 0));
        player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE.setPotionName("Broken Armor"), 1200, -4)); // Increase damage taken by 60% for 1 min (formula: 20% x (amplifier + 1))

        world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.NEUTRAL, 1.0F, 1.0F);

        return true;
    }

    protected final BlockPos getSpawnLocation(final EntityPlayer player, final World world) {
        final BlockPos bedLocation = player.getBedLocation(0);
        return (bedLocation != null && world.isChunkGeneratedAt(bedLocation.getX() >> 4, bedLocation.getZ() >> 4) && world.getBlockState(bedLocation).getBlock() == Blocks.BED) ? bedLocation.add(0.5, 1.0, 0.5) : world.getSpawnPoint();
    }
}
