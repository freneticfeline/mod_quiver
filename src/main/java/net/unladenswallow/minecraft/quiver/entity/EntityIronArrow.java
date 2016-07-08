package net.unladenswallow.minecraft.quiver.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.unladenswallow.minecraft.quiver.FFQLogger;

public class EntityIronArrow extends EntityCustomArrow {

	public EntityIronArrow(World worldIn, EntityLivingBase shooter) {
		super(worldIn, shooter);
		this.setUnlocalizedName("iron_arrow");
		this.setDamage(this.getDamage() * 1.7f);
	}

}
