package net.unladenswallow.minecraft.quiver.item;

import java.util.List;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.unladenswallow.minecraft.quiver.FFQLogger;
import net.unladenswallow.minecraft.quiver.ModFFQuiver;

public class ItemBowAndQuiver extends ItemCustomBow {

	protected ItemQuiverableArrow quiverableArrow;
	
	public ItemBowAndQuiver(String unlocalizedName, ItemQuiverableArrow quiverableArrow) {
		super(unlocalizedName, "minecraft:bow");
		this.quiverableArrow = quiverableArrow;
		this.setMaxDamage(64);
	}

    protected ItemQuiverableArrow getQuiverableArrow() {
		return quiverableArrow;
	}

	@Override
    protected boolean isUsableByPlayer(ItemStack stack, EntityPlayer playerIn) {
//		FFLogger.info("ItemBowAndQuiver isUsableByPlayer: maxItemUseDuration = " + this.getMaxItemUseDuration(stack));
		/* Damage is being used as "ammo count", so it's usable as long as it isn't fully damaged.
		 * This also means that the bow will never break, because it can't be used the last time. */
//		FFLogger.info("ItemBowAndQuiver isUsableByPlayer: " 
//				+ (this.getQuiverableArrow() != null
//				&& (hasInfiniteArrows(stack, playerIn) || (stack.getItemDamage() < stack.getMaxDamage())
//					       ))
//				+ " (type = " + getTypeFromMeta(stack.getMetadata()) 
//				+ "; stack.getItemDamage() = " + stack.getItemDamage() + "; stack.getMaxDamage() = " + stack.getMaxDamage() + ")");
		return (this.getQuiverableArrow() != null
				&& (hasInfiniteArrows(stack, playerIn) || (stack.getItemDamage() < stack.getMaxDamage())
			       ));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
//		logCurrentStackState(stack);
		super.addInformation(stack, playerIn, tooltip, advanced);
		int ammoCount = stack.getMaxDamage() - stack.getItemDamage();
		tooltip.add(TextFormatting.GRAY.toString() + "Quiver: "
				+ (this.getQuiverableArrow() == null ? "empty"
								: ammoCount + " / " + stack.getMaxDamage() + " " 
								  + TextFormatting.BLUE.toString() + this.getQuiverableArrow().getName() + (stack.getItemDamage() > 1 ? "s" : "")
				  ));
		if (this.getQuiverableArrow() == null) {
			tooltip.add(TextFormatting.GRAY.toString() + TextFormatting.ITALIC.toString()
				+ "Load with " + "8 or 3 of any arrow type");
		} else if (stack.getItemDamage() > 0) {
			tooltip.add(TextFormatting.GRAY.toString() + TextFormatting.ITALIC.toString()
				+ "Reload with " + Math.min(8, stack.getItemDamage()) + (stack.getItemDamage() > 3 ? " or 3 " : " ")
				+ this.getQuiverableArrow().getName()	+ (stack.getItemDamage() > 1 ? "s" : "")
					);
		}
		
	}

	@SuppressWarnings("unused")
	private void logCurrentStackState(ItemStack stack) {
		FFQLogger.info("The current state of this " + stack.getDisplayName() + " is...\n"
				+ "damage = " + stack.getItemDamage() + "; "
				+ "arrowCount = " + getArrowCount(stack)
				);
		
	}

	@Override
    public boolean isRepairable()
    {
        return false;
    }
	
	private int getArrowCount(ItemStack stack) {
		return this.getMaxDamage() - stack.getItemDamage();
	}

