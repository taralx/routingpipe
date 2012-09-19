package net.taral.mc.routingpipe;

import net.minecraftforge.client.MinecraftForgeClient;
import buildcraft.transport.TransportProxyClient;

public class ClientInit {

	public ClientInit() {
		MinecraftForgeClient.registerItemRenderer(Main.routingPipe.shiftedIndex, TransportProxyClient.pipeItemRenderer);
	}

}
