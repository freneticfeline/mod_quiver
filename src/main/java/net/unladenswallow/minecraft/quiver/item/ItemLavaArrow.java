package net.unladenswallow.minecraft.quiver.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;
import net.unladenswallow.minecraft.quiver.entity.EntityLavaArrow;

public class ItemLavaArrow extends ItemQuiverableArrow {

	public ItemLavaArrow() {
		super("lava_arrow", new ItemCustomBow("genericCustomBow", "minecraft:bow"){});
		this.itemUsedByBow = this;
	}

	@Override
	public EntityArrow getNewEntityArrow(World worldIn, EntityPlayer playerIn, int itemUseDuration) {
		return new EntityLavaArrow(worldIn, playerIn);
	}

}
