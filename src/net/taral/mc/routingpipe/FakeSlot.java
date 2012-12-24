package net.taral.mc.routingpipe;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FakeSlot extends Slot {

	public FakeSlot(int index, int x, int y) {
		super(null, index, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return false;
	}

	@Override
	public ItemStack getStack() {
		return null;
	}

	@Override
	public void putStack(ItemStack par1ItemStack) {
	}

	@Override
	public void onSlotChanged() {
	}

}
