package net.taral.mc.routingpipe;

import java.util.Iterator;
import java.util.LinkedList;

import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraftforge.common.ForgeDirection;
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
	public int getTextureIndex(ForgeDirection direction) {
		return 16;
	}

	@Override
	public LinkedList<ForgeDirection> filterPossibleMovements(LinkedList<ForgeDirection> possibleForgeDirection,
			Position pos, IPipedItem item) {
		Iterator<ForgeDirection> iterator = possibleForgeDirection.iterator();
		while (iterator.hasNext()) {
			ForgeDirection o = iterator.next();
			TileEntity tile = container.getTile(o);

			if (tile instanceof TileGenericPipe) {
				continue;
			}

			ITransactor transactor = Transactor.getTransactorFor(tile);
			ItemStack stack = item.getItemStack();
			ItemStack added = transactor.add(stack, o.getOpposite(), true);
			stack.stackSize -= added.stackSize;
			if (stack.stackSize == 0) {
				((PipeTransportItems) transport).scheduleRemoval(item);
				possibleForgeDirection.clear();
				break;
			}
			item.setItemStack(stack);
			iterator.remove();
		}
		return possibleForgeDirection;
	}

	@Override
	public void entityEntered(IPipedItem item, ForgeDirection orientation) {
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
