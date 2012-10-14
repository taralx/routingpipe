package net.taral.mc.routingpipe;

import java.util.Iterator;
import java.util.LinkedList;

import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import buildcraft.api.core.Orientations;
import buildcraft.api.core.Position;
import buildcraft.api.transport.IPipedItem;
import buildcraft.core.inventory.ITransactor;
import buildcraft.core.inventory.Transactor;
import buildcraft.core.utils.Utils;
import buildcraft.transport.IPipeTransportItemsHook;
import buildcraft.transport.Pipe;
import buildcraft.transport.PipeTransportItems;
import buildcraft.transport.TileGenericPipe;
import buildcraft.transport.pipes.PipeLogic;

public class PipeItemsInsertion extends Pipe implements IPipeTransportItemsHook {

	public PipeItemsInsertion(int itemID) {
		super(new PipeTransportItems(), new PipeLogic(), itemID);
	}

	@Override
	public String getTextureFile() {
		return "/gfx/routingpipe/blocks/blocks.png";
	}

	@Override
	public int getTextureIndex(Orientations direction) {
		return 16;
	}

	@Override
	public LinkedList<Orientations> filterPossibleMovements(LinkedList<Orientations> possibleOrientations,
			Position pos, IPipedItem item) {
		Iterator<Orientations> iterator = possibleOrientations.iterator();
		while (iterator.hasNext()) {
			Orientations o = iterator.next();
			TileEntity tile = container.getTile(o);

			if (tile instanceof TileGenericPipe) {
				continue;
			}

			ITransactor transactor = Transactor.getTransactorFor(tile);
			ItemStack stack = item.getItemStack();
			ItemStack added = transactor.add(stack, o.reverse(), true);
			stack.stackSize -= added.stackSize;
			if (stack.stackSize == 0) {
				((PipeTransportItems) transport).scheduleRemoval(item);
				possibleOrientations.clear();
				break;
			}
			item.setItemStack(stack);
			iterator.remove();
		}
		return possibleOrientations;
	}

	@Override
	public void entityEntered(IPipedItem item, Orientations orientation) {
	}

	@Override
	public void readjustSpeed(IPipedItem item) {
		if (item.getSpeed() > Utils.pipeNormalSpeed) {
			item.setSpeed(item.getSpeed() - Utils.pipeNormalSpeed / 2.0F);
		}

		if (item.getSpeed() < Utils.pipeNormalSpeed) {
			item.setSpeed(Utils.pipeNormalSpeed);
		}
	}

}
