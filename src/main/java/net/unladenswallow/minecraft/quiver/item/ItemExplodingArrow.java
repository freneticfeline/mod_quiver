package net.unladenswallow.minecraft.quiver.item;

public class ItemExplodingArrow extends ItemQuiverableArrow {

	public ItemExplodingArrow() {
		super("exploding_arrow", new ItemExplosionBow());
		this.itemUsedByBow = this;
	}
}
