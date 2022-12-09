package me.vickrum.alts.inventory;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import me.vickrum.alts.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.massivecraft.massivecore.util.Txt;

import me.vickrum.alts.entity.FactionAlts;
import me.vickrum.alts.entity.MConf;
import me.vickrum.alts.entity.object.Alts;
import me.vickrum.alts.event.CategorySelectEvent;
import me.vickrum.alts.extra.ItemBuilder;
import me.vickrum.alts.gui.BaseGui;
import me.vickrum.alts.gui.configuration.Clickable;
import me.vickrum.alts.gui.types.IClickableGui;

public class AltShopGui extends BaseGui implements IClickableGui {

	private final FactionAlts alts;

	public AltShopGui(Player player, String uuid) {
		super(player, MConf.get().altshopMainGui);

		alts = FactionAlts.get(uuid);
	}

	@Override
	public List<Clickable> getClickables() {
		return MConf.get().altshopMainGuiButtons;
	}

	@Override
	public void loadClicks() {
		getClickablesFiltered(this.inventory).forEach(actionButton -> {
			int slot = actionButton.getSlot();

			for (int i = 1; i < alts.getAltsSize() + 1; i++) {
				if (alts.getAlts().get("ALT:" + i) == null) {
					alts.addAlts("ALT:" + i, new Alts());
				}
			}

			if (!actionButton.getDisplayItem().getBaseItem().getType().equals(Material.SKULL_ITEM)) {
				ItemStack itemStack = actionButton.getDisplayItem().toItemStack();

				this.inventory.setItem(slot, itemStack);

				try {
					if (actionButton.getAction() != null) {
						setClickable(slot, event -> {
							CategorySelectEvent inventoryClicked = new CategorySelectEvent(this.player,
									actionButton.getAction());
							Bukkit.getPluginManager().callEvent(inventoryClicked);
						});
					}
				} catch (Exception ignore) {
				}
			} else {
				try {
					String price = MConf.get().maxed;

					if (alts.getAltsSize() >= MConf.get().maxAltSize) {
						price = MConf.get().maxed;
					} else if (alts.getAltsSize() < MConf.get().maxAltSize) {
						int alt = alts.getAltsSize() + 1;
						Double PriceforAlt = Main.get().getPrice("ALT_" + alt).getAmount();
						price = "&2$&a" + NumberFormat.getInstance().format(PriceforAlt);
					}


					List<String> lore1 = new ArrayList<>();
					for (String s : actionButton.getDisplayItem().getMeta().getLore())
						lore1.add(s.replace("{price}", price));


					ItemStack itemLol = new ItemBuilder(Material.SKULL_ITEM).durability(3)
							.name(Txt.parse(
									actionButton.getDisplayItem().getMeta().getDisplayName().replace("{altsTotal}",
											NumberFormat.getInstance().format(alts.getAltsSize()))))
							.setLore(Txt.parse(lore1))
							.texture(actionButton.getTexture());

					this.inventory.setItem(slot, itemLol);

					try {
						if (actionButton.getAction() != null) {
							setClickable(slot, event -> {
								CategorySelectEvent inventoryClicked = new CategorySelectEvent(this.player,
										actionButton.getAction());
								Bukkit.getPluginManager().callEvent(inventoryClicked);
							});
						}
					} catch (Exception ignore) {
					}
				} catch (Exception ignore) {
				}
			}
		});
	}
}
