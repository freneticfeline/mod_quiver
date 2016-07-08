package net.unladenswallow.minecraft.quiver.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;
import net.unladenswallow.minecraft.quiver.entity.EntityIronArrow;

public class ItemIronArrow extends ItemQuiverableArrow {

	public ItemIronArrow() {
		super("iron_arrow", new ItemCustomBow("ironArrowBow", "minecraft:bow"){});
		this.itemUsedByBow = this;
	}

	@Override
	public EntityArrow getNewEntityArrow(World worldIn, EntityPlayer playerIn, int itemUseDuration) {
		return new EntityIronArrow(worldIn, playerIn);
	}

}
