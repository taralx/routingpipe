package net.taral.mc.routingpipe;

import java.util.logging.Level;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import buildcraft.core.utils.Localization;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.ItemPipe;

import com.google.common.base.Throwables;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "RoutingPipe", version = "1.1.1", name = "Routing Pipe", dependencies = "required-after:BuildCraft|Transport")
@NetworkMod(clientSideRequired = true, versionBounds = "[1.1.1,)")
public class Main {
	public static final int GUI_ID = 1;

	public static ItemPipe routingPipe;
	public static ItemPipe insertionPipe;

	@Instance("RoutingPipe")
	public static Main instance;

	@PreInit
	public void preInitialize(FMLPreInitializationEvent evt) {
		int routingPipeId = 24799;
		int insertionPipeId = 24798;

		Configuration cfg = new Configuration(evt.getSuggestedConfigurationFile());
		try {
			cfg.load();
			routingPipeId = cfg.getOrCreateIntProperty("routingPipe", "item", routingPipeId).getInt();
			insertionPipeId = cfg.getOrCreateIntProperty("insertionPipeId", "item", insertionPipeId).getInt();
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "Routing Pipe has a problem loading its configuration.");
			Throwables.propagate(e);
		} finally {
			cfg.save();
		}

		if (routingPipeId != 0) {
			routingPipe = BlockGenericPipe.registerPipe(routingPipeId, PipeItemsRouting.class);
			routingPipe.setItemName("routingPipe");
			LanguageRegistry.addName(routingPipe, "Routing Pipe");
		}

		if (insertionPipeId != 0) {
			insertionPipe = BlockGenericPipe.registerPipe(insertionPipeId, PipeItemsInsertion.class);
			insertionPipe.setItemName("insertionPipe");
			LanguageRegistry.addName(insertionPipe, "Insertion Pipe");
		}

		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());

		Localization.addLocalization("/lang/routingpipe/", "en_US");
	}

	@Init
	public void init(FMLInitializationEvent evt) {
		if (routingPipe != null) {
			GameRegistry.addRecipe(new ItemStack(routingPipe, 8), "DGI", 'D', Item.diamond, 'G', Block.glass, 'I',
					Item.ingotIron);
			GameRegistry.addRecipe(new ItemStack(routingPipe, 8), "IGD", 'D', Item.diamond, 'G', Block.glass, 'I',
					Item.ingotIron);
		}
		if (insertionPipe != null) {
			GameRegistry.addRecipe(new ItemStack(insertionPipe, 8), " R ", "SGS", 'R', Item.redstone, 'S', Block.stone,
					'G', Block.glass);
		}

		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
			new ClientInit();
	}

	@PostInit
	public void postInitialize(FMLPostInitializationEvent evt) {
		if (routingPipe != null) {
			CraftingManager.getInstance().getRecipeList()
					.add(new ShapedOreRecipe(routingPipe, "BGB", 'B', "ingotBronze", 'G', Block.glass));
		}
	}
}
