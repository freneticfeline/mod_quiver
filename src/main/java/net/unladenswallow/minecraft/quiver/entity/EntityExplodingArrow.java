package net.unladenswallow.minecraft.quiver.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.unladenswallow.minecraft.quiver.ModFFQuiver;

public class EntityExplodingArrow extends EntityCustomArrow {

	private float explosionRadius = 1.0f;

	public EntityExplodingArrow(World worldIn, EntityLivingBase shooter) {
		super(worldIn, shooter);
		this.setUnlocalizedName("exploding_arrow");
	}

	public EntityExplodingArrow(World worldIn, EntityLivingBase shooter, float explosionRadiusModifier) {
		this(worldIn, shooter);
		this.explosionRadius = this.explosionRadius * explosionRadiusModifier;
	}


	@Override
    protected void handleInTileState(Block block, EnumFacing facing) {
    	this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius, true);
    	this.setDead();
	}
	
	@Override
	protected void handleEntityHit(Entity entity) {
    	this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius, true);
		this.setDead();
	}
	
	@Override
    protected ItemStack getArrowStack()
    {
        return new ItemStack(ModFFQuiver.explodingArrow);
    }

}
