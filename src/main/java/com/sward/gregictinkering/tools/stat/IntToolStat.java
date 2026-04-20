package com.sward.gregictinkering.tools.stat;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import lombok.Getter;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
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
 * Tool stat representing an integer value
 */
public class IntToolStat implements INumericToolStat<Integer>
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
	private final int defaultValue;

	/**
	 * Min value for this stat
	 */
	@Getter
	private final int minValue;

	/**
	 * Max value for this stat
	 */
	@Getter
	private final int maxValue;

	private final IJsonPredicate<Item> items;

	public IntToolStat(
		ToolStatId name,
		int color,
		int defaultValue,
		int minValue,
		int maxValue,
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

	public IntToolStat(
		ToolStatId name,
		int color,
		int defaultValue,
		int minValue,
		int maxValue,
		@Nullable TagKey<Item> tag
	)
	{
		this(name, color, defaultValue, minValue, maxValue, tag == null ? ItemPredicate.ANY : ItemPredicate.tag(tag));
	}

	public IntToolStat(ToolStatId name, int color, int defaultValue, int minValue, int maxValue)
	{
		this(name, color, defaultValue, minValue, maxValue, ItemPredicate.ANY);
	}

	@Override
	public boolean supports(@NotNull Item item)
	{
		return items.matches(item);
	}

	@Override
	public @NotNull Integer getDefaultValue()
	{
		return defaultValue;
	}

	@Override
	public @NotNull Integer clamp(@NotNull Integer value)
	{
		return Mth.clamp(value, getMinValue(), getMaxValue());
	}

	@Override
	public @NotNull Object makeBuilder()
	{
		return new IntegerBuilder(defaultValue);
	}

	@Override
	public void update(ModifierStatsBuilder builder, @NotNull Integer value)
	{
		builder.<IntegerBuilder>updateStat(
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
		builder.<IntegerBuilder>updateStat(this, b -> b.add += (int)value);
	}

	@Override
	public void percent(ModifierStatsBuilder builder, double factor)
	{
		builder.<IntegerBuilder>updateStat(this, b -> b.percent += (int)factor);
	}

	@Override
	public void multiply(ModifierStatsBuilder builder, double factor)
	{
		builder.<IntegerBuilder>updateStat(this, b -> b.multiply *= (int)factor);
	}

	@Override
	public void multiplyAll(ModifierStatsBuilder builder, double factor)
	{
		builder.<IntegerBuilder>updateStat(this, b -> b.multiply *= (int)factor);
		builder.multiplier(this, factor);
	}

	@Override
	public @NotNull Integer build(@NotNull ModifierStatsBuilder parent, @NotNull Object builderObj)
	{
		IntegerBuilder builder = (IntegerBuilder) builderObj;
		return (int)((builder.base + builder.add) * (1 + builder.percent) * builder.multiply);
	}

	@Nullable
	@Override
	public Integer read(@NotNull Tag tag)
	{
		if (TagUtil.isNumeric(tag))
		{
			return ((NumericTag) tag).getAsInt();
		}
		return null;
	}

	@Override
	public Tag write(@NotNull Integer value)
	{
		return IntTag.valueOf(value);
	}

	@Override
	public @NotNull Integer deserialize(@NotNull JsonElement json)
	{
		return GsonHelper.convertToInt(json, getName().toString());
	}

	@Override
	public @NotNull JsonElement serialize(@NotNull Integer value)
	{
		return new JsonPrimitive(value);
	}

	@Override
	public @NotNull Integer fromNetwork(FriendlyByteBuf buffer)
	{
		return buffer.readInt();
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, @NotNull Integer value)
	{
		buffer.writeInt(value);
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
		return "IntToolStat{" + name + '}';
	}

	/**
	 * Internal builder to store the add and multiply value
	 */
	protected static class IntegerBuilder
	{
		private int base;
		private int add = 0;
		private float percent = 0;
		private float multiply = 1;

		public IntegerBuilder(int base)
		{
			this.base = base;
		}
	}
}
