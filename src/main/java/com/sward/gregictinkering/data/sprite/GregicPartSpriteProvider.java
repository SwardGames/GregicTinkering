package com.sward.gregictinkering.data.sprite;

import com.sward.gregictinkering.GregicTinkeringMod;
import com.sward.gregictinkering.materials.stats.BatteryMaterialStats;
import com.sward.gregictinkering.materials.stats.EngineMaterialStats;
import com.sward.gregictinkering.materials.stats.GregicStatlessMaterialStats;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

public class GregicPartSpriteProvider extends AbstractPartSpriteProvider
{
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
		buildTool("wrench")
			.addBreakableHead("head")
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