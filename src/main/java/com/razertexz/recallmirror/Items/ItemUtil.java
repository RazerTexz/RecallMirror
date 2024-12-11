package com.razertexz.recallmirror.Items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.init.MobEffects;

public final class ItemUtil {
	private ItemUtil() { }

	public static final void teleport(final Item item, final BlockPos targetPos, final int itemCooldown, final int effectsDuration, final World world, final EntityPlayer player) {
		if (player.dimension != 0) {
            player.changeDimension(0);
		}

        player.setPositionAndUpdate(targetPos.getX(), targetPos.getY(), targetPos.getZ());

		// Cooldown
		player.getCooldownTracker().setCooldown(item, itemCooldown);

		// Apply blindness & slowness effect
        player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, effectsDuration, 0));
        player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, effectsDuration, 0));

		world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.NEUTRAL, 1F, 1F);
	}
}
