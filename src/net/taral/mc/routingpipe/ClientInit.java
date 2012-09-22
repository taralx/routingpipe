package net.taral.mc.routingpipe;

import net.minecraftforge.client.MinecraftForgeClient;
import buildcraft.transport.TransportProxyClient;

public class ClientInit {

	public ClientInit() {
		if (Main.routingPipe != null)
			MinecraftForgeClient.registerItemRenderer(Main.routingPipe.shiftedIndex,
					TransportProxyClient.pipeItemRenderer);
		if (Main.insertionPipe != null)
			MinecraftForgeClient.registerItemRenderer(Main.insertionPipe.shiftedIndex,
					TransportProxyClient.pipeItemRenderer);
	}

}
