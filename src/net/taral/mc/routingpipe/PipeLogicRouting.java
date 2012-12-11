package net.taral.mc.routingpipe;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.pipes.PipeLogic;

public class PipeLogicRouting extends PipeLogic {

	public ForgeDirection[] directions;

	public PipeLogicRouting() {
		super();

		directions = new ForgeDirection[6];
		for (int i = 0; i < 6; i++) {
			directions[i] = ForgeDirection.getOrientation(i).getOpposite();
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

		for (int i = 0; i < directions.length; i++) {
			directions[i] = ForgeDirection.getOrientation(nbt.getInteger("directions[" + i + "]"));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		for (int i = 0; i < directions.length; i++) {
			nbt.setInteger("directions[" + i + "]", directions[i].ordinal());
		}
	}

}
