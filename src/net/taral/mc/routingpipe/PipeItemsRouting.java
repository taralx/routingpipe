package net.taral.mc.routingpipe;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import net.minecraftforge.common.ForgeDirection;

import buildcraft.api.core.Position;
import buildcraft.api.transport.IPipedItem;
import buildcraft.core.network.IClientState;
import buildcraft.core.utils.Utils;
import buildcraft.transport.IPipeTransportItemsHook;
import buildcraft.transport.Pipe;
import buildcraft.transport.PipeTransportItems;

public class PipeItemsRouting extends Pipe implements IPipeTransportItemsHook, IClientState {

	public PipeItemsRouting(int itemID) {
		super(new PipeTransportItems(), new PipeLogicRouting(), itemID);

		((PipeTransportItems) transport).allowBouncing = true;
	}

	@Override
	public String getTextureFile() {
		return "/gfx/routingpipe/blocks/blocks.png";
	}

	@Override
	public int getTextureIndex(ForgeDirection direction) {
		if (direction == ForgeDirection.UNKNOWN)
			return 0;
		return direction.ordinal() + 1;
	}

	@Override
	public LinkedList<ForgeDirection> filterPossibleMovements(LinkedList<ForgeDirection> possibleForgeDirection,
			Position pos, IPipedItem item) {
		ForgeDirection cameFrom = pos.orientation.getOpposite();

		if (cameFrom == ForgeDirection.UNKNOWN)
			return possibleForgeDirection;

		PipeLogicRouting routingLogic = (PipeLogicRouting) logic;

		ForgeDirection newOrientation = routingLogic.directions[cameFrom.ordinal()];
		if (newOrientation == cameFrom || possibleForgeDirection.contains(newOrientation)) {
			possibleForgeDirection.clear();
			possibleForgeDirection.add(newOrientation);
		}

		return possibleForgeDirection;
	}

	@Override
	public void entityEntered(IPipedItem item, ForgeDirection orientation) {
	}

	@Override
	public void readjustSpeed(IPipedItem item) {
		// Minimum speed only

		if (item.getSpeed() < Utils.pipeNormalSpeed) {
			item.setSpeed(Utils.pipeNormalSpeed);
		}
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		for (ForgeDirection o : ((PipeLogicRouting) logic).directions) {
			data.writeByte(o.ordinal());
		}
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		ForgeDirection[] directions = ((PipeLogicRouting) logic).directions;
		for (int i = 0; i < directions.length; i++) {
			directions[i] = ForgeDirection.getOrientation(data.readByte());
		}
	}

}
