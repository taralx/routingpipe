package net.taral.mc.routingpipe;

import buildcraft.transport.TileGenericPipe;
import buildcraft.transport.pipes.PipeLogicDiamond;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (!(tile instanceof TileGenericPipe))
			return null;

		TileGenericPipe pipe = (TileGenericPipe) tile;

		switch (ID) {
		case Main.GUI_ID:
			return new ContainerRoutingPipe(player.inventory, (PipeLogicRouting) pipe.pipe.logic);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (!(tile instanceof TileGenericPipe))
			return null;

		TileGenericPipe pipe = (TileGenericPipe) tile;

		switch (ID) {
		case Main.GUI_ID:
			return new GuiRoutingPipe(player.inventory, pipe);
		}

		return null;
	}

}
