package net.unladenswallow.minecraft.quiver.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.unladenswallow.minecraft.quiver.ModFFQuiver;
import net.unladenswallow.minecraft.quiver.entity.EntityExplodingArrow;

public class ItemExplosionBow extends ItemCustomBow {

	public ItemExplosionBow() {
		super("explosionBow", ModFFQuiver.MODID + ":" + "explosion_bow");
		this.setModelVariantCount(3);
	}
	
	@Override
	protected boolean shotIsCritical(int itemUseDuration, float arrowDamage) {
        return (itemUseDuration > 500);
	}

	@Override
	protected float getArrowDamage(int itemUseDuration) {
        float f = (float)itemUseDuration / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 0.75F)
        {
            f = 0.75F;
        }
        return f;
	}

	@Override
	protected void applyEnchantments(EntityArrow entityarrow, ItemStack stack) {
		// No enchantments apply
	}

	@Override
	protected EntityArrow getNewEntityArrow(World worldIn, EntityPlayer playerIn, int itemUseDuration) {
        float radiusMod = 1.0F;
        if (itemUseDuration > 500) {
        	radiusMod = 25.0F;
        } else if (itemUseDuration > 200) {
        	radiusMod = 11.0F;
        } else if (itemUseDuration > 150) {
        	radiusMod = 7.0F;
        } else if (itemUseDuration > 100) {
        	radiusMod = 4.0F;
        } else if (itemUseDuration > 50) {
        	radiusMod = 2.0F;
        }

		return new EntityExplodingArrow(worldIn, playerIn, radiusMod);
	}

	@Override
	protected Item getItemUsedByBow() {
		return ModFFQuiver.explodingArrow;
	}

	protected float getNewFovModifier(int itemInUseDuration) {
        float f = (float)itemInUseDuration / 20.0F;

        if (itemInUseDuration > 500) {
        	f = 1.8f + ((float)(itemInUseDuration % 2) * 0.2F);
        } else if (itemInUseDuration > 200) {
        	f = 1.8f;
        } else if (itemInUseDuration > 150) {
        	f = 1.6f;
        } else if (itemInUseDuration > 100) {
        	f = 1.4f;
        } else if (itemInUseDuration > 50) {
        	f = 1.2f;
        } else if (f > 1.0F) {
            f = 1.0F;
        } else {
            f *= f;
        }

        return f;
	}
