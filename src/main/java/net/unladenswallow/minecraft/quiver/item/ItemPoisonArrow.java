package net.unladenswallow.minecraft.quiver.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;
import net.unladenswallow.minecraft.quiver.entity.EntityPoisonArrow;

public class ItemPoisonArrow extends ItemQuiverableArrow {

	public ItemPoisonArrow() {
		super();
		setUnlocalizedName("poisonArrow");
		this.bowToMimic = new ItemCustomBow("genericCustomBow", "minecraft:bow"){};
		this.itemUsedByBow = this;
	}

	@Override
	public EntityArrow getNewEntityArrow(World worldIn, EntityPlayer playerIn, int itemUseDuration) {
		return new EntityPoisonArrow(worldIn, playerIn);
	}

}
