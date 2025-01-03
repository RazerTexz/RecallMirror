package com.razertexz.recallmirror.Items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class ItemNetherRecallMirror extends CustomItem {
    public ItemNetherRecallMirror() {
        super("nether_recall_mirror", 1);
        addPropertyOverride(new ResourceLocation("charged"), new IItemPropertyGetter() {
            @Override
            public final float apply(final ItemStack stack, final World world, final EntityLivingBase entity) {
                final NBTTagCompound tag = stack.getTagCompound();
                if (tag == null || !tag.getBoolean("used")) {
                    return 0.0F;
                } else {
                    return 1.0F;
                }
            }
        });
    }
    
    @Override
    public final void addInformation(final ItemStack stack, final World world, final List<String> list, final ITooltipFlag eh) {
        list.add("An upgraded version of the regular Recall Mirror.");
        list.add("Returns you to your bed / world spawn and back to where you used it.");
    }

    @Override
    public final ItemStack onItemUseFinish(final ItemStack stack, final World world, final EntityLivingBase entityLiving) {
        if (!world.isRemote && entityLiving instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) entityLiving;
            NBTTagCompound tag = stack.getTagCompound();
            if (tag == null) {
                tag = new NBTTagCompound();
                stack.setTagCompound(tag);
            }

            BlockPos targetPos = new BlockPos(tag.getDouble("PosX"), tag.getDouble("PosY"), tag.getDouble("PosZ"));
            if (!tag.getBoolean("used")) {
                tag.setDouble("PosX", player.posX);
                tag.setDouble("PosY", player.posY);
                tag.setDouble("PosZ", player.posZ);
                tag.setInteger("Dimension", player.dimension);

                targetPos = getSpawnLocation(player, world);

                tag.setBoolean("used", true);
            } else {
                tag.setBoolean("used", false);
            }

            // 3 secs effects duration & 30 secs cooldown
            teleportTo(player, world, targetPos, 60, 600, tag.getInteger("Dimension"));
        }
        return stack;
    }
}
