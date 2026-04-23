package com.sward.gregictinkering.data.material;

import com.sward.gregictinkering.materials.stats.PlungerHeadMaterialStats;
import com.sward.gregictinkering.materials.stats.SoftMalletHeadMaterialStats;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import static com.sward.gregictinkering.GregicTinkeringMaterialIds.*;

public class CraftingToolMaterialStatsDataProvider extends AbstractMaterialStatsDataProvider
{
	public CraftingToolMaterialStatsDataProvider(
		PackOutput packOutput,
		AbstractMaterialDataProvider materials
	)
	{
		super(packOutput, materials);
	}

	@Override
	protected void addMaterialStats()
	{
		addSoftMalletMaterialStats();
		addPlungerMaterialStats();
	}

	private void addSoftMalletMaterialStats()
	{
		addMaterialStats(MaterialIds.wood, new SoftMalletHeadMaterialStats(60, 0F));
		addMaterialStats(MaterialIds.chorus, new SoftMalletHeadMaterialStats(180, 1F));
		addMaterialStats(MaterialIds.slimewood, new SoftMalletHeadMaterialStats(375, 1F));
		addMaterialStats(MaterialIds.treatedWood, new SoftMalletHeadMaterialStats(300, 1.5F));
		addMaterialStats(MaterialIds.ironwood, new SoftMalletHeadMaterialStats(512, 2F));
		addMaterialStats(MaterialIds.nahuatl, new SoftMalletHeadMaterialStats(350, 3F));
		addMaterialStats(MaterialIds.platedSlimewood, new SoftMalletHeadMaterialStats(595, 2F));
		addMaterialStats(MaterialIds.steeleaf, new SoftMalletHeadMaterialStats(200, 3F));
	}

	private void addPlungerMaterialStats()
	{
		addMaterialStats(RUBBER, new PlungerHeadMaterialStats(256, 0F));
		addMaterialStats(POLYETHYLENE, new PlungerHeadMaterialStats(256, 0F));
		addMaterialStats(SILICONE_RUBBER, new PlungerHeadMaterialStats(512, 0.5F));
		addMaterialStats(STYRENE_BUTADIENE_RUBBER, new PlungerHeadMaterialStats(512, 0.5F));
		addMaterialStats(POLYTETRAFLUOROETHYLENE, new PlungerHeadMaterialStats(512, 0.5F));
		addMaterialStats(POLYBENZIMIDAZOLE, new PlungerHeadMaterialStats(1024, 0.5F));
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Crafting Tool Material Stats";
	}
}
