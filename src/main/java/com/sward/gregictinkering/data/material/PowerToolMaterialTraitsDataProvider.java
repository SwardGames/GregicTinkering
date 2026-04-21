package com.sward.gregictinkering.data.material;

import com.sward.gregictinkering.GregicTinkeringModifiers;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.TinkerModifiers;

import static com.sward.gregictinkering.data.TieredMaterialIds.*;

public class PowerToolMaterialTraitsDataProvider extends AbstractMaterialTraitDataProvider
{
	public PowerToolMaterialTraitsDataProvider(PackOutput packOutput, AbstractMaterialDataProvider materials)
	{
		super(packOutput, materials);
	}

	@Override
	protected void addMaterialTraits()
	{
		for (MaterialId[] tier : ENGINES)
		{
			for (int i = 0; i < tier.length; i++)
			{
				MaterialId materialId = tier[i];

				if (i < 2)
				{
					noTraits(materialId);
				}
				else
				{
					addDefaultTraits(materialId, GregicTinkeringModifiers.AIR_FED.getId());
				}
			}
		}

		for (MaterialId[] tier : TANKS)
		{
			for (MaterialId materialId : tier)
			{
				addDefaultTraits(materialId, TinkerModifiers.tankHandler.getId());
			}
		}

		for (MaterialId[] tier : BATTERIES)
		{
			for (MaterialId materialId : tier)
			{
				addDefaultTraits(materialId, GregicTinkeringModifiers.ELECTRIC_ITEM_HANDLER.getId());
			}
		}
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Power Tool Material Traits";
	}
}
