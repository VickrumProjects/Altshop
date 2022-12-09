package me.vickrum.alts.extra;

import com.massivecraft.massivecore.util.InventoryUtil;
import com.massivecraft.massivecore.util.Txt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class TxtUtil {
	private TxtUtil() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

	public static String parse(String string) {
		return Txt.parse(string);
	}

	public static String parse(String string, Object... args) {
		return Txt.parse(string, args);
	}

	public static Collection<String> parse(Collection<String> list) {
		return Txt.parse(list);
	}

	public static String parseAndReplace(String string, String... replacements) {
		if (replacements.length > 0) {
			Iterator<String> iterator = Arrays.<String>asList(replacements).iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				if (iterator.hasNext()) {
					String value = iterator.next();
					if (key == null || value == null)
						continue;
					string = string.replace(key, value);
				}
			}
		}
		return parse(string);
	}

	public static List<String> parseAndReplace(List<String> list, String... replacements) {
		return (List<String>) list.stream().map(line -> parseAndReplace(line, replacements))
				.filter(line -> !line.isEmpty()).collect(Collectors.toList());
	}

	public static String getMaterialName(Material material) {
		return Txt.getNicedEnum(material);
	}

	public static String getItemName(ItemStack itemStack) {
		ChatColor color;
		if (InventoryUtil.isNothing(itemStack))
			return Txt.parse("<silver><em>Nothing");
		try {
			color = (itemStack.getEnchantments().size() > 0) ? ChatColor.AQUA : ChatColor.WHITE;
		} catch (Throwable ex) {
			color = ChatColor.WHITE;
		}
		if (color == ChatColor.WHITE && itemStack.getType() == Material.GOLDEN_APPLE) {
			short durability = itemStack.getDurability();
			switch (durability) {
			case 1:
				color = ChatColor.LIGHT_PURPLE;
				break;
			default:
				color = ChatColor.AQUA;
				break;
			}
		}
		if (itemStack.hasItemMeta()) {
			ItemMeta itemMeta = itemStack.getItemMeta();
			if (itemMeta.hasDisplayName())
				return color.toString() + ChatColor.ITALIC.toString() + itemMeta.getDisplayName();
		}
		String presetDisplay = null;
		if (itemStack.getType() == Material.COAL) {
			short durability = itemStack.getDurability();
			switch (durability) {
			case 1:
				presetDisplay = "Charcoal";
				break;
			default:
				presetDisplay = "Coal";
				break;
			}
		} else if (itemStack.getType() == Material.INK_SACK) {
			short durability = itemStack.getDurability();
			switch (durability) {
			case 1:
				presetDisplay = "Rose Red";
				break;
			case 2:
				presetDisplay = "Cactus Green";
				break;
			case 3:
				presetDisplay = "Cocoa Beans";
				break;
			case 4:
				presetDisplay = "Lapis Lazuli";
				break;
			case 5:
				presetDisplay = "Purple Dye";
				break;
			case 6:
				presetDisplay = "Cyan Dye";
				break;
			case 7:
				presetDisplay = "Light Gray Dye";
				break;
			case 8:
				presetDisplay = "Gray Dye";
				break;
			case 9:
				presetDisplay = "Pink Dye";
				break;
			case 10:
				presetDisplay = "Lime Dye";
				break;
			case 11:
				presetDisplay = "Dandelion Yellow";
				break;
			case 12:
				presetDisplay = "Light Blue Dye";
				break;
			case 13:
				presetDisplay = "Magenta Dye";
				break;
			case 14:
				presetDisplay = "Orange Dye";
				break;
			case 15:
				presetDisplay = "Bone Meal";
				break;
			default:
				presetDisplay = "Ink Sack";
				break;
			}
		} else if (itemStack.getType() == Material.COOKED_FISH) {
			short durability = itemStack.getDurability();
			switch (durability) {
			case 1:
				presetDisplay = "Cooked Salmon";
				break;
			default:
				presetDisplay = "Cooked Fish";
				break;
			}
		} else if (itemStack.getType() == Material.RAW_FISH) {
			short durability = itemStack.getDurability();
			switch (durability) {
			case 1:
				presetDisplay = "Raw Salmon";
				break;
			case 2:
				presetDisplay = "Clownfish";
				break;
			case 3:
				presetDisplay = "Pufferfish";
				break;
			default:
				presetDisplay = "Raw Fish";
				break;
			}
		} else if (itemStack.getType() == Material.RED_ROSE) {
			short durability = itemStack.getDurability();
			switch (durability) {
			case 0:
				presetDisplay = "Poppy";
				break;
			case 1:
				presetDisplay = "Blue Orchid";
				break;
			case 2:
				presetDisplay = "Allium";
				break;
			case 3:
				presetDisplay = "Azure Bluet";
				break;
			case 4:
				presetDisplay = "Red Tulip";
				break;
			case 5:
				presetDisplay = "Orange Tulip";
				break;
			case 6:
				presetDisplay = "White Tulip";
				break;
			case 7:
				presetDisplay = "Pink Tulip";
				break;
			case 8:
				presetDisplay = "Oxeye Daisy";
				break;
			}
		} else if (itemStack.getType() == Material.CARROT_ITEM) {
			presetDisplay = "Carrot";
		} else if (itemStack.getType() == Material.POTATO_ITEM) {
			presetDisplay = "Potato";
		}
		return color + ((presetDisplay != null) ? presetDisplay : Txt.getMaterialName(itemStack.getType()));
	}

	public static String shuffle(String input) {
		List<Character> characters = new ArrayList<>();
		for (char c : input.toCharArray())
			characters.add(Character.valueOf(c));
		StringBuilder output = new StringBuilder(input.length());
		while (characters.size() != 0) {
			int randPicker = (int) (Math.random() * characters.size());
			output.append(characters.remove(randPicker));
		}
		return output.toString();
	}

	public static String getPrettyLocation(Location location) {
		return getPrettyLocation(location, true);
	}

	public static String getPrettyLocation(Location location, boolean includeWorld) {
		return (includeWorld ? (location.getWorld().getName() + ", ") : "") + location.getBlockX() + ", "
				+ location.getBlockY() + ", " + location.getBlockZ();
	}
}
