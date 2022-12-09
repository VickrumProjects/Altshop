package me.vickrum.alts.extra;

import com.massivecraft.massivecore.nms.NmsSkullMeta;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class SkullUtil {

	public static ItemStack getSkullItem(String texture, String name) {
		return getSkullItem(texture, UUID.randomUUID(), name);
	}

	public static ItemStack getSkullItem(String texture, UUID uuid, String name) {
		ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
		NmsSkullTexture.get().set(skullMeta, name, uuid, texture);
		itemStack.setItemMeta((ItemMeta) skullMeta);
		return itemStack;
	}

	public static ItemStack getSkullItem(OfflinePlayer offlinePlayer) {
		return getSkullItem(offlinePlayer.getUniqueId(), offlinePlayer.getName());
	}

	public static ItemStack getSkullItem(UUID uuid, String name) {
		ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
		NmsSkullTexture.get().set(skullMeta, name, uuid);
		itemStack.setItemMeta((ItemMeta) skullMeta);
		return itemStack;
	}

	public static String getSkullTexture(ItemStack itemStack) {
		if (!(itemStack.getItemMeta() instanceof SkullMeta))
			return null;
		SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
		String owner = NmsSkullMeta.get().getName(skullMeta);
		if (owner != null)
			return owner;
		return NmsSkullTexture.get().getTexture(skullMeta);
	}
}