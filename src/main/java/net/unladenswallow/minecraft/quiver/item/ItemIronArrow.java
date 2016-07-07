package net.unladenswallow.minecraft.quiver.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;
import net.unladenswallow.minecraft.quiver.entity.EntityIronArrow;

public class ItemIronArrow extends ItemQuiverableArrow {

	public ItemIronArrow() {
		super();
		setUnlocalizedName("ironArrow");
		this.bowToMimic = new ItemCustomBow("ironArrowBow", "minecraft:bow"){};
		this.itemUsedByBow = this;
	}

	public EntityArrow getNewEntityArrow(World worldIn, EntityPlayer playerIn, float damage, int itemUseDuration) {
		return new EntityIronArrow(worldIn, playerIn);
	}

}
