package net.unladenswallow.minecraft.quiver.item;

public class ItemExplodingArrow extends ItemQuiverableArrow {

	public ItemExplodingArrow() {
		super();
		setUnlocalizedName("explodingArrow");
		this.bowToMimic = new ItemExplosionBow();
		this.itemUsedByBow = this;
	}
}
