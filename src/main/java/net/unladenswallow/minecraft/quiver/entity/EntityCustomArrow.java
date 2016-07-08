package net.unladenswallow.minecraft.quiver.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

/**
 * This class extends EntityArrow, with modifications to pull
 * certain behaviors out to their own functions so that they can be overridden
 * by subclasses.  By extending EntityArrow, custom arrows can be rendered by the
 * native arrow renderer until I figure out how to do custom rendering.
 * 
 */
public abstract class EntityCustomArrow extends EntityTippedArrow
{
	protected String unlocalizedName = "customArrow";
    protected int xTile = -1;
    protected int yTile = -1;
    protected int zTile = -1;
    protected Block inTile;
    protected int inData;
    protected boolean inGround;
    protected int ticksInGround;
    protected int ticksInAir;
    protected double damage = 2.0D;
    /** The amount of knockback an arrow applies when it hits a mob. */
    protected int knockbackStrength;

    protected int fire;
    
    public EntityCustomArrow(World worldIn, EntityLivingBase shooter)
    {
        /* For some reason when this constructor was copied verbatim from EntityArrow, the fired
         * arrows always hit the player in survival mode.  Even though the code was copied verbatim!
         * Calling the constructor doesn't have the same problem.
         */
        super(worldIn, shooter);
//        FFLogger.info("EntityCustomArrow [" + System.identityHashCode(this) + "] <init>: origin = " + (new BlockPos(shooter.posX, shooter.posY, shooter.posZ)).toString());
    }


    /**
     * Called to update the entity's position/logic.
     */
    /*
     * Holy poop this method could use some refactoring.  Anyway, the whole thing was copied verbatim
     * from EntityArrow, with interesting parts pulled out into helper methods that can be overridden
     * by subclasses. 
     */
    public void onUpdate()
    {
        super.onUpdate();
        
//        MEMLogger.info("EntityCustomArrow [" + System.identityHashCode(this) + "] onUpdate()");
        
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
        }

        BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
//        MEMLogger.info("EntityCustomArrow onUpdate(): blockpos = " + blockpos.toString());
//        MEMLogger.info("EntityCustomArrow onUpdate(): position = " + (new BlockPos(this.posX, this.posY, this.posZ)).toString());
//        MEMLogger.info("EntityCustomArrow onUpdate(): prevPos = " + (new BlockPos(this.prevPosX, this.prevPosY, this.prevPosZ)).toString());
//        MEMLogger.info("EntityCustomArrow onUpdate(): motion = [x=" + this.motionX + ", y=" + this.motionY + ", z=" + this.motionZ + "]");
        
        IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
        Block block = iblockstate.getBlock();

        if (block.getMaterial(iblockstate) != Material.air)
        {
//            block.setBlockBoundsBasedOnState(this.worldObj, blockpos);
            AxisAlignedBB axisalignedbb = block.getCollisionBoundingBox(iblockstate, this.worldObj, blockpos);

            if (axisalignedbb != null && axisalignedbb.isVecInside(new Vec3d(this.posX, this.posY, this.posZ)))
            {
                this.inGround = true;
            }
        }

        if (this.arrowShake > 0)
        {
            --this.arrowShake;
        }

