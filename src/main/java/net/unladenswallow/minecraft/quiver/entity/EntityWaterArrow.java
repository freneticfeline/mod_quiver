package net.unladenswallow.minecraft.quiver.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.unladenswallow.minecraft.quiver.ModFFQuiver;

public class EntityWaterArrow extends EntityCustomArrow {

	public EntityWaterArrow(World worldIn, EntityLivingBase shooter) {
		super(worldIn, shooter);
		this.setUnlocalizedName("water_arrow");
	}

	@Override
    protected void handleInTileState(Block block, EnumFacing facing) {
        BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
    	BlockPos facingBlockPos = facingBlock(blockpos, facing);
//    	worldObj.setBlockState(facingBlockPos, Blocks.water.getDefaultState());
    	ItemBucket waterBucket = (ItemBucket)(Items.water_bucket);
    	waterBucket.tryPlaceContainedLiquid((EntityPlayer)this.shootingEntity, this.worldObj, facingBlockPos);
    	this.setDead();
	}
	
	@Override
	protected void handleEntityHit(Entity entity) {
        BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
//		Block.spawnAsEntity(worldObj, blockpos, new ItemStack(ModFreneticFolly.waterArrow));
    	ItemBucket waterBucket = (ItemBucket)(Items.water_bucket);
//    	FFLogger.info("EntityWaterArrow handleEntityHit: trying to place a block of water at " + blockpos);
    	waterBucket.tryPlaceContainedLiquid((EntityPlayer)this.shootingEntity, this.worldObj, blockpos);
		this.setDead();
	}
    
    @Override
    protected ItemStack getArrowStack()
    {
        return new ItemStack(ModFFQuiver.waterArrow);
    }
}
