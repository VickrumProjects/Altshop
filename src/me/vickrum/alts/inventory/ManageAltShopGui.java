package me.vickrum.alts.inventory;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import com.massivecraft.massivecore.util.Txt;

import me.vickrum.alts.entity.FactionAlts;
import me.vickrum.alts.entity.MConf;
import me.vickrum.alts.entity.object.Alts;
import me.vickrum.alts.event.AltDespawnEvent;
import me.vickrum.alts.event.AltInformationEvent;
import me.vickrum.alts.event.AltSpawnEvent;
import me.vickrum.alts.gui.configuration.Clickable;
import me.vickrum.alts.gui.types.IClickableGui;
import me.vickrum.alts.gui.types.RefreshGui;

public class ManageAltShopGui extends RefreshGui implements IClickableGui {

	private final FactionAlts alts;

	private final String facid;

	public ManageAltShopGui(Player player, String uuid) {
		super(player, MConf.get().altshopManageAccountsGui);

		alts = FactionAlts.get(uuid);
		facid = uuid;
	}

	public long getRefreshTicks() {
		return MConf.get().refreshGUITime;
	}

	@Override
	public List<Clickable> getClickables() {
		return MConf.get().altshopManageAccountsGuiButtons;
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
					if (alts.hasAltsSize(Integer.parseInt(number[1]))) {
						if (actionButton.getAction().endsWith("_SETTINGS")) {
							Alts altbot = alts.getAlts().get("ALT:" + number[1]);
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
								if (event.getClick().equals(ClickType.LEFT)) {
									AltSpawnEvent confClicked = new AltSpawnEvent(this.player, actionButton.getAction(),
											number[1], slot);
									Bukkit.getPluginManager().callEvent(confClicked);
									return;
								}

								if (event.getClick().equals(ClickType.RIGHT)) {
									AltDespawnEvent confClicked = new AltDespawnEvent(this.player,
											actionButton.getAction(), number[1]);
									Bukkit.getPluginManager().callEvent(confClicked);
									return;
								}

								if (event.getClick().equals(ClickType.SHIFT_LEFT)
										|| event.getClick().equals(ClickType.SHIFT_RIGHT)) {
									AltInformationEvent confClicked = new AltInformationEvent(this.player,
											actionButton.getAction(), number[1]);
									Bukkit.getPluginManager().callEvent(confClicked);
									return;
								}
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
					if (alts.hasAltsSize(Integer.parseInt(number[1]))) {
						if (actionButton.getAction().endsWith("_SETTINGS")) {
							Alts altbot = alts.getAlts().get("ALT:" + number[1]);
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
								if (event.getClick().equals(ClickType.LEFT)) {
									AltSpawnEvent confClicked = new AltSpawnEvent(this.player, actionButton.getAction(),
											number[1], slot);
									Bukkit.getPluginManager().callEvent(confClicked);
									return;
								}

								if (event.getClick().equals(ClickType.RIGHT)) {
									AltDespawnEvent confClicked = new AltDespawnEvent(this.player,
											actionButton.getAction(), number[1]);
									Bukkit.getPluginManager().callEvent(confClicked);
									return;
								}

								if (event.getClick().equals(ClickType.SHIFT_LEFT)
										|| event.getClick().equals(ClickType.SHIFT_RIGHT)) {
									AltInformationEvent confClicked = new AltInformationEvent(this.player,
											actionButton.getAction(), number[1]);
									Bukkit.getPluginManager().callEvent(confClicked);
									return;
								}
							});
						}
					}
				}
			}
		});
	}
}
