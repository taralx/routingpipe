package net.taral.mc.routingpipe;

import java.net.URL;

import net.minecraft.src.StringTranslate;
import net.minecraftforge.client.MinecraftForgeClient;
import buildcraft.core.utils.Localization;
import buildcraft.transport.TransportProxyClient;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ClientProxy {

	public ClientProxy() {
		if (Main.routingPipe != null) {
			MinecraftForgeClient.registerItemRenderer(Main.routingPipe.shiftedIndex,
					TransportProxyClient.pipeItemRenderer);
		}
		if (Main.insertionPipe != null) {
			MinecraftForgeClient.registerItemRenderer(Main.insertionPipe.shiftedIndex,
					TransportProxyClient.pipeItemRenderer);
		}

		MinecraftForgeClient.preloadTexture("/gfx/routingpipe/blocks/blocks.png");

		loadLocalization("/lang/routingpipe/");
		Localization.addLocalization("/lang/routingpipe/bc/", "en_US");
	}

	private void loadLocalization(String prefix) {
		tryLoadLocalization(prefix, "en_US");
		tryLoadLocalization(prefix, StringTranslate.getInstance().getCurrentLanguage());
	}

	private void tryLoadLocalization(String prefix, String lang) {
		URL resource = getClass().getResource(prefix + lang + ".properties");
		if (resource == null)
			return;

		LanguageRegistry.instance().loadLocalization(resource, lang, false);
	}

}
