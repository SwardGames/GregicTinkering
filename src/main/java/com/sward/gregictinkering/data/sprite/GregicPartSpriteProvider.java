package com.sward.gregictinkering.data.sprite;

import com.sward.gregictinkering.GregicTinkeringMod;
import com.sward.gregictinkering.materials.stats.*;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

public class GregicPartSpriteProvider extends AbstractPartSpriteProvider
{
	private static final String[] CRAFTING_TOOLS = { "wrench", "hammer", "file", "screwdriver", "saw", "wire_cutter", "crowbar" };

	public GregicPartSpriteProvider()
	{
		super(GregicTinkeringMod.MOD_ID);
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Parts";
	}

	@Override
	protected void addAllSpites()
	{
		// Parts
		addHead("drill_head");

		// Tools
		for (String craftingTool : CRAFTING_TOOLS)
		{
			buildTool(craftingTool)
				.addBreakableHead("head")
				.addBinding("binding")
				.addHandle("handle");
		}

		buildTool("soft_mallet")
			.addBreakablePart("head", SoftMalletHeadMaterialStats.ID)
			.addBinding("binding")
			.addHandle("handle");

		buildTool("plunger")
			.addBreakablePart("head", PlungerHeadMaterialStats.ID)
			.addBinding("binding")
			.addHandle("handle");

		buildTool("drill")
			.addBreakableHead("head")
			.addPart("casing", GregicStatlessMaterialStats.CASING.getIdentifier());

		buildTool("chainsaw")
			.addBreakableHead("head")
			.addPart("casing", GregicStatlessMaterialStats.CASING.getIdentifier());

		buildTool("power_tool")
			.addPart("engine", EngineMaterialStats.ID)
			.addPart("battery", BatteryMaterialStats.ID);

		buildTool("drill")
			.addPart("head_active", HeadMaterialStats.ID)
			.disallowAnimated();

		buildTool("chainsaw")
			.addPart("head_active", HeadMaterialStats.ID)
			.disallowAnimated();
	}
}