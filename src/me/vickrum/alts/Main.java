package me.vickrum.alts;

import me.vickrum.alts.entity.MConf;
import me.vickrum.alts.entity.object.Price;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.massivecraft.massivecore.MassivePlugin;

import me.vickrum.alts.gui.EngineGui;
import net.milkbowl.vault.economy.Economy;

public class Main extends MassivePlugin {
	private static Main i;

	public static Economy economy;

	public Main() {
		i = this;
	}

	public static Main get() {
		return i;
	}

	public void onEnableInner() {
		activateAuto();

		if (!this.setupEconomy()) {
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}

		activate(EngineGui.class);
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(Economy.class);
		if (economyProvider != null)
			economy = (Economy) economyProvider.getProvider();
		return (economy != null);
	}

	public static Economy getEconomy() {
		return economy;
	}

	public void onDisable() {
		super.onDisable();
	}

	public boolean isVersionSynchronized() {
		return false;
	}

	public Price getPrice(String name) {
		return MConf.get().altPrices.getPrice().stream().filter(damageSet -> damageSet.getName().equalsIgnoreCase(name))
				.findFirst().orElse(null);
	}
}