//	
//	
//	
//	
//	
//	private static final String modelBaseName = "explosion_bow";
//    public static final String[] bowPullIconNameArray = new String[] {"pulling_0", "pulling_1", "pulling_2"};
//
//	public ExplosionBow() {
//		super();
//		setUnlocalizedName("explosionBow");
//		setCreativeTab(CreativeTabs.tabCombat);
//	}
//	
//    /**
//     * Called when the player stops using an Item (stops holding the right mouse button).
//     *  
//     * @param timeLeft The amount of ticks left before the using would have been complete
//     */
//	@Override
//    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft)
//    {
//        int j = this.getMaxItemUseDuration(stack) - timeLeft;
//        net.minecraftforge.event.entity.player.ArrowLooseEvent event = new net.minecraftforge.event.entity.player.ArrowLooseEvent(playerIn, stack, j);
//        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return;
//        j = event.charge;
//
//        boolean flag = playerIn.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
//
//        if (flag || playerIn.inventory.hasItem(ModEmeraldMaterial.explodingArrow))
//        {
//            float f = (float)j / 20.0F;
//            f = (f * f + f * 2.0F) / 3.0F;
//
//            if ((double)f < 0.1D)
//            {
//                return;
//            }
//
////            FMLRelaunchLog.info("ExplosionBow onPlayerStoppedUsing(): f = " + f + "; j = " + j + "; timeLeft = " + timeLeft);
//            
//            if (f > 0.7F)
//            {
//                f = 0.7F;
//            }
//            
//            float radiusMod = 1.0F;
//            if (j > 500) {
//            	radiusMod = 30.0F;
//            } else if (j > 200) {
//            	radiusMod = 14.0F;
//            } else if (j > 150) {
//            	radiusMod = 9.0F;
//            } else if (j > 100) {
//            	radiusMod = 5.0F;
//            } else if (j > 50) {
//            	radiusMod = 2.0F;
//            }
//            
//            EntityExplodingArrow entityexplodingarrow = new EntityExplodingArrow(worldIn, playerIn, f * 2.0F, radiusMod);
//
//            if (j > 50) {
//            	entityexplodingarrow.setIsCritical(true);
//            }
//            
//            stack.damageItem(1, playerIn);
//            worldIn.playSoundAtEntity(playerIn, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
//
//            if (flag)
//            {
//                entityexplodingarrow.canBePickedUp = 2;
//            }
//            else
//            {
//                playerIn.inventory.consumeInventoryItem(ModEmeraldMaterial.explodingArrow);
//            }
//
//            playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
//
//            if (!worldIn.isRemote)
//            {
//                worldIn.spawnEntityInWorld(entityexplodingarrow);
//            }
//        }
//    }
//
//	@Override
//    public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
//    {
//		int useTime = getMaxItemUseDuration(stack) - useRemaining;
////		FMLRelaunchLog.info("EmeraldBow getModel(): useRemaining = " + useRemaining);
////		FMLRelaunchLog.info("EmeraldBow getModel(): useTime = " + useTime);
//        ModelResourceLocation modelresourcelocation = new ModelResourceLocation(ModEmeraldMaterial.MODID + ":" + modelBaseName, "inventory");
//
//        if(stack.getItem() == this && player.getItemInUse() != null && useRemaining > 0)
//        {
//        	if(useTime >= 21)
//            {
//        		modelresourcelocation = new ModelResourceLocation(ModEmeraldMaterial.MODID + ":" + modelBaseName + "_" + bowPullIconNameArray[2], "inventory");
//            }
//            else if(useTime > 10)
//            {
//                modelresourcelocation = new ModelResourceLocation(ModEmeraldMaterial.MODID + ":" + modelBaseName + "_" + bowPullIconNameArray[1], "inventory");
//            }
//            else if(useTime > 0)
//            {
//                modelresourcelocation = new ModelResourceLocation(ModEmeraldMaterial.MODID + ":" + modelBaseName + "_" + bowPullIconNameArray[0], "inventory");
//            }
//        }
////        FMLRelaunchLog.info("EmeraldBow getModel(): modelResourcelocation is " + modelresourcelocation.getResourceDomain() + ":" + modelresourcelocation.getResourcePath());
//        return modelresourcelocation;
//    }
//	
//    /**
//     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
//     */
//	@Override
//    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
//    {
//        net.minecraftforge.event.entity.player.ArrowNockEvent event = new net.minecraftforge.event.entity.player.ArrowNockEvent(playerIn, itemStackIn);
//        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return event.result;
//
//        if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItem(ModEmeraldMaterial.explodingArrow))
//        {
//            playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
//        }
//
//        return itemStackIn;
//    }
//
//
//    @SubscribeEvent
//	public void fovUpdate(FOVUpdateEvent event) {
//		if (event.entity instanceof EntityPlayer) {
//			if (event.entity.isUsingItem() && event.entity.getItemInUse().getItem() == this) {
//		        float f = 1.0F;
//	            int i = event.entity.getItemInUseDuration();
//	            float f1 = (float)i / 20.0F;
//
//	            if (i > 500) {
//	            	f1 = 1.8f + ((float)(i % 2) * 0.2F);
//	            } else if (i > 200) {
//	            	f1 = 1.8f;
//	            } else if (i > 150) {
//	            	f1 = 1.6f;
//	            } else if (i > 100) {
//	            	f1 = 1.4f;
//	            } else if (i > 50) {
//	            	f1 = 1.2f;
//	            } else if (f1 > 1.0F) {
//	                f1 = 1.0F;
//	            } else {
//	                f1 *= f1;
//	            }
//
//	            f *= 1.0F - f1 * 0.15F;
////				FMLRelaunchLog.info("TorchBow fovUpdate(): i = " + i + "; fov = " + event.fov + "; newfov = " + f);
//	            
//	            event.newfov = f;
//			}
//		}
//	}
//
}
