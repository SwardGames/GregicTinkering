package com.sward.gregictinkering.data.material;

import com.sward.gregictinkering.materials.stats.PlungerHeadMaterialStats;
import com.sward.gregictinkering.materials.stats.SoftMalletHeadMaterialStats;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import static com.sward.gregictinkering.GregicTinkeringMaterialIds.*;
import static net.minecraft.world.item.Tiers.*;

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
		// head order is durability, mining speed, mining level, damage

		// tier 1
		// vanilla wood: 59, 2F, WOOD, 0F
		addMaterialStats(MaterialIds.wood, new SoftMalletHeadMaterialStats(60, 2F, WOOD, 0F));
		addMaterialStats(MaterialIds.bone, new SoftMalletHeadMaterialStats(100, 2.5F, STONE, 1.25F));
		addMaterialStats(MaterialIds.chorus, new SoftMalletHeadMaterialStats(180, 3F, STONE, 1F));

		// tier 2
		// vanilla iron: 250, 6F, IRON, 2F
		addMaterialStats(MaterialIds.venombone, new SoftMalletHeadMaterialStats(175, 4.5F, IRON, 2.25F));
		addMaterialStats(MaterialIds.slimewood, new SoftMalletHeadMaterialStats(375, 4F, IRON, 1F));
		// tier 2 - nether
		addMaterialStats(MaterialIds.necroticBone, new SoftMalletHeadMaterialStats(125, 4F, IRON, 2.25F));

		// tier 2 (mod integration)
		addMaterialStats(MaterialIds.treatedWood, new SoftMalletHeadMaterialStats(300, 3.5F, STONE, 1.5F));
		addMaterialStats(MaterialIds.ironwood, new SoftMalletHeadMaterialStats(512, 6.5F, IRON, 2F));


		// tier 3
		// vanilla diamond: 1561, 8F, DIAMOND, 3F
		addMaterialStats(MaterialIds.nahuatl, new SoftMalletHeadMaterialStats(350, 4.5F, DIAMOND, 3F));

		// tier 3 (mod integration)
		addMaterialStats(MaterialIds.necronium, new SoftMalletHeadMaterialStats(357, 4F, DIAMOND, 2.75F));
		addMaterialStats(MaterialIds.platedSlimewood, new SoftMalletHeadMaterialStats(595, 5F, DIAMOND, 2F));
		addMaterialStats(MaterialIds.steeleaf, new SoftMalletHeadMaterialStats(200, 8F, DIAMOND, 3F));

		// tier 4
		// vanilla netherite: 2031, 9F, NETHERITE, 4F
		addMaterialStats(MaterialIds.blazingBone, new SoftMalletHeadMaterialStats(530, 6F, IRON, 3F));
	}

	private void addPlungerMaterialStats()
	{
		addMaterialStats(RUBBER, new PlungerHeadMaterialStats(100, 2F, WOOD, 0F));
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Crafting Tool Material Stats";
	}
}
