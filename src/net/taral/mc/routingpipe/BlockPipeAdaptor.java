package net.taral.mc.routingpipe;

import buildcraft.api.core.Orientations;
import net.minecraft.src.BlockContainer;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockPipeAdaptor extends BlockContainer {

	protected BlockPipeAdaptor(int id) {
		super(id, Material.wood);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(soundWoodFootstep);
		setCreativeTab(CreativeTabs.tabTransport);
		setBlockName("pipeAdaptor");
		//setRequiresSelfNotify();
		GameRegistry.registerBlock(this);
	}

	@Override
	public String getBlockName() {
		// TODO Auto-generated method stub
		return super.getBlockName();
	}

	@Override
	public String getTextureFile() {
		return "/gfx/routingpipe/blocks/blocks.png";
	}

	@Override
	public int getBlockTextureFromSide(int side) {
		return 32;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TilePipeAdaptor();
	}

}