	/**
	 *  Add a recipe for each possible damage amount for the BowAndQuiver
	 **/
	public void addRepairRecipes() {
		if (this.getQuiverableArrow() != null) {
			Object[] params;
//			FFLogger.info("ItemBowAndQuiver addRepairRecipes(): Adding recipes for " + this.getUnlocalizedName());
			Item recipeArrow = this.getQuiverableArrow().getItemUsedByBow();
			/* First add a recipe for empty quiver and bow and both 3 and 8 of this bow's arrow item */
			params = new Object[4];
			fillParamsArrayWithItems(params, recipeArrow);
			params[3] = new ItemStack(ModFFQuiver.emptyBowAndQuiver, 1);
//			FFLogger.info("ItemBowAndQuiver addRepairRecipes(): Using " + recipeArrow.getUnlocalizedName()
//				+ " with damage = " + this.getMaxDamage());
			GameRegistry.addShapelessRecipe(new ItemStack(this, 1, this.getMaxDamage() - 3), params);
			params = new Object[9];
			fillParamsArrayWithItems(params, recipeArrow);
			params[8] = new ItemStack(ModFFQuiver.emptyBowAndQuiver, 1);
			GameRegistry.addShapelessRecipe(new ItemStack(this, 1, this.getMaxDamage() - 8), params);
			/* Add recipes for adding arrows to this variant of the BowAndQuiver, for each possible damage value of the BowAndQuiver */
			for (int i = 1; i <= this.getMaxDamage(); i++) {
				/* Only allow "reloading" 8 arrows at a time (full crafting table), 
				 * or 3 arrows at a time (full inventory crafting box),
				 * unless there are fewer than 8 missing */
				int ammoCount = Math.min(8, i);
				int newDamage = i-ammoCount;
				params = new Object[ammoCount+1];
				//				for (int j = 0; j < ammoCount; j++) {
				//					params[j] = new ItemStack(Items.arrow);
				//				}
				fillParamsArrayWithItems(params, recipeArrow);
				params[ammoCount] = new ItemStack(this, 1, i);
//				FFLogger.info("ItemBowAndQuiver addRepairRecipes(): Adding recipe for BAQ with damage = "
//						+ newDamage + " and " + ammoCount + " of " + recipeArrow.getUnlocalizedName());
				GameRegistry.addShapelessRecipe(new ItemStack(this, 1, newDamage), params);
				if (i > 3) {
					params = new Object[4];
					fillParamsArrayWithItems(params, recipeArrow);
					params[3] = new ItemStack(this, 1, i);
					newDamage = i-3;
//					FFLogger.info("ItemBowAndQuiver addRepairRecipes(): Adding recipe for BAQ with damage = "
//							+ newDamage + " and " + 3 + " of " + recipeArrow.getUnlocalizedName());
					GameRegistry.addShapelessRecipe(new ItemStack(this, 1, newDamage), params);
				}
			}
		}
	}

	private void fillParamsArrayWithItems(Object[] params, Item ammoItem) {
//		FFLogger.info("ItemBowAndQuiver fillParamsArrayWithItems(): adding " + params.length + " instances of " + ammoItem.getUnlocalizedName());
		for (int j = 0; j < params.length; j++) {
			params[j] = new ItemStack(ammoItem);
		}
	}

	@Override
	protected void consumeAmmo(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        // Do nothing.  Ammo is "consumed" by the bow taking damage
	}

	@Override
	protected void takeDamage(int i, ItemStack stack, EntityPlayer playerIn) {
		if (EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) == 0) {
			stack.damageItem(1, playerIn);
		}
        if (stack.getItemDamage() == stack.getMaxDamage()) {
//        	FFLogger.info("ItemBowAndQuiver takeDamage(): Quiver empty.  Reverting to default state.");
        	playerIn.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ModFFQuiver.emptyBowAndQuiver));
        	playerIn.getHeldItemMainhand().setItemDamage(0);
        }
//        logCurrentStackState(stack);
	}


    @Override
	protected EntityArrow getNewEntityArrow(World worldIn, EntityPlayer playerIn, int itemUseDuration) {
		return this.getQuiverableArrow().getNewEntityArrow(worldIn, playerIn, itemUseDuration);
	}

    @Override
	protected float getNewFovModifier(int itemInUseDuration) {
    	if (this.getQuiverableArrow() == null) {
//    		FFLogger.info("ItemBowAndQuiver getNewFovModifier: null quiverableArrow for " + this.getUnlocalizedName());
    		return super.getNewFovModifier(itemInUseDuration);
    	} else {
//    		FFLogger.info("ItemBowAndQuiver getNewFovModifier: quiverableArrow = " + (this.getQuiverableArrow() == null ? "null" : this.getQuiverableArrow().getName()));
//    		FFLogger.info("ItemBowAndQuiver getNewFovModifier: itemInUseDuration = " + itemInUseDuration
//    				+ "; newFovModifier = " + this.getQuiverableArrow().getNewFovModifier(itemInUseDuration));
    		return this.getQuiverableArrow().getNewFovModifier(itemInUseDuration);
    	}
    }

}
