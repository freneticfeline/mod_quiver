package net.unladenswallow.minecraft.quiver.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;
import net.unladenswallow.minecraft.quiver.entity.EntityWaterArrow;

public class ItemWaterArrow extends ItemQuiverableArrow {

	public ItemWaterArrow() {
		super("water_arrow", new ItemCustomBow("genericCustomBow", "minecraft:bow"){});
		this.itemUsedByBow = this;
	}

	@Override
	public EntityArrow getNewEntityArrow(World worldIn, EntityPlayer playerIn, int itemUseDuration) {
		return new EntityWaterArrow(worldIn, playerIn);
	}

}
