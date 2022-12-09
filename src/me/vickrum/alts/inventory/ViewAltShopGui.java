package me.vickrum.alts.inventory;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.massivecraft.massivecore.util.Txt;

import me.vickrum.alts.entity.FactionAlts;
import me.vickrum.alts.entity.MConf;
import me.vickrum.alts.entity.object.Alts;
import me.vickrum.alts.event.AltInformationEvent;
import me.vickrum.alts.gui.configuration.Clickable;
import me.vickrum.alts.gui.types.IClickableGui;
import me.vickrum.alts.gui.types.RefreshGui;

public class ViewAltShopGui extends RefreshGui implements IClickableGui {

	private final FactionAlts sandy;

	private final String facid;

	public ViewAltShopGui(Player player, String uuid) {
		super(player, MConf.get().altshopAccountsGui);

		sandy = FactionAlts.get(uuid);
		facid = uuid;
	}

	public long getRefreshTicks() {
		return MConf.get().refreshGUITime;
	}
	
	@Override
	public List<Clickable> getClickables() {
		return MConf.get().altshopAccountsGuiButtons;
	}

	public void refresh() {
		getClickablesFiltered(this.inventory).forEach(actionButton -> {
			int slot = actionButton.getSlot();

			if (actionButton.getAction() != null) {
				if (!actionButton.getAction().contains("ALT")) {
					ItemStack itemStack = actionButton.getDisplayItem().toItemStack();
					this.inventory.setItem(slot, itemStack);

					if (actionButton.getAction().equalsIgnoreCase("EXIT")) {
						setClickable(slot, event -> {
							player.closeInventory();
							return;
						});
					}

					if (actionButton.getAction().equalsIgnoreCase("BACK")) {
						setClickable(slot, event -> {
							player.closeInventory();
							new AltShopGui(player, facid).open();
							return;
						});
					}
				} else if (actionButton.getAction().contains("ALT")) {
					String[] number = actionButton.getAction().split("_");
					if (sandy.hasAltsSize(Integer.parseInt(number[1]))) {
						if (actionButton.getAction().endsWith("_INFORMATION")) {
							Alts altbot = sandy.getAlts().get("ALT:" + number[1]);
							if (altbot.isDespawned()) {
								ItemStack itemStack = actionButton.getDisplayItem().toItemStack("{status}",
										Txt.parse(MConf.get().altDeactive), "{x}", "0", "{y}", "0", "{z}", "0",
										"{world}", "world");
								this.inventory.setItem(slot, itemStack);
							} else {
								ItemStack itemStack = actionButton.getDisplayItem().toItemStack("{status}",
										Txt.parse(MConf.get().altActive), "{x}",
										String.valueOf(altbot.getLocation().getBlockX()), "{y}",
										String.valueOf(altbot.getLocation().getBlockY()), "{z}",
										String.valueOf(altbot.getLocation().getBlockZ()), "{world}",
										altbot.getLocation().getWorld().getName());
								this.inventory.setItem(slot, itemStack);
							}
							
							setClickable(slot, event -> {
								AltInformationEvent confClicked = new AltInformationEvent(this.player, actionButton.getAction(), number[1]);
								Bukkit.getPluginManager().callEvent(confClicked);
								return;
							});
						}
					}
				}
			}
		});
	}
	
	@Override
	public void loadClicks() {
		getClickablesFiltered(this.inventory).forEach(actionButton -> {
			int slot = actionButton.getSlot();

			if (actionButton.getAction() != null) {
				if (!actionButton.getAction().contains("ALT")) {
					ItemStack itemStack = actionButton.getDisplayItem().toItemStack();
					this.inventory.setItem(slot, itemStack);

					if (actionButton.getAction().equalsIgnoreCase("EXIT")) {
						setClickable(slot, event -> {
							player.closeInventory();
							return;
						});
					}

					if (actionButton.getAction().equalsIgnoreCase("BACK")) {
						setClickable(slot, event -> {
							player.closeInventory();
							new AltShopGui(player, facid).open();
							return;
						});
					}
				} else if (actionButton.getAction().contains("ALT")) {
					String[] number = actionButton.getAction().split("_");
					if (sandy.hasAltsSize(Integer.parseInt(number[1]))) {
						if (actionButton.getAction().endsWith("_INFORMATION")) {
							Alts altbot = sandy.getAlts().get("ALT:" + number[1]);
							if (altbot.isDespawned()) {
								ItemStack itemStack = actionButton.getDisplayItem().toItemStack("{status}",
										Txt.parse(MConf.get().altDeactive), "{x}", "0", "{y}", "0", "{z}", "0",
										"{world}", "world");
								this.inventory.setItem(slot, itemStack);
							} else {
								ItemStack itemStack = actionButton.getDisplayItem().toItemStack("{status}",
										Txt.parse(MConf.get().altActive), "{x}",
										String.valueOf(altbot.getLocation().getBlockX()), "{y}",
										String.valueOf(altbot.getLocation().getBlockY()), "{z}",
										String.valueOf(altbot.getLocation().getBlockZ()), "{world}",
										altbot.getLocation().getWorld().getName());
								this.inventory.setItem(slot, itemStack);
							}
							
							setClickable(slot, event -> {
								AltInformationEvent confClicked = new AltInformationEvent(this.player, actionButton.getAction(), number[1]);
								Bukkit.getPluginManager().callEvent(confClicked);
								return;
							});
						}
					}
				}
			}
		});
	}
}
