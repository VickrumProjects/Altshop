package me.vickrum.alts.integration;

import com.massivecraft.massivecore.Engine;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public abstract class PapiExpansionEngine extends Engine {
	private RankUpExpansion classesExpansion;

	public abstract RankUpExpansion getInstance();

	public void register() {
		this.classesExpansion = getInstance();
		this.classesExpansion.register();
	}

	public String setPlaceholders(Player player, String line) {
		return PlaceholderAPI.setPlaceholders(player, line);
	}
}
