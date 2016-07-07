package net.unladenswallow.minecraft.quiver.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityIronArrow extends EntityCustomArrow {

	public EntityIronArrow(World worldIn, EntityLivingBase shooter) {
		super(worldIn, shooter);
		unlocalizedName = "torchArrow";
		this.setDamage(this.getDamage() * 1.7f);
	}

}