        if (this.inGround)
        {
//        	MEMLogger.info("EntityCustomArrow [" + System.identityHashCode(this) + "] onUpdate(): I'm in the ground");
            int j = block.getMetaFromState(iblockstate);

            if (block == this.inTile && j == this.inData)
            {
            	/*
            	 * Pulled out for subclass override
            	 */
            	handleInTileState(block, determineImpactFace(blockpos, this.posX, this.posY, this.posZ));
            }
            else
            {
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        }
        else
        {
//        	MEMLogger.info("EntityCustomArrow onUpdate(): I'm in the air");
            ++this.ticksInAir;
            Vec3d vec31 = new Vec3d(this.posX, this.posY, this.posZ);
            Vec3d vec3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            RayTraceResult rayTraceResult = this.worldObj.rayTraceBlocks(vec31, vec3, false, true, false);
            vec31 = new Vec3d(this.posX, this.posY, this.posZ);
            vec3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (rayTraceResult != null)
            {
                vec3 = new Vec3d(rayTraceResult.hitVec.xCoord, rayTraceResult.hitVec.yCoord, rayTraceResult.hitVec.zCoord);
            }

            Entity entity = null;
            @SuppressWarnings("rawtypes")
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            int i;
            float f1;

            for (i = 0; i < list.size(); ++i)
            {
                Entity entity1 = (Entity)list.get(i);

                if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5))
                {
                    f1 = 0.3F;
                    AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
                    RayTraceResult rayTraceResult1 = axisalignedbb1.calculateIntercept(vec31, vec3);

                    if (rayTraceResult1 != null)
                    {
                        double d1 = vec31.distanceTo(rayTraceResult1.hitVec);

                        if (d1 < d0 || d0 == 0.0D)
                        {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            if (entity != null)
            {
                rayTraceResult = new RayTraceResult(entity);
            }

            if (rayTraceResult != null && rayTraceResult.entityHit != null && rayTraceResult.entityHit instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)rayTraceResult.entityHit;

                if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))
                {
                    rayTraceResult = null;
                }
            }

            float f2;
            float f3;
            float f4;

            if (rayTraceResult != null)
            {
//            	MEMLogger.info("EntityCustomArrow onUpdate(): movingobjectposition is not null");
                if (rayTraceResult.entityHit != null)
                {
//                	FFQLogger.info("EntityCustomArrow onUpdate(): I hit " + rayTraceResult.entityHit.getName());

                	/*
                	 * Pulled out for subclass override
                	 */
                	handleEntityHit(rayTraceResult.entityHit);
                }
                else
                {
//                	MEMLogger.info("EntityCustomArrow onUpdate(): I don't know what this means.");

                	BlockPos blockpos1 = rayTraceResult.getBlockPos();
                    this.xTile = blockpos1.getX();
                    this.yTile = blockpos1.getY();
                    this.zTile = blockpos1.getZ();
                    iblockstate = this.worldObj.getBlockState(blockpos1);
                    this.inTile = iblockstate.getBlock();
//                	MEMLogger.info("EntityCustomArrow onUpdate(): Looks like I'm in block " + this.inTile.getLocalizedName());
                    this.inData = this.inTile.getMetaFromState(iblockstate);
                    this.motionX = (double)((float)(rayTraceResult.hitVec.xCoord - this.posX));
                    this.motionY = (double)((float)(rayTraceResult.hitVec.yCoord - this.posY));
                    this.motionZ = (double)((float)(rayTraceResult.hitVec.zCoord - this.posZ));
                    f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / (double)f3 * 0.05000000074505806D;
                    this.posY -= this.motionY / (double)f3 * 0.05000000074505806D;
                    this.posZ -= this.motionZ / (double)f3 * 0.05000000074505806D;
                    this.playSound(SoundEvents.entity_arrow_hit, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    this.inGround = true;
                    this.arrowShake = 7;
                    this.setIsCritical(false);

                    if (this.inTile.getMaterial(iblockstate) != Material.air)
                    {
                        this.inTile.onEntityCollidedWithBlock(this.worldObj, blockpos1, iblockstate, this);
                    }
                }
            }

            if (this.getIsCritical())
            {
                for (i = 0; i < 4; ++i)
                {
                    this.worldObj.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * (double)i / 4.0D, this.posY + this.motionY * (double)i / 4.0D, this.posZ + this.motionZ * (double)i / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ, new int[0]);
                }
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
            {
                ;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
            {
                this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F)
            {
                this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
            {
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            f3 = 0.99F;
            f1 = 0.05F;

            if (this.isInWater())
            {
                for (int l = 0; l < 4; ++l)
                {
                    f4 = 0.25F;
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ, new int[0]);
                }

                f3 = 0.6F;
                handleInWater();
            }

            if (this.isWet())
            {
                this.extinguish();
            }

            this.motionX *= (double)f3;
            this.motionY *= (double)f3;
            this.motionZ *= (double)f3;
            this.motionY -= (double)f1;
            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();
        }
    }

    private void handleInWater() {
		// TODO Auto-generated method stub
		
	}


	protected void handleEntityHit(Entity entity) {
//    	FFQLogger.info("EntityCustomArrow handleEntityHit():  my base damage is " + this.damage);
    	float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        int k = MathHelper.ceiling_double_int((double)f2 * this.damage);

        if (this.getIsCritical())
        {
            k += this.rand.nextInt(k / 2 + 2);
        }

        DamageSource damagesource;

        if (this.shootingEntity == null)
        {
        	damagesource = DamageSource.causeArrowDamage(this, this);
        }
        else
        {
        	damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
        }

        if (this.isBurning() && !(entity instanceof EntityEnderman))
        {
        	entity.setFire(5);
        }

        if (entity.attackEntityFrom(damagesource, (float)k))
        {
            if (entity instanceof EntityLivingBase)
            {
                EntityLivingBase entitylivingbase = (EntityLivingBase)entity;

                if (!this.worldObj.isRemote)
                {
                    entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
                }

                if (this.knockbackStrength > 0)
                {
                    float f4 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

                    if (f4 > 0.0F)
                    {
                    	entity.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)f4, 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)f4);
                    }
                }

                if (this.shootingEntity instanceof EntityLivingBase)
                {
                    EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
                    EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, entitylivingbase);
                }

                if (this.shootingEntity != null && entity != this.shootingEntity && entity instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
                {
                    ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new SPacketChangeGameState(6, 0.0F));
                }
            }

