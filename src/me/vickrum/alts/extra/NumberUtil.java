package me.vickrum.alts.extra;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import org.bukkit.ChatColor;

public final class NumberUtil {
	private NumberUtil() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

	private static final DecimalFormat CHANCE_FORMAT = new DecimalFormat("#,##0.00");

	private static final DecimalFormat SINGLE_DECIMAL = new DecimalFormat("#,##0.0");

	private static final NavigableMap<Long, String> SUFFIXES = new TreeMap<>();

	static {
		SUFFIXES.put(Long.valueOf(1000L), "k");
		SUFFIXES.put(Long.valueOf(1000000L), "M");
		SUFFIXES.put(Long.valueOf(1000000000L), "B");
		SUFFIXES.put(Long.valueOf(1000000000000L), "T");
		SUFFIXES.put(Long.valueOf(1000000000000000L), "Q");
	}

	public static String format(double number) {
		return NumberFormat.getInstance(Locale.US).format(number);
	}

	public static String format(BigDecimal bigDecimal) {
		return NumberFormat.getInstance().format(bigDecimal);
	}

	public static String formatDoubleDigit(double number) {
		return CHANCE_FORMAT.format(number);
	}

	public static String formatSingleDigit(double number) {
		return SINGLE_DECIMAL.format(number);
	}

	public static String formatToSuffix(long amount) {
		if (amount < 0L)
			return "-" + formatToSuffix(-amount);
		if (amount < 1000L)
			return Long.toString(amount);
		Map.Entry<Long, String> entry = SUFFIXES.floorEntry(Long.valueOf(amount));
		Long divideBy = entry.getKey();
		String suffix = entry.getValue();
		return SINGLE_DECIMAL.format(amount / divideBy.longValue()) + suffix;
	}

	public static String getProgressBar(int current, int max, int totalBars, String symbol, ChatColor completeColor,
			ChatColor notCompleteColor) {
		return getProgressBar(current, max, totalBars, symbol, completeColor, notCompleteColor);
	}

	public static String getProgressBar(long current, long max, int totalBars, String symbol, ChatColor completeColor,
			ChatColor notCompleteColor) {
		float percent = (float) current / (float) max;
		int progressBars = (int) (totalBars * percent);
		int leftOver = totalBars - progressBars;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(completeColor.toString());
		int i;
		for (i = 0; i < progressBars; i++)
			stringBuilder.append(symbol);
		stringBuilder.append(notCompleteColor.toString());
		for (i = 0; i < leftOver; i++)
			stringBuilder.append(symbol);
		return stringBuilder.toString();
	}

	public static int getPercent(long current, long max) {
		double onePercent = max * 0.01D;
		return (int) (current / onePercent);
	}

	public static int getPercent(int current, int max) {
		return getPercent(current, max);
	}

	public static String shortenChance(double chance) {
		return CHANCE_FORMAT.format(chance);
	}

	public static double readStringAmount(String input) {
		try {
			return Long.parseLong(input);
		} catch (Exception exception) {
			String suffix = null;
			StringBuilder stringBuilder = new StringBuilder();
			boolean decimalPlace = false;
			int decimalPosition = 0;
			for (char c : input.toCharArray()) {
				if (Character.isLetter(c)) {
					suffix = Character.toString(c);
					break;
				}
				if (decimalPlace) {
					if (decimalPosition < 3) {
						stringBuilder.append(c);
						decimalPosition++;
					}
				} else {
					if (c == '.')
						decimalPlace = true;
					stringBuilder.append(c);
				}
			}
			input = stringBuilder.toString();
			String finalSuffix = suffix;
			Double value = null;
			Long multiplier = null;
			Map.Entry<Long, String> foundEntry = SUFFIXES.entrySet().stream()
					.filter(entry -> ((String) entry.getValue()).equalsIgnoreCase(finalSuffix)).findFirst()
					.orElse(null);
			if (foundEntry != null)
				multiplier = foundEntry.getKey();
			try {
				value = Double.valueOf(Double.parseDouble(input));
			} catch (Exception exception1) {
			}
			if (value == null)
				return 0.0D;
			if (multiplier != null)
				value = Double.valueOf(value.doubleValue() * multiplier.longValue());
			return value.doubleValue();
		}
	}

	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	public static boolean isLong(String input) {
		try {
			Long.parseLong(input);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	public static boolean isDouble(String input) {
		try {
			Double.parseDouble(input);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
}
