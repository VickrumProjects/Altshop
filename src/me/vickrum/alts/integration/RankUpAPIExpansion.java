package me.vickrum.alts.integration;

import java.text.NumberFormat;
import me.vickrum.alts.*;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RankUpAPIExpansion extends RankUpExpansion {
	public RankUpAPIExpansion() {
		super(Main.get());
	}

	public String getIdentifier() {
		return "alts";
	}

	public int alts = 0;

	public String onPlaceholderRequest(Player player, String identifier) {
		if (player == null)
			return "";
		if (identifier.equals("total")) {
			alts = 0;
			for (NPC npc : CitizensAPI.getNPCRegistry().sorted()) {
				if (npc != null && npc.getFullName().toLowerCase().contains("alt")) {
					alts++;
				}
			}

			return NumberFormat.getInstance().format(alts);
		}

		if (identifier.equals("online")) {
			int total = alts + Bukkit.getOnlinePlayers().size();

			return NumberFormat.getInstance().format(total);
		}
		return "";
	}
}