            this.playSound(SoundEvents.entity_arrow_hit, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

            if (!(entity instanceof EntityEnderman))
            {
                this.setDead();
            }
        }
        else
        {
            this.motionX *= -0.10000000149011612D;
            this.motionY *= -0.10000000149011612D;
            this.motionZ *= -0.10000000149011612D;
            this.rotationYaw += 180.0F;
            this.prevRotationYaw += 180.0F;
            this.ticksInAir = 0;
        }
	}

	protected void handleInTileState(Block block, EnumFacing impactFace) {
//		MEMLogger.info("EntityCustomArrow handleInTileState()");
        ++this.ticksInGround;

        if (this.ticksInGround >= 1200)
        {
            this.setDead();
        }
    }

	protected EnumFacing swapZFacing(EnumFacing facing) {
		if (facing == EnumFacing.NORTH) {
			return EnumFacing.SOUTH;
		} else if (facing == EnumFacing.SOUTH) {
			return EnumFacing.NORTH;
		} else {
			return facing;
		}
	}

	protected BlockPos facingBlock(BlockPos blockpos, EnumFacing facing) {
		if (facing == EnumFacing.UP) {
			return new BlockPos(blockpos.getX(), blockpos.getY()+1, blockpos.getZ());
		} else if (facing == EnumFacing.DOWN) {
			return new BlockPos(blockpos.getX(), blockpos.getY()-1, blockpos.getZ());
		} else if (facing == EnumFacing.NORTH) {
			return new BlockPos(blockpos.getX(), blockpos.getY(), blockpos.getZ()+1);
		} else if (facing == EnumFacing.SOUTH) {
			return new BlockPos(blockpos.getX(), blockpos.getY(), blockpos.getZ()-1);
		} else if (facing == EnumFacing.EAST) {
			return new BlockPos(blockpos.getX()+1, blockpos.getY(), blockpos.getZ());
		} else {
			return new BlockPos(blockpos.getX()-1, blockpos.getY(), blockpos.getZ());
		}
	}

	private EnumFacing determineImpactFace(BlockPos blockpos, double posX, double posY, double posZ) {
		// Adding 0.5 to each block coordinate gives us the center of the block
		double xDiff = (double)(blockpos.getX()) + 0.5d - posX;
		double yDiff = (double)(blockpos.getY()) + 0.5d - posY;
		double zDiff = (double)(blockpos.getZ()) + 0.5d - posZ;
		
//		MEMLogger.info("EntityCustomArrow impactFace(): position diffs = [x=" + xDiff + ", y=" + yDiff + ", z=" + zDiff + "]"); 
		
		if (Math.abs(xDiff) > Math.abs(yDiff)) {
			if (Math.abs(xDiff) > Math.abs(zDiff)) {
				if (xDiff > 0) return EnumFacing.WEST;
				else return EnumFacing.EAST;
			} else {
				if (zDiff > 0) return EnumFacing.SOUTH;
				else return EnumFacing.NORTH;
			}
		} else if (Math.abs(yDiff) > Math.abs(zDiff)) {
			if (yDiff > 0) return EnumFacing.DOWN;
			else return EnumFacing.UP;
		} else {
			if (zDiff > 0) return EnumFacing.SOUTH;
			else return EnumFacing.NORTH;
		}
	}

	@Override
    protected void doBlockCollisions()
    {
//    	FFLogger.info("EntityCustomArrow doBlockCollisions()");
        BlockPos blockpos = new BlockPos(this.getEntityBoundingBox().minX + 0.001D, this.getEntityBoundingBox().minY + 0.001D, this.getEntityBoundingBox().minZ + 0.001D);
        BlockPos blockpos1 = new BlockPos(this.getEntityBoundingBox().maxX - 0.001D, this.getEntityBoundingBox().maxY - 0.001D, this.getEntityBoundingBox().maxZ - 0.001D);

        if (this.worldObj.isAreaLoaded(blockpos, blockpos1))
        {
            for (int i = blockpos.getX(); i <= blockpos1.getX(); ++i)
            {
                for (int j = blockpos.getY(); j <= blockpos1.getY(); ++j)
                {
                    for (int k = blockpos.getZ(); k <= blockpos1.getZ(); ++k)
                    {
                        BlockPos blockpos2 = new BlockPos(i, j, k);
                        IBlockState iblockstate = this.worldObj.getBlockState(blockpos2);

//                    	FFLogger.info("EntityCustomArrow doBlockCollisions(): collided with " + iblockstate.getBlock().getUnlocalizedName());

                        try
                        {
                            iblockstate.getBlock().onEntityCollidedWithBlock(this.worldObj, blockpos2, iblockstate, this);
                        }
                        catch (Throwable throwable)
                        {
                            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Colliding entity with block");
                            CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being collided with");
                            CrashReportCategory.addBlockInfo(crashreportcategory, blockpos2, iblockstate);
                            throw new ReportedException(crashreport);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected ItemStack getArrowStack()
    {
        return new ItemStack(Items.arrow);
    }
    
    @Override
    public String getName() {
        return I18n.translateToLocal(this.getUnlocalizedName() + ".name");
    }
    
    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }
    
    public String getUnlocalizedName() {
        return "entity." + this.unlocalizedName;
    }
}