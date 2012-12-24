package net.taral.mc.routingpipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public class ContainerRoutingPipe extends Container {

	public PipeLogicRouting logic;

	public ContainerRoutingPipe(InventoryPlayer playerInventory, PipeLogicRouting logic) {
		this.logic = logic;

		for (int slot = 0; slot < 9; slot++) {
			addSlotToContainer(new Slot(playerInventory, slot, 8 + slot * 18, 198));
		}

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 9; col++) {
				addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 140 + row * 18));
			}
		}

		for (int o = 0; o < 6; o++) {
			addSlotToContainer(new FakeSlot(o + 36, 112, 18 + o * 18));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	@Override
	public ItemStack slotClick(int slotNumber, int button, int shiftPressed, EntityPlayer player) {
		if (slotNumber >= 36) {
			if (!player.worldObj.isRemote) {
				int o = logic.directions[slotNumber - 36].ordinal();
				if (button == 0) {
					o = o + 1;
				} else {
					o = o + 5;
				}
				logic.directions[slotNumber - 36] = ForgeDirection.getOrientation(o % 6);
				logic.worldObj.markBlockForUpdate(logic.xCoord, logic.yCoord, logic.zCoord);
			}
			return null;
		}
		return super.slotClick(slotNumber, button, shiftPressed, player);
	}

}
