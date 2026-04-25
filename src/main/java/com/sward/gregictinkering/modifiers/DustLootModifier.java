package com.sward.gregictinkering.modifiers;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.OreProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.sward.gregictinkering.GregicTinkeringConfig;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class DustLootModifier extends Modifier implements ProcessLootModifierHook
{
	@Override
	protected void registerHooks(ModuleHookMap.Builder hookBuilder)
	{
		hookBuilder.addHook(this, ModifierHooks.PROCESS_LOOT);
	}

	@Override
	public void processLoot(
		@NotNull IToolStackView tool,
		@NotNull ModifierEntry modifier,
		@NotNull List<ItemStack> generatedLoot,
		@NotNull LootContext context
	)
	{
		for (int i = generatedLoot.size() - 1; i >= 0; i--)
		{
			MaterialEntry materialEntry = ChemicalHelper.getMaterialEntry(generatedLoot.get(i).getItem());

			if (materialEntry.isEmpty() || materialEntry.tagPrefix() != TagPrefix.rawOre)
			{
				continue;
			}

			OreProperty oreProperty = materialEntry.material().getProperty(PropertyKey.ORE);

			if (oreProperty == null)
			{
				continue;
			}

			RandomSource rng = context.getRandom();

			int chance = GregicTinkeringConfig.getDustLootChance() * modifier.getLevel();

			if (rng.nextInt(10000) > chance)
			{
				continue;
			}

			List<Material> byproducts = oreProperty.getOreByProducts();

			generatedLoot.add(
				ChemicalHelper.getDust(
					byproducts.get(rng.nextInt(byproducts.size())),
					GregicTinkeringConfig.getDustLootDustItemSize().size
				)
			);
		}
	}
}
