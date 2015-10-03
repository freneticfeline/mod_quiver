package net.unladenswallow.minecraft.quiver.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.unladenswallow.minecraft.quiver.FFQLogger;

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
	
	public ItemQuiverableArrow() {
		this.setUnlocalizedName("vanillaArrow");
		this.bowToMimic = new ItemCustomBow("vanillaBow", "minecraft:bow"){};
		this.itemUsedByBow = Items.arrow;
		this.setCreativeTab(CreativeTabs.tabCombat);
	}
	
	public String getName() {
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
	}
	
	public Item getItemUsedByBow() {
		return this.itemUsedByBow;
	}
	
	public EntityArrow getNewEntityArrow(World worldIn, EntityPlayer playerIn, float damage, int itemUseDuration) {
		if (this.getBowToMimick() == null) {
			FFQLogger.warning("ItemQuiverableArrow getnewEntityArrow: I have been called to get a new arrow entity, but I have no bow to mimic.  Subclass needs to override this method.");
			return new EntityArrow(worldIn, playerIn, damage);
		} else {
			return this.getBowToMimick().getNewEntityArrow(worldIn, playerIn, damage, itemUseDuration);
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
	
	public int getModelVariation(int useTime) {
		if (this.getBowToMimick() == null) {
			FFQLogger.warning("ItemQuiverableArrow getModelVariation: I have been called to get model variation, but I have no bow to mimic.  Subclass needs to override this method.");
			return 0;
		} else {
			return this.getBowToMimick().getModelVariation(useTime);
		}
	}

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
