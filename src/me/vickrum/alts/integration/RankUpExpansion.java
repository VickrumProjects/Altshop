package me.vickrum.alts.integration;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.plugin.Plugin;

public abstract class RankUpExpansion extends PlaceholderExpansion {
	private final Plugin plugin;

	public RankUpExpansion(Plugin plugin) {
		this.plugin = plugin;
	}

	public String getAuthor() {
		return this.plugin.getDescription().getAuthors().toString();
	}

	public String getVersion() {
		return this.plugin.getDescription().getVersion();
	}
}
