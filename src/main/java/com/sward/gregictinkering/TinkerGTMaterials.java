package com.sward.gregictinkering;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import net.minecraft.world.level.ItemLike;
import slimeknights.mantle.registration.object.MetalItemObject;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.shared.TinkerMaterials;

public class TinkerGTMaterials
{
	public static Material SLIMESTEEL;
	public static Material AMETHYST_BRONZE;
	public static Material PIG_IRON;
	public static Material CINDERSLIME;
	public static Material QUEENS_SLIME;
	public static Material HEPATIZON;
	public static Material MANYULLYN;

	public static Material KNIGHTMETAL;

	static void register(final MaterialEvent event)
	{
		SLIMESTEEL = registerMetal(
			TinkerMaterials.slimesteel,
			"slimesteel",
			0xFF46ECE7,
			MaterialIconSet.METALLIC,
			"Fe?"
		);

		AMETHYST_BRONZE = registerMetal(
			TinkerMaterials.amethystBronze,
			"amethyst_bronze",
			0xFFD9A2D0,
			MaterialIconSet.SHINY,
			GTMaterials.Amethyst, 1, GTMaterials.Bronze, 1
		);

		PIG_IRON = registerMetal(
			TinkerMaterials.pigIron,
			"pig_iron",
			0xFFF0A8A4,
			MaterialIconSet.DULL,
			"Fe?"
		);

		CINDERSLIME = registerMetal(
			TinkerMaterials.cinderslime,
			"cinderslime",
			0xFFFF2010,
			MaterialIconSet.ROUGH,
			"Fe?"
		);

		QUEENS_SLIME = registerMetal(
			TinkerMaterials.queensSlime,
			"queens_slime",
			0xFF809912,
			MaterialIconSet.SHINY,
			"CoAu?"
		);

		HEPATIZON = registerMetal(
			TinkerMaterials.hepatizon,
			"hepatizon",
			0xFF60496B,
			MaterialIconSet.DULL,
			GTMaterials.Copper, 2, GTMaterials.Cobalt, 1, GTMaterials.NetherQuartz, 1
		);

		MANYULLYN = registerMetal(
			TinkerMaterials.manyullyn,
			"manyullyn",
			0xFF9261CC,
			MaterialIconSet.METALLIC,
			"Co3?"
		);

		KNIGHTMETAL = registerMetal(
			TinkerMaterials.knightmetal,
			"knightmetal",
			0xFFC4D6AE,
			MaterialIconSet.METALLIC,
			"Fe?"
		);
	}

	private static Material registerMetal(MetalItemObject tinkersMetal, String name, int color, MaterialIconSet iconSet, Object... components)
	{
		Material material = new Material.Builder(TConstruct.getResource(name))
			.ingot()
			.dust()
			.components(components)
			.color(color).iconSet(iconSet)
			.flags(MaterialFlags.GENERATE_PLATE, MaterialFlags.GENERATE_SMALL_GEAR, MaterialFlags.GENERATE_RING, MaterialFlags.GENERATE_BOLT_SCREW)
			.buildAndRegister();

		TagPrefix.ingot.setIgnored(material, tinkersMetal::getIngot);
		TagPrefix.nugget.setIgnored(material, tinkersMetal::getNugget);
		TagPrefix.block.setIgnored(material, (ItemLike)tinkersMetal);

		return material;
	}

	private static Material registerMetal(MetalItemObject tinkersMetal, String name, int color, MaterialIconSet iconSet, String formula)
	{
		Material material = new Material.Builder(TConstruct.getResource(name))
			.ingot()
			.dust()
			.formula(formula)
			.color(color).iconSet(iconSet)
			.flags(MaterialFlags.GENERATE_PLATE, MaterialFlags.GENERATE_SMALL_GEAR, MaterialFlags.GENERATE_RING, MaterialFlags.GENERATE_BOLT_SCREW)
			.buildAndRegister();

		TagPrefix.ingot.setIgnored(material, tinkersMetal::getIngot);
		TagPrefix.nugget.setIgnored(material, tinkersMetal::getNugget);
		TagPrefix.block.setIgnored(material, (ItemLike)tinkersMetal);

		return material;
	}
}
