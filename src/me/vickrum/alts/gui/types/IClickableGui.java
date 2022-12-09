package me.vickrum.alts.gui.types;

import me.vickrum.alts.gui.configuration.Clickable;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.stream.Collectors;

public interface IClickableGui {

	List<Clickable> getClickables();

	default List<Clickable> getClickablesFiltered(Inventory inventory) {
		return getClickables().stream()
				.filter(clickable -> clickable.getSlot() >= 0 && clickable.getSlot() < inventory.getSize())
				.collect(Collectors.toList());
	}

	void loadClicks();

}
