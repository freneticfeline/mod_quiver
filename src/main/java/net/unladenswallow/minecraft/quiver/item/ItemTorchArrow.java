package net.unladenswallow.minecraft.quiver.item;

public class ItemTorchArrow extends ItemQuiverableArrow {

	public ItemTorchArrow() {
		super("torch_arrow", new ItemTorchBow());
		this.itemUsedByBow = this;
	}
}
