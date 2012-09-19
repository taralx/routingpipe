package net.taral.mc.routingpipe;

import java.util.logging.Level;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;
import buildcraft.core.utils.Localization;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.ItemPipe;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "RoutingPipe", version = "1.0.1", name = "Routing Pipe", dependencies = "required-after:BuildCraft|Transport")
@NetworkMod(clientSideRequired = true)
public class Main {
	public static final int GUI_ID = 1;

	public static ItemPipe routingPipe;

	@Instance("RoutingPipe")
	public static Main instance;

	@PreInit
	public void preInitialize(FMLPreInitializationEvent evt) {
		int routingPipeId = 24799;

		Configuration cfg = new Configuration(evt.getSuggestedConfigurationFile());
		try {
			cfg.load();
			routingPipeId = cfg.getOrCreateIntProperty("routingPipe", "item", routingPipeId).getInt();
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "Routing Pipe has a problem loading its configuration.");
		} finally {
			cfg.save();
		}

		routingPipe = BlockGenericPipe.registerPipe(routingPipeId, PipeItemsRouting.class);
		routingPipe.setItemName("routingPipe");
		LanguageRegistry.addName(routingPipe, "Routing Pipe");

		MinecraftForge.EVENT_BUS.register(this);
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());

		Localization.addLocalization("/lang/routingpipe/", "en_US");
	}

	@Init
	public void init(FMLInitializationEvent evt) {
		if (routingPipe == null)
			return;

		GameRegistry.addRecipe(new ItemStack(routingPipe, 8), "DGI", 'D', Item.diamond, 'G', Block.glass, 'I', Item.ingotIron);
		GameRegistry.addRecipe(new ItemStack(routingPipe, 8), "IGD", 'D', Item.diamond, 'G', Block.glass, 'I', Item.ingotIron);

		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
			new ClientInit();
	}

	@ForgeSubscribe
	public void handleOre(OreRegisterEvent evt) {
		if (routingPipe != null && evt.Name.equals("ingotBronze")) {
			GameRegistry.addRecipe(new ItemStack(routingPipe), "BGB", 'B', evt.Ore, 'G', Block.glass);
		}
	}
}
