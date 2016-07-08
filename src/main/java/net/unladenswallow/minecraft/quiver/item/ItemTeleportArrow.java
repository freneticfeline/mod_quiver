package net.unladenswallow.minecraft.quiver.item;

public class ItemTeleportArrow extends ItemQuiverableArrow {

	public ItemTeleportArrow() {
		super("teleport_arrow", new ItemTeleportBow());
		this.itemUsedByBow = this;
	}

}
