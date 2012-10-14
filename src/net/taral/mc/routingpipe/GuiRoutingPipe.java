package net.taral.mc.routingpipe;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import buildcraft.transport.TileGenericPipe;

public class GuiRoutingPipe extends GuiContainer {

	private InventoryPlayer playerInventory;
	private PipeLogicRouting logic;

	public GuiRoutingPipe(InventoryPlayer playerInventory, TileGenericPipe tile) {
		super(new ContainerRoutingPipe(playerInventory, (PipeLogicRouting) tile.pipe.logic));
		this.playerInventory = playerInventory;
		logic = (PipeLogicRouting) tile.pipe.logic;

		xSize = 176;
		ySize = 222;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		int tex = mc.renderEngine.getTexture("/gfx/routingpipe/gui/routing.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(tex);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		for (int i = 0; i < logic.orientations.length; i++) {
			int o = logic.orientations[i].ordinal();

			if (o <= 6) {
				drawTexturedModalRect(x + 112, y + 18 + i * 18, xSize + 1, o * 18 + 1, 16, 16);
			}
		}
	}

}
