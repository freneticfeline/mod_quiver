package net.unladenswallow.minecraft.quiver.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.unladenswallow.minecraft.quiver.ClientProxy;
import net.unladenswallow.minecraft.quiver.ModFFQuiver;

public abstract class ItemCustomBow extends ItemBow {

	private String modelBaseName = ModFFQuiver.MODID + ":" + "custom_bow";
	private int modelVariantCount = 3;

	public ItemCustomBow(String unlocalizedName, String modelBaseName) {
		super();
		this.setUnlocalizedName(unlocalizedName);
		this.setModelBaseName(modelBaseName);
		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setRegistryName(ModFFQuiver.MODID, ClientProxy.stripItemPrefix(this.getUnlocalizedName()));
//		MEMLogger.info("ItemCustomBow <init>: " + getUnlocalizedName() + "; " + getModelBaseName() + "; " + Arrays.toString(bowPullIconNameArray));
	}

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        ActionResult<ItemStack> event = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemStackIn, worldIn, playerIn, hand, true);
        if (event != null) return event;

        if (isUsableByPlayer(itemStackIn, playerIn))
        {
            playerIn.setActiveHand(hand);
            return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
        } else {
            return new ActionResult(EnumActionResult.FAIL, itemStackIn);
        }
        
    }

    protected boolean isUsableByPlayer(ItemStack itemStackIn, EntityPlayer playerIn) {
		return hasInfiniteArrows(itemStackIn, playerIn) || playerIn.inventory.hasItemStack(new ItemStack(getItemUsedByBow()));
	}

    protected boolean hasInfiniteArrows(ItemStack itemStackIn, EntityPlayer playerIn) {
    	return (EnchantmentHelper.getEnchantmentLevel(Enchantments.infinity, itemStackIn) > 0)
    			|| playerIn.capabilities.isCreativeMode;
    }
    
	/**
     * Called when the player stops using an Item (stops holding the right mouse button).
     *  
     * @param timeLeft The amount of ticks left before the using would have been complete
     */
	@Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase playerIn, int timeLeft)
    {
	    if (playerIn instanceof EntityPlayer) {
	        EntityPlayer player = (EntityPlayer)playerIn;

	        int itemUseDuration = this.getMaxItemUseDuration(stack) - timeLeft;
	        itemUseDuration = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, player, itemUseDuration, isUsableByPlayer(stack, (EntityPlayer)playerIn));

	        if (isUsableByPlayer(stack, player))
	        {
	            float arrowDamage = getArrowDamage(itemUseDuration);
	            // I don't understand why this is done, but ItemBow does it, so we'll do it
	            if ((double)arrowDamage < 0.1D) {
	                return;
	            }

	            //            MEMLogger.info("ItemCustomBow onPlayerStoppedUsing(): f = " + arrowDamage + "; j = " + itemUseDuration + "; timeLeft = " + timeLeft);

	            EntityArrow entityarrow = getNewEntityArrow(worldIn, player, itemUseDuration);
//	            FFQLogger.info("Created a(n) " + entityarrow.getName() + " [damage: " + entityarrow.getDamage() + "]");

	            entityarrow.setIsCritical(shotIsCritical(itemUseDuration, arrowDamage));
	            //            if (entityarrow.getIsCritical()) {
	            //                MEMLogger.info("ItemCustomBow onPlayerStoppedUsing(): PEW! PEW!");
	            //            }

	            applyEnchantments(entityarrow, stack);
	            initializeArrowVelocity(entityarrow, player, arrowDamage);

	            takeDamage(1, stack, player);

	            worldIn.playSound(player.posX, player.posY, player.posZ, SoundEvents.entity_arrow_shoot, SoundCategory.HOSTILE, 1.0F, (1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + arrowDamage * 0.5F), true);

	            if (hasInfiniteArrows(stack, player))
	            {
	                entityarrow.canBePickedUp = EntityArrow.PickupStatus.CREATIVE_ONLY;
	            }
	            else
	            {
	                this.consumeAmmo(stack, worldIn, player);
	            }

	            player.addStat(StatList.func_188057_b(this));

	            if (!worldIn.isRemote)
	            {
	                worldIn.spawnEntityInWorld(entityarrow);
	            }
	        }
	    }
    }

	protected void initializeArrowVelocity(EntityArrow entityarrow, EntityPlayer player, float arrowDamage) {
        entityarrow.func_184547_a(player, player.rotationPitch, player.rotationYaw, 0.0F, arrowDamage * 3.0F, 1.0F);
    }

    protected void consumeAmmo(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        playerIn.inventory.clearMatchingItems(getItemUsedByBow(), -1, 1, null);
	}

	protected void takeDamage(int i, ItemStack stack, EntityPlayer playerIn) {
        stack.damageItem(1, playerIn);
	}

	/**
 	 * Helper function for onPlayerStoppedUsing() that allows subclasses to easily overwrite
	 * how to apply bow enchantments to the spawned arrow entity
	 * 
	 * @param entityarrow
	 * @param stack
	 */
	protected void applyEnchantments(EntityArrow entityarrow, ItemStack stack) {
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.power, stack);

        if (j > 0)
        {
            entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
        }

        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.punch, stack);

        if (k > 0)
        {
            entityarrow.setKnockbackStrength(k);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.flame, stack) > 0)
        {
            entityarrow.setFire(100);
        }
	}

	/**
 	 * Helper function for onPlayerStoppedUsing() that allows subclasses to easily overwrite
	 * how to determine whether a shot is critical
	 *
	 * @param itemUseDuration
	 * @param arrowDamage
	 * @return
	 */
	protected boolean shotIsCritical(int itemUseDuration, float arrowDamage) {
        return (arrowDamage == 1.0F);
	}

	/**
	 * Helper function for onPlayerStoppedUsing() that allows subclasses to easily overwrite custom
	 * arrow damage calculation
	 * 
	 * @param itemUseDuration
	 * @return
	 */
	protected float getArrowDamage(int itemUseDuration) {
        float f = (float)itemUseDuration / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
	}

	/**
	 * Helper function for onPlayerStoppedUsing() that allows subclasses to easily overwrite custom
	 * EntityArrow subclass to be spawned by bow release
	 * 
	 * Some subclasses may want the stack meta value, while others may not
	 * 
	 * @param worldIn
	 * @param playerIn
	 * @param damage
	 * @return
	 */
	protected EntityArrow getNewEntityArrow(World worldIn, EntityPlayer playerIn, int itemUseDuration) {
		return new EntityTippedArrow(worldIn, playerIn);
	}

