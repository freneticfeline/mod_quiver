package net.unladenswallow.minecraft.quiver.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.unladenswallow.minecraft.quiver.ModFFQuiver;

public class EntityTorchArrow extends EntityCustomArrow {

	public EntityTorchArrow(World worldIn, EntityLivingBase shooter) {
		super(worldIn, shooter);
		this.setUnlocalizedName("torch_arrow");
	}

	@Override
    protected void handleInTileState(Block block, EnumFacing facing) {
        BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
    	BlockPos facingBlockPos = facingBlock(blockpos, facing);
    	if (facing != EnumFacing.DOWN && Blocks.TORCH.canPlaceBlockAt(worldObj, facingBlockPos)) {
        	worldObj.setBlockState(facingBlockPos, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, swapZFacing(facing)));
    	} else {
    		Block.spawnAsEntity(worldObj, facingBlockPos, new ItemStack(ModFFQuiver.torchArrow));
    	}
    	this.setDead();
	}
	
	@Override
	protected void handleEntityHit(Entity entity) {
		entity.setFire(10);
		this.setDead();
	}
}
