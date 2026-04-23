package com.sward.gregictinkering.data.sprite;

import com.sward.gregictinkering.GregicTinkeringMod;
import com.sward.gregictinkering.materials.stats.*;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

public class GregicPartSpriteProvider extends AbstractPartSpriteProvider
{
	private static final String[] CRAFTING_TOOLS = { "wrench", "hammer", "file", "screwdriver", "saw", "wire_cutter", "crowbar" };

	private static final String[] POWER_TOOLS = { "drill", "chainsaw", "powered_wrench", "powered_hammer", "powered_file", "powered_screwdriver", "powered_saw", "powered_wire_cutter" };

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

		buildTool("power_tool")
			.addPart("engine", EngineMaterialStats.ID)
			.addPart("battery", BatteryMaterialStats.ID)
			.addPart("casing", GregicStatlessMaterialStats.CASING.getIdentifier());

		for (String tool : POWER_TOOLS)
		{
			buildTool(tool).addBreakableHead("head");

			buildTool(tool).addPart("head_active", HeadMaterialStats.ID).disallowAnimated();
		}

		buildTool("drill").addPart("casing", GregicStatlessMaterialStats.CASING.getIdentifier());
	}
}