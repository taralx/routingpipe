package net.taral.mc.routingpipe;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import buildcraft.api.core.Orientations;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.pipes.PipeLogic;

public class PipeLogicRouting extends PipeLogic {

	public Orientations[] orientations;

	public PipeLogicRouting() {
		super();

		orientations = Orientations.dirs();
		for (int i = 0; i < 6; i++) {
			orientations[i] = orientations[i].reverse();
		}
	}

	@Override
	public boolean blockActivated(EntityPlayer player) {
		ItemStack currentEquippedItem = player.getCurrentEquippedItem();

		if (currentEquippedItem != null && currentEquippedItem.itemID < Block.blocksList.length
				&& Block.blocksList[currentEquippedItem.itemID] instanceof BlockGenericPipe)
			return false;

		if (!container.worldObj.isRemote) {
			player.openGui(Main.instance, Main.GUI_ID, container.worldObj, container.xCoord, container.yCoord,
					container.zCoord);
		}

		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		for (int i = 0; i < orientations.length; i++) {
			orientations[i] = Orientations.values()[nbt.getInteger("orientations[" + i + "]")];
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		for (int i = 0; i < orientations.length; i++) {
			nbt.setInteger("orientations[" + i + "]", orientations[i].ordinal());
		}
	}

}
