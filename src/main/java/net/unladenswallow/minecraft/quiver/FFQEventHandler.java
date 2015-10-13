package net.unladenswallow.minecraft.quiver;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.unladenswallow.minecraft.quiver.item.ItemBowAndQuiver;

public class FFQEventHandler {

	@SubscribeEvent
	public void onItemCraftedEvent(ItemCraftedEvent event) {
		if (event.crafting != null && event.crafting.getItem() instanceof ItemBowAndQuiver) {
			for (int i = 0; i < event.craftMatrix.getSizeInventory(); i++) {
				if (event.craftMatrix.getStackInSlot(i) != null
						&& event.craftMatrix.getStackInSlot(i).getItem() instanceof ItemBowAndQuiver
						&& event.craftMatrix.getStackInSlot(i).isItemEnchanted() ) {
					EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(event.craftMatrix.getStackInSlot(i)), 
							event.crafting);
				}
			}
		}
	}

}
