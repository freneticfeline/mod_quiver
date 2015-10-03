package net.unladenswallow.minecraft.quiver.item;

public class ItemTeleportArrow extends ItemQuiverableArrow {

	public ItemTeleportArrow() {
		super();
		setUnlocalizedName("teleportArrow");
		this.bowToMimic = new ItemTeleportBow();
		this.itemUsedByBow = this;
	}

}
