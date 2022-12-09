package me.vickrum.alts.integration;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.Integration;
import org.bukkit.entity.Player;

public abstract class PapiExpansionIntegration extends Integration {
	public PapiExpansionIntegration() {
		setPluginName("PlaceholderAPI");
	}

	public abstract PapiExpansionEngine getEngineInstance();

	public Engine getEngine() {
		return getEngineInstance();
	}

	public void setIntegrationActiveInner(boolean active) {
		if (active) {
			getEngineInstance().register();
		}
	}

	public String setPlaceholders(Player player, String input) {
		return getEngineInstance().setPlaceholders(player, input);
	}
}
