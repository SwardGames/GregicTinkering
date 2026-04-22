package com.sward.gregictinkering;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.dimension.DimensionType;

import static com.sward.gregictinkering.GregicTinkeringMod.id;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GregicTinkeringTags
{
	public static class Items
	{
		public static final TagKey<Item> POWER_TOOL = TagKey.create(Registries.ITEM, id("modifiable/power_tool"));
	}

	public static class DimensionTypes
	{
		public static final TagKey<DimensionType> NO_AIR = TagKey.create(Registries.DIMENSION_TYPE, id("no_air"));
	}
}
