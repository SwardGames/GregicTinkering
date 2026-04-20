package com.sward.gregictinkering.tools.stat;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import lombok.Getter;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.predicate.IJsonPredicate;
import slimeknights.mantle.data.predicate.item.ItemPredicate;
import slimeknights.tconstruct.library.tools.stat.INumericToolStat;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.utils.TagUtil;

import javax.annotation.Nullable;

/**
 * Tool stat representing an Long value
 */
public class LongToolStat implements INumericToolStat<Long>
{
	/**
	 * Name of this tool stat
	 */
	@Getter
	private final ToolStatId name;

	/**
	 * Color for this stat type
	 */
	@Getter
	private final TextColor color;

	/**
	 * Gets the default value for this stat
	 */
	private final long defaultValue;

	/**
	 * Min value for this stat
	 */
	@Getter
	private final long minValue;

	/**
	 * Max value for this stat
	 */
	@Getter
	private final long maxValue;

	private final IJsonPredicate<Item> items;

	public LongToolStat(
		ToolStatId name,
		int color,
		long defaultValue,
		long minValue,
		long maxValue,
		IJsonPredicate<Item> items
	)
	{
		this.name = name;
		this.color = TextColor.fromRgb(color);
		this.defaultValue = defaultValue;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.items = items;
	}

	public LongToolStat(
		ToolStatId name,
		int color,
		long defaultValue,
		long minValue,
		long maxValue,
		@Nullable TagKey<Item> tag
	)
	{
		this(name, color, defaultValue, minValue, maxValue, tag == null ? ItemPredicate.ANY : ItemPredicate.tag(tag));
	}

	public LongToolStat(ToolStatId name, int color, long defaultValue, long minValue, long maxValue)
	{
		this(name, color, defaultValue, minValue, maxValue, ItemPredicate.ANY);
	}

	@Override
	public boolean supports(@NotNull Item item)
	{
		return items.matches(item);
	}

	@Override
	public @NotNull Long getDefaultValue()
	{
		return defaultValue;
	}

	@Override
	public @NotNull Long clamp(@NotNull Long value)
	{
		return Math.min(Math.max(value, getMinValue()), getMaxValue());
	}

	@Override
	public @NotNull Object makeBuilder()
	{
		return new LongToolStat.LongBuilder(defaultValue);
	}

	@Override
	public void update(ModifierStatsBuilder builder, @NotNull Long value)
	{
		builder.<LongBuilder>updateStat(
			this, b ->
			{
				b.add += value;
				b.base = 0;
			}
		);
	}

	@Override
	public void add(ModifierStatsBuilder builder, double value)
	{
		builder.<LongBuilder>updateStat(this, b -> b.add += (long)value);
	}

	@Override
	public void percent(ModifierStatsBuilder builder, double factor)
	{
		builder.<LongBuilder>updateStat(this, b -> b.percent += factor);
	}

	@Override
	public void multiply(ModifierStatsBuilder builder, double factor)
	{
		builder.<LongBuilder>updateStat(this, b -> b.multiply *= factor);
	}

	@Override
	public void multiplyAll(ModifierStatsBuilder builder, double factor)
	{
		builder.<LongBuilder>updateStat(this, b -> b.multiply *= factor);
		builder.multiplier(this, factor);
	}

	@Override
	public @NotNull Long build(@NotNull ModifierStatsBuilder parent, @NotNull Object builderObj)
	{
		LongBuilder builder = (LongToolStat.LongBuilder) builderObj;
		return (long)((builder.base + builder.add) * (1 + builder.percent) * builder.multiply);
	}

	@Nullable
	@Override
	public Long read(@NotNull Tag tag)
	{
		if (TagUtil.isNumeric(tag))
		{
			return ((NumericTag) tag).getAsLong();
		}
		return null;
	}

	@Override
	public Tag write(@NotNull Long value)
	{
		return LongTag.valueOf(value);
	}

	@Override
	public @NotNull Long deserialize(@NotNull JsonElement json)
	{
		return GsonHelper.convertToLong(json, getName().toString());
	}

	@Override
	public @NotNull JsonElement serialize(@NotNull Long value)
	{
		return new JsonPrimitive(value);
	}

	@Override
	public @NotNull Long fromNetwork(FriendlyByteBuf buffer)
	{
		return buffer.readLong();
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, @NotNull Long value)
	{
		buffer.writeLong(value);
	}

	/**
	 * Generic friendly way to format the value
	 */
	@Override
	public @NotNull Component formatValue(float value)
	{
		return IToolStat.formatNumber(getTranslationKey(), getColor(), value);
	}

	@Override
	public String toString()
	{
		return "LongToolStat{" + name + '}';
	}

	/**
	 * Internal builder to store the add and multiply value
	 */
	protected static class LongBuilder
	{
		private long base;
		private long add = 0;
		private double percent = 0;
		private double multiply = 1;

		public LongBuilder(long base)
		{
			this.base = base;
		}
	}
}
