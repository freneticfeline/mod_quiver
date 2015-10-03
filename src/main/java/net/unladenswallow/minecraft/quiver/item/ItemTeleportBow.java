package net.unladenswallow.minecraft.quiver.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.unladenswallow.minecraft.quiver.ModFFQuiver;
import net.unladenswallow.minecraft.quiver.entity.EntityTeleportArrow;

public class ItemTeleportBow extends ItemCustomBow {

	public ItemTeleportBow() {
		super("teleportBow", ModFFQuiver.MODID + ":" + "teleport_bow");
		this.setModelVariantCount(3);
	}
	
	@Override
	protected boolean shotIsCritical(int itemUseDuration, float arrowDamage) {
        return false;
	}

	@Override
	protected void applyEnchantments(EntityArrow entityarrow, ItemStack stack) {
		// No enchantments apply
	}

	@Override
	protected EntityArrow getNewEntityArrow(World worldIn, EntityPlayer playerIn, float damage, int itemUseDuration) {
		return new EntityTeleportArrow(worldIn, playerIn, damage);
	}

	@Override
	protected Item getItemUsedByBow() {
		return ModFFQuiver.teleportArrow;
	}

}
