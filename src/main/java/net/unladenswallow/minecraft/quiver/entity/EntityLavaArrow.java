package net.unladenswallow.minecraft.quiver.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityLavaArrow extends EntityCustomArrow {

	public EntityLavaArrow(World worldIn, EntityLivingBase shooter, float p_i1756_3_) {
		super(worldIn, shooter, p_i1756_3_);
		unlocalizedName = "lavaArrow";
	}

	@Override
    protected void handleInTileState(Block block, EnumFacing facing) {
        BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
    	BlockPos facingBlockPos = facingBlock(blockpos, facing);
//    	worldObj.setBlockState(facingBlockPos, Blocks.water.getDefaultState());
    	ItemBucket waterBucket = (ItemBucket)(Items.lava_bucket);
    	waterBucket.tryPlaceContainedLiquid(this.worldObj, facingBlockPos);
    	this.setDead();
	}
	
	@Override
	protected void handleEntityHit(Entity entity) {
        BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
//		Block.spawnAsEntity(worldObj, blockpos, new ItemStack(ModFreneticFolly.waterArrow));
    	ItemBucket waterBucket = (ItemBucket)(Items.lava_bucket);
//    	FFLogger.info("EntityLavaArrow handleEntityHit: trying to place a block of lava at " + blockpos);
    	waterBucket.tryPlaceContainedLiquid(this.worldObj, blockpos);
		this.setDead();
	}
}
