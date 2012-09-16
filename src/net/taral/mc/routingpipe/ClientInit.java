package net.taral.mc.routingpipe;

import net.minecraftforge.client.MinecraftForgeClient;
import buildcraft.transport.TransportProxyClient;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientInit {

	public ClientInit() {
		MinecraftForgeClient.registerItemRenderer(Main.routingPipe.shiftedIndex,
				TransportProxyClient.pipeItemRenderer);
	}

}