/*	@Override
    public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
    {
		int useTime = getMaxItemUseDuration(stack) - useRemaining;
//		MEMLogger.info("ItemCustomBow getModel(): useRemaining = " + useRemaining);
//		MEMLogger.info("ItemCustomBow getModel(): useTime = " + useTime);
        ModelResourceLocation modelresourcelocation = new ModelResourceLocation(modelBaseName, "inventory");

        if(stack.getItem() == this && player.getItemInUse() != null && useRemaining > 0)
        {
        	int modelVariation = getModelVariation(useTime);
        	if (modelVariation < 0 || modelVariation >= getModelVariantCount()) {
        		FFQLogger.warning("ItemCustomBow getModel(): specified model variant " + modelVariation + " is out of range; using default"); 
        		modelresourcelocation = new ModelResourceLocation(modelBaseName + "_pulling_0", "inventory");
        	} else {
        		modelresourcelocation = new ModelResourceLocation(modelBaseName + "_pulling_" + modelVariation, "inventory");
//        		FFLogger.info("ItemCustomBow getModel(): modelresourcelocation = " + modelresourcelocation);
        	}
        }
//        FMLRelaunchLog.info("ItemCustomBow getModel(): modelResourcelocation is " + modelresourcelocation.getResourceDomain() + ":" + modelresourcelocation.getResourcePath());
        return modelresourcelocation;
    }
	
	*//**
	 * Helper function for getModel() that allows subclasses to easily overwrite custom animation
	 * sequences for bow pull
	 * 
	 * @param useTime
	 * @return
	 *//*
	protected int getModelVariation(int useTime) {
    	if(useTime >= 21) {
    		return 2;
        } else if(useTime > 10) {
            return 1;
        } else {
            return 0;
        }
	}
*/
	@SubscribeEvent
	public void fovUpdate(FOVUpdateEvent event) {
		if (event.getEntity() instanceof EntityPlayer) {
			if (event.getEntity().isHandActive() && event.getEntity().getActiveItemStack().getItem() == this) {
				float fovModifier = getNewFovModifier(event.getEntity().getItemInUseCount());
		        float fov = 1.0f;
		        fov *= 1.0F - fovModifier * 0.15F;
//				MEMLogger.info("ItemCustomBow fovUpdate(): itemUseDuration = " + event.entity.getItemInUseDuration() + "; fovModifier = " + fovModifier + "; newfov = " + fov);
	            event.setNewfov(fov);
			}
		}
	}

	/**
	 * Helper function for fovUpdate() that allows subclasses to easily overwrite zoom
	 * sequences for bow pull
	 * 
	 */
	protected float getNewFovModifier(int itemInUseDuration) {
        float f = (float)itemInUseDuration / 20.0F;

        if (f > 1.0F) {
            f = 1.0F;
        } else {
            f *= f;
        }

        return f;
	}

	public String getModelBaseName() {
		return modelBaseName;
	}

	protected void setModelBaseName(String modelBaseName) {
		this.modelBaseName = modelBaseName;
	}

	public int getModelVariantCount() {
		return modelVariantCount;
	}

	protected void setModelVariantCount(int modelVariantCount) {
		this.modelVariantCount = modelVariantCount;
	}

	protected Item getItemUsedByBow() {
		return Items.arrow;
	}

}
