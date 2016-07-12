package net.unladenswallow.minecraft.quiver.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;
import net.unladenswallow.minecraft.quiver.ClientProxy;
import net.unladenswallow.minecraft.quiver.FFQLogger;
import net.unladenswallow.minecraft.quiver.ModFFQuiver;

/**
 * This class represents an arrow that can be placed into the Bow and Quiver.
 * Specifically, a "quiverable" arrow must provide:
 *  - What entity should spawn when it is used by a Bow and Quiver, including what
 *    damage should be applied to its entity based on time in use of the Bow and Quiver
 *  - What FOV should be displayed based on time in use of the Bow and Quiver
 *  - What the Bow and Quiver's model base name is when using this type of arrow
 *  - What model variant should be used based on time in use of the Bow and Quiver
 *  - What item (possibly itself) should be used in the recipe for restocking the Bow and Quiver
 *  
 *  If there is a non-quiver bow whose behavior we want to mimic, we can use that
 *  to define much of this behavior
 *  
 *  The base ItemQuiverableArrow class represents the special case of the vanilla arrow.
 *  Additional "quiverable" arrow types should extend ItemQuiverableArrow
 *  
 * @author FreneticFeline
 *
 */
public class ItemQuiverableArrow extends Item {

	protected ItemCustomBow bowToMimic;
	protected Item itemUsedByBow;
	
	public ItemQuiverableArrow(String unlocalizedName, ItemCustomBow bowToMimic) {
	    this.setUnlocalizedName(unlocalizedName);
	    this.bowToMimic = bowToMimic;
        this.itemUsedByBow = Items.ARROW;
        this.setRegistryName(ModFFQuiver.MODID, ClientProxy.stripItemPrefix(this.getUnlocalizedName()));
        this.setCreativeTab(CreativeTabs.COMBAT);
	}

	public ItemQuiverableArrow() {
	    this("vanilla_arrow", new ItemCustomBow("vanilla_bow", "minecraft:bow"){});
	}
	
	public String getName() {
        return ("" + I18n.format(this.getUnlocalizedName() + ".name", new Object[0])).trim();
	}
	
	public Item getItemUsedByBow() {
		return this.itemUsedByBow;
	}
	
	public EntityArrow getNewEntityArrow(World worldIn, EntityPlayer playerIn, int itemUseDuration) {
		if (this.getBowToMimick() == null) {
			FFQLogger.warning("ItemQuiverableArrow getnewEntityArrow: I have been called to get a new arrow entity, but I have no bow to mimic.  Subclass needs to override this method.");
			return new EntityTippedArrow(worldIn, playerIn);
		} else {
			return this.getBowToMimick().getNewEntityArrow(worldIn, playerIn, itemUseDuration);
		}
	}

	public String getModelBaseName() {
		if (this.getBowToMimick() == null) {
			FFQLogger.warning("ItemQuiverableArrow getModelBaseName: I have been called to get model base name, but I have no bow to mimic.  Subclass needs to override this method.");
			return "unknown";
		} else {
			return this.getBowToMimick().getModelBaseName();
		}
	}
	
/*	public int getModelVariation(int useTime) {
		if (this.getBowToMimick() == null) {
			FFQLogger.warning("ItemQuiverableArrow getModelVariation: I have been called to get model variation, but I have no bow to mimic.  Subclass needs to override this method.");
			return 0;
		} else {
			return this.getBowToMimick().getModelVariation(useTime);
		}
	}
*/
	public float getNewFovModifier(int useTime) {
		if (this.getBowToMimick() == null) {
			FFQLogger.warning("ItemQuiverableArrow getNewFovModifier: I have been called to get FOV modifier, but I have no bow to mimic.  Subclass needs to override this method.");
			return 1.0f;
		} else {
			return this.getBowToMimick().getNewFovModifier(useTime);
		}
	}

	protected ItemCustomBow getBowToMimick() {
		return this.bowToMimic;
	}
}
