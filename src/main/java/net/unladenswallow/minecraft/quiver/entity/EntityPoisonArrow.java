package net.unladenswallow.minecraft.quiver.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.unladenswallow.minecraft.quiver.ModFFQuiver;

public class EntityPoisonArrow extends EntityCustomArrow {

	private static int EFFECT_POTIONID = Potion.poison.getId();
	private static int EFFECT_TICK_LENGTH = 200;
	private static int EFFECT_AMPLIFIER = 0;
	private static boolean EFFECT_AMBIANT = true;
	private static boolean EFFECT_SHOW_PARTICLES = true;
	
	public EntityPoisonArrow(World worldIn, EntityLivingBase shooter, float damage) {
		super(worldIn, shooter, damage);
		unlocalizedName = "poisonArrow";
	}

	@Override
    protected void handleInTileState(Block block, EnumFacing facing) {
        BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
    	BlockPos facingBlockPos = facingBlock(blockpos, facing);
		Block.spawnAsEntity(worldObj, facingBlockPos, new ItemStack(ModFFQuiver.poisonArrow));
		this.setDead();
	}
	
	@Override
	protected void handleEntityHit(Entity entity) {
		super.handleEntityHit(entity);
		if (entity instanceof EntityLivingBase) {
			EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
			entityLivingBase.addPotionEffect(new PotionEffect(EFFECT_POTIONID, EFFECT_TICK_LENGTH, 
					EFFECT_AMPLIFIER, EFFECT_AMBIANT, EFFECT_SHOW_PARTICLES));
		}
	}

}
