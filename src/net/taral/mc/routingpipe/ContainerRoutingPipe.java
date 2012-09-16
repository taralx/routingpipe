package net.taral.mc.routingpipe;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import buildcraft.api.core.Orientations;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class ContainerRoutingPipe extends Container {

	public PipeLogicRouting logic;

	public ContainerRoutingPipe(InventoryPlayer playerInventory, PipeLogicRouting logic) {
		this.logic = logic;

		for (int slot = 0; slot < 9; slot++)
			addSlotToContainer(new Slot(playerInventory, slot, 8 + slot * 18, 198));

		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 9; col++)
				addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 140 + row * 18));

		for (int o = 0; o < 6; o++)
			addSlotToContainer(new FakeSlot(o + 36, 112, 18 + o * 18));
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	@Override
	public ItemStack slotClick(int slotNumber, int button, boolean shiftPresssed, EntityPlayer player) {
		if (slotNumber >= 36) {
			if (!player.worldObj.isRemote) {
				int o = logic.orientations[slotNumber - 36].ordinal();
				if (button == 0) {
					o = o + 1;
				} else {
					o = o + 5;
				}
				logic.orientations[slotNumber - 36] = Orientations.values()[o % 6];
			}
			return null;
		}
		return super.slotClick(slotNumber, button, shiftPresssed, player);
	}

	@Override
	public void updateCraftingResults() {
		super.updateCraftingResults();

		int data = 0;
		for (Orientations o : logic.orientations) {
			data = (data << 4) | o.ordinal();
		}

		for (Object crafter : crafters) {
			((ICrafting) crafter).updateCraftingInventoryInfo(this, 0, data);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		if (id != 0) {
			super.updateProgressBar(id, data);
			return;
		}

		int n = logic.orientations.length;
		for (int i = n - 1; i >= 0; i--) {
			logic.orientations[i] = Orientations.values()[data & 15];
			data = data >> 4;
		}
	}

}