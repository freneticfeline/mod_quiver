package net.unladenswallow.minecraft.quiver.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityPoisonArrow extends EntityCustomArrow {

	private static Potion EFFECT = MobEffects.poison;
	private static int EFFECT_TICK_LENGTH = 400;
	private static int EFFECT_AMPLIFIER = 0;
	private static boolean EFFECT_AMBIANT = true;
	private static boolean EFFECT_SHOW_PARTICLES = true;
	
	public EntityPoisonArrow(World worldIn, EntityLivingBase shooter) {
		super(worldIn, shooter);
		this.setUnlocalizedName("poison_arrow");
	}

	@Override
    protected void handleInTileState(Block block, EnumFacing facing) {
        BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
    	BlockPos facingBlockPos = facingBlock(blockpos, facing);
//		Block.spawnAsEntity(worldObj, facingBlockPos, new ItemStack(ModFFQuiver.poisonArrow));
		this.setDead();
	}
	
	@Override
	protected void handleEntityHit(Entity entity) {
		super.handleEntityHit(entity);
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
			entityLivingBase.addPotionEffect(new PotionEffect(EFFECT, EFFECT_TICK_LENGTH, 
					EFFECT_AMPLIFIER, EFFECT_AMBIANT, EFFECT_SHOW_PARTICLES));
		}
	}
    
    @Override
    protected ItemStack getArrowStack()
    {
//        return new ItemStack(ModFFQuiver.poisonArrow);
        return null;
    }

}
