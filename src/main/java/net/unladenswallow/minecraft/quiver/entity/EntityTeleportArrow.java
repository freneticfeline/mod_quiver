package net.unladenswallow.minecraft.quiver.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityTeleportArrow extends EntityCustomArrow {

	public EntityTeleportArrow(World worldIn, EntityLivingBase shooter) {
		super(worldIn, shooter);
		this.setUnlocalizedName("teleport_arrow");
	}

	@Override
    protected void handleInTileState(Block block, EnumFacing facing) {
        for (int i = 0; i < 32; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY + this.rand.nextDouble() * 2.0D, this.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian(), new int[0]);
        }

        if (!this.world.isRemote)
        {
            if (null != this.shootingEntity && this.shootingEntity instanceof EntityPlayerMP)
            {
                EntityPlayerMP entityplayermp = (EntityPlayerMP)this.shootingEntity;

                if (entityplayermp.connection.getNetworkManager().isChannelOpen() && entityplayermp.world == this.world && !entityplayermp.isPlayerSleeping())
                {
                    net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(entityplayermp, this.posX, this.posY, this.posZ, 5.0F);
                    if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
                    { // Don't indent to lower patch size

                    if (this.shootingEntity.isRiding())
                    {
                    	this.shootingEntity.startRiding((Entity)null);
                    }

                    this.shootingEntity.setPositionAndUpdate(event.getTargetX(), event.getTargetY(), event.getTargetZ());
                    this.shootingEntity.fallDistance = 0.0F;
                    }
                }
            }

            this.setDead();
            
        }
	}
	
	@Override
	protected void handleEntityHit(Entity entity) {
		entity.move(MoverType.SELF, 0, 20, 0);
		this.setDead();
	}
}
