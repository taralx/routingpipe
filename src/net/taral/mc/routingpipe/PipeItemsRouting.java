package net.taral.mc.routingpipe;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import buildcraft.api.core.Orientations;
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
	public int getTextureIndex(Orientations direction) {
		if (direction == Orientations.Unknown)
			return 0;
		return direction.ordinal() + 1;
	}

	@Override
	public LinkedList<Orientations> filterPossibleMovements(LinkedList<Orientations> possibleOrientations,
			Position pos, IPipedItem item) {
		Orientations cameFrom = pos.orientation.reverse();

		if (cameFrom == Orientations.Unknown)
			return possibleOrientations;

		PipeLogicRouting routingLogic = (PipeLogicRouting) logic;

		Orientations newOrientation = routingLogic.orientations[cameFrom.ordinal()];
		if (newOrientation == cameFrom || possibleOrientations.contains(newOrientation)) {
			possibleOrientations.clear();
			possibleOrientations.add(newOrientation);
		}

		return possibleOrientations;
	}

	@Override
	public void entityEntered(IPipedItem item, Orientations orientation) {
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
		for (Orientations o : ((PipeLogicRouting) logic).orientations) {
			data.writeByte(o.ordinal());
		}
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		Orientations[] values = Orientations.values();
		Orientations[] orientations = ((PipeLogicRouting) logic).orientations;
		for (int i = 0; i < orientations.length; i++) {
			orientations[i] = values[data.readByte()];
		}
	}

}
