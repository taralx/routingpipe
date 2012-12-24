package net.taral.mc.routingpipe;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.core.utils.Utils;

public class TilePipeAdaptor extends TileEntity implements IInventory {

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (worldObj.isRemote)
			return;

		if (Utils.addToRandomPipeEntry(this, ForgeDirection.UNKNOWN, stack))
			return;

		double theta = worldObj.rand.nextFloat() * 2 * Math.PI;
		double phi = worldObj.rand.nextFloat() * Math.PI;

		double dx = Math.sin(phi) * Math.cos(theta);
		double dy = Math.cos(phi);
		double dz = Math.sin(phi) * Math.sin(theta);
		double scale = Math.max(Math.max(Math.abs(dx), Math.abs(dy)), Math.abs(dz)) * 2;

		EntityItem entity = new EntityItem(worldObj, xCoord + 0.5F + dx / scale, yCoord + 0.5F + dy / scale,
				zCoord + 0.5F + dz / scale, stack);

		entity.motionX = dx * 0.05;
		entity.motionY = dy * 0.05;
		entity.motionZ = dz * 0.05;
		worldObj.spawnEntityInWorld(entity);
	}

	// Boring IInventory methods

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int n) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;
	}

	@Override
	public String getInvName() {
		return "Pipe Adaptor";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return false;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

}
