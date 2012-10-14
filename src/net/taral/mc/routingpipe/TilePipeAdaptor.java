package net.taral.mc.routingpipe;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import cpw.mods.fml.common.FMLLog;

import buildcraft.api.core.Orientations;
import buildcraft.core.utils.Utils;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;

public class TilePipeAdaptor extends TileEntity implements IInventory {

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (worldObj.isRemote)
			return;

		if (Utils.addToRandomPipeEntry(this, Orientations.Unknown, stack))
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
