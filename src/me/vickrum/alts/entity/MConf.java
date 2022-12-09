package me.vickrum.alts.entity;

import java.util.List;

import me.vickrum.alts.entity.object.Price;
import me.vickrum.alts.entity.object.PriceList;
import org.bukkit.Material;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;

import me.vickrum.alts.extra.ItemStackWrapper;
import me.vickrum.alts.extra.MetaWrapper;
import me.vickrum.alts.gui.configuration.Clickable;
import me.vickrum.alts.gui.configuration.GuiDesign;

@EditorName("config")
public class MConf extends Entity<MConf> {

	protected static transient MConf i;

	///////////////////////////////////////////////////
	// Aliases
	///////////////////////////////////////////////////
	public List<String> altshopAlias = MUtil.list("alts", "altshop");
	public List<String> altsSetCommandAliases = MUtil.list("set");
	public List<String> altsDespawnCommandAliases = MUtil.list("despawn", "forcedespawn");

	///////////////////////////////////////////////////
	// Inventory Creating
	///////////////////////////////////////////////////
	public GuiDesign altshopMainGui = new GuiDesign("&6Alt Shop",
			MUtil.map(Character.valueOf('#'), new ItemStackWrapper(Material.STAINED_GLASS_PANE, 1, (short) 1, "&r"),
					Character.valueOf('>'), new ItemStackWrapper(Material.STAINED_GLASS_PANE, 1, (short) 7, "&r"),
					Character.valueOf('!'),
					new ItemStackWrapper(Material.BOOK, 1, (short) 0, "&6&l&nAlt Accounts",
							MUtil.list("&7", "&eWhat are Alt Accounts?", "&6❙ &fNPC's you can use to AFK areas.",
									"&6❙ &fAdd power to your faction", "&6❙ &fManage your alts here.", "&7",
									"&7Purchase alts by clicking below"))),
			MUtil.list("####!####", "#>>>>>>>#", "#########"));

	public List<Clickable> altshopMainGuiButtons = MUtil.list(new Clickable(10,
			new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6&lAlt Accounts (&e{altsTotal}&6)",
					MUtil.list("&7", "&6❙ &fClick to view &6Alt Accounts&f!")),
			"VIEW_ALTS",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTc5ZjgyZjEyMjZmNDc4ZGQzNDdlYjhhNDc5MDkzMjRlZWQyY2UyMjFkOTE5OGNjYWQ2NmFjMThlMzIwOGMwNiJ9fX0="),
			new Clickable(13,
					new ItemStackWrapper(Material.ANVIL, 1, (short) 0, "&c&lManage Alts",
							MUtil.list("&7", "&c❙ &fClick to &cManage Alts&f!")),
					"MANAGE_ALTS", null),
			new Clickable(16,
					geHeadItem("&2&lPurchase Alt",
							MUtil.list("&7", "&2❙ &fPrice: {price}", "&7", "&aClick to purchase an alt!")),
					"PURCHASE_ALTS",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjA5Mjk5YTExN2JlZTg4ZDMyNjJmNmFiOTgyMTFmYmEzNDRlY2FlMzliNDdlYzg0ODEyOTcwNmRlZGM4MWU0ZiJ9fX0="));

	///////////////////////////////////////////////////
	// Inventory Creating (Accounts)
	///////////////////////////////////////////////////
	public GuiDesign altshopAccountsGui = new GuiDesign("&6Alt Accounts",
			MUtil.map(Character.valueOf('#'), new ItemStackWrapper(Material.STAINED_GLASS_PANE, 1, (short) 1, "&r"),
					Character.valueOf('-'), new ItemStackWrapper(Material.AIR)),
			MUtil.list("#########", "#-------#", "#-------#", "#-------#", "#########"));

	public List<Clickable> altshopAccountsGuiButtons = MUtil.list(
			new Clickable(10,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Alt Information #1",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&7&o( ( Click to teleport to your current location ) )")),
					"ALT_1_INFORMATION", null),
			new Clickable(11,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Alt Information #2",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&7&o( ( Click to teleport to your current location ) )")),
					"ALT_2_INFORMATION", null),
			new Clickable(12,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Alt Information #3",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&7&o( ( Click to teleport to your current location ) )")),
					"ALT_3_INFORMATION", null),
			new Clickable(13,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Alt Information #4",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&7&o( ( Click to teleport to your current location ) )")),
					"ALT_4_INFORMATION", null),
			new Clickable(14,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Alt Information #5",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&7&o( ( Click to teleport to your current location ) )")),
					"ALT_5_INFORMATION", null),
			new Clickable(15,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Alt Information #6",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&7&o( ( Click to teleport to your current location ) )")),
					"ALT_6_INFORMATION", null),
			new Clickable(16,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Alt Information #7",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&7&o( ( Click to teleport to your current location ) )")),
					"ALT_7_INFORMATION", null),
			new Clickable(19,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Alt Information #8",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&7&o( ( Click to teleport to your current location ) )")),
					"ALT_8_INFORMATION", null),
			new Clickable(20,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Alt Information #9",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&7&o( ( Click to teleport to your current location ) )")),
					"ALT_9_INFORMATION", null),
			new Clickable(21,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Alt Information #10",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&7&o( ( Click to teleport to your current location ) )")),
					"ALT_10_INFORMATION", null),

			new Clickable(40,
					new ItemStackWrapper(Material.BARRIER, 1, (short) 0, "&cExit", MUtil.list("&7Close the Alts Menu")),
					"EXIT", null),
			new Clickable(39, new ItemStackWrapper(Material.ARROW, 1, (short) 0, "&6Back",
					MUtil.list("&7Go back to the Alts Main Page")), "BACK", null));

	///////////////////////////////////////////////////
	// Inventory Creating (Manage)
	///////////////////////////////////////////////////
	public GuiDesign altshopManageAccountsGui = new GuiDesign("&6Manage Accounts",
			MUtil.map(Character.valueOf('#'), new ItemStackWrapper(Material.STAINED_GLASS_PANE, 1, (short) 1, "&r"),
					Character.valueOf('-'), new ItemStackWrapper(Material.AIR)),
			MUtil.list("#########", "#-------#", "#-------#", "#-------#", "#########"));

	public List<Clickable> altshopManageAccountsGuiButtons = MUtil.list(
			new Clickable(10,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Manage Alt #1",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&a❙ Left-Click to spawn alt", "&c❙ Right Click to despawn alt",
									"&6❙ Shift Click to teleport alt", "&7")),
					"ALT_1_SETTINGS", null),
			new Clickable(11,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Manage Alt #2",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&a❙ Left-Click to spawn alt", "&c❙ Right Click to despawn alt",
									"&6❙ Shift Click to teleport alt", "&7")),
					"ALT_2_SETTINGS", null),
			new Clickable(12,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Manage Alt #3",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&a❙ Left-Click to spawn alt", "&c❙ Right Click to despawn alt",
									"&6❙ Shift Click to teleport alt", "&7")),
					"ALT_3_SETTINGS", null),
			new Clickable(13,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Manage Alt #4",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&a❙ Left-Click to spawn alt", "&c❙ Right Click to despawn alt",
									"&6❙ Shift Click to teleport alt", "&7")),
					"ALT_4_SETTINGS", null),
			new Clickable(14,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Manage Alt #5",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&a❙ Left-Click to spawn alt", "&c❙ Right Click to despawn alt",
									"&6❙ Shift Click to teleport alt", "&7")),
					"ALT_5_SETTINGS", null),
			new Clickable(15,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Manage Alt #6",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&a❙ Left-Click to spawn alt", "&c❙ Right Click to despawn alt",
									"&6❙ Shift Click to teleport alt", "&7")),
					"ALT_6_SETTINGS", null),
			new Clickable(16,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Manage Alt #7",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&a❙ Left-Click to spawn alt", "&c❙ Right Click to despawn alt",
									"&6❙ Shift Click to teleport alt", "&7")),
					"ALT_7_SETTINGS", null),
			new Clickable(19,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Manage Alt #8",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&a❙ Left-Click to spawn alt", "&c❙ Right Click to despawn alt",
									"&6❙ Shift Click to teleport alt", "&7")),
					"ALT_8_SETTINGS", null),
			new Clickable(20,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Manage Alt #9",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&a❙ Left-Click to spawn alt", "&c❙ Right Click to despawn alt",
									"&6❙ Shift Click to teleport alt", "&7")),
					"ALT_9_SETTINGS", null),
			new Clickable(21,
					new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3, "&6Manage Alt #10",
							MUtil.list("&6Status: {status}", "&6Location: &f{x}, {y}, {z} {world}", "&7",
									"&a❙ Left-Click to spawn alt", "&c❙ Right Click to despawn alt",
									"&6❙ Shift Click to teleport alt", "&7")),
					"ALT_10_SETTINGS", null),

			new Clickable(40,
					new ItemStackWrapper(Material.BARRIER, 1, (short) 0, "&cExit", MUtil.list("&7Close the Alts Menu")),
					"EXIT", null),
			new Clickable(39, new ItemStackWrapper(Material.ARROW, 1, (short) 0, "&6Back",
					MUtil.list("&7Go back to the Alts Main Page")), "BACK", null));

	private ItemStackWrapper geHeadItem(String Displayname, List<String> lore) {
		ItemStackWrapper itemStackWrapper = new ItemStackWrapper(Material.SKULL_ITEM, 1, (short) 3);
		MetaWrapper metaWrapper = new MetaWrapper();

		metaWrapper.setDisplayName(Txt.parse(Displayname));

		metaWrapper.setLore(lore);

		itemStackWrapper.setMeta(metaWrapper);

		return itemStackWrapper;
	}

	public PriceList altPrices = new PriceList(MUtil.list(new Price("ALT_1", 10000.0),
			new Price("ALT_2", 20000.0),
			new Price("ALT_3", 30000.0),
			new Price("ALT_4", 40000.0),
			new Price("ALT_5", 50000.0),
			new Price("ALT_6", 60000.0),
			new Price("ALT_7", 70000.0),
			new Price("ALT_8", 80000.0),
			new Price("ALT_9", 90000.0),
			new Price("ALT_10", 100000.0),
			new Price("ALT_11", 110000.0),
			new Price("ALT_12", 120000.0),
			new Price("ALT_13", 130000.0),
			new Price("ALT_14", 140000.0),
			new Price("ALT_15", 150000.0),
			new Price("ALT_16", 160000.0),
			new Price("ALT_17", 170000.0),
			new Price("ALT_18", 180000.0),
			new Price("ALT_19", 190000.0),
			new Price("ALT_20", 200000.0),
			new Price("ALT_21", 210000.0),
			new Price("ALT_22", 220000.0),
			new Price("ALT_23", 230000.0),
			new Price("ALT_24", 240000.0),
			new Price("ALT_25", 250000.0),
			new Price("ALT_26", 260000.0),
			new Price("ALT_27", 270000.0),
			new Price("ALT_28", 280000.0)));

	public String maxed = "&7You are maxed out!";

	public String altName = "&e{player}'s Alt #{number}";
	public int maxAltSize = 5;

	public String altActive = "&aActive";
	public String altDeactive = "&cDeactive";

	public String altSkinName = "DrePvP";
	public boolean preventFactionSystem = true;
	public boolean factionNameForceUpperCase = false;

	public long refreshGUITime = 5l;

	public String msgAlreadyHaveMaxAltSize = "&6&lPalm &8❙ &cYou have already gotten the max alt size! ({max})";
	public String msgNoAltsToManage = "&6&lPalm &8❙ &cYou have no alts to manage!";
	public String msgCantSpawnAlt = "&6&lPalm &8❙ &cYou cannot spawn alt #{number} since you do not own it!";
	public String msgCantDeSpawnAlt = "&6&lPalm &8❙ &cYou cannot despawn alt #{number} since you do not own it!";
	public String msgPurchaseAltBeforeYouUse = "&6&lPalm &8❙ &cYou have to purchase alt #{number} before being able to do this.";
	public String msgBotAlreadySpawned = "&6&lPalm &8❙ &cThis alt is already spawned.";
	public String msgBotDespawnedMsg = "&6&lPalm &8❙ &cDespawned alt #{number}.";
	public String msgBotDontExistMsg = "&6&lPalm &8❙ &cYou can't despawn something that doesn't exist.";
	public boolean allowInWilderness = false;
	public String msgCantSpawnInWilderness = "&6&lPalm &8❙ &7You can't spawn an alt in &2Wilderness&7!";
	public String msgCantSpawnInWarzone = "&6&lPalm &8❙ &7You can't spawn an alt in &4Warzone&7!";
	public String spawnedAltSpawnedMsg = "&6&lPalm &8❙ &aYou have spawned in alt #{number} to your location!";
	public String msgYouNeedAFaction = "&6&lPalm &8❙ &cYou need a faction to be able to use alts!";
	public String msgSuccessfullyPurchasedAlt = "&6&lPalm &8❙ &aYou have successfully purchased alt #{number}!";
	public String msgNotEnoughMoney = "&6&lPalm &8❙ &cYou do not have enough cash to buy this item. You need ${amount}.";
	public String msgBotTelportedToLocation = "&6&lPalm &8❙ &aTeleported alt #{number} to your location!";
	public String msgBotDontTpExistMsg = "&6&lPalm &8❙ &cYou can't teleport something that doesn't exist.";
	public String msgStaffPlayerAtXAlready = "&6&lPalm &8❙ &c{player} already has {amount} of alts!";
	public String msgPlayerAltsChange = "&6&lPalm &8❙ &aA Staff member has set your alts size to {amount}!";
	public String msgYouSetAlts = "&6&lPalm &8❙ &aYou have successfully set {player} alt size to {amount}!";
	public String msgUnknownAlt = "&6&lPalm &8❙ &cThis is an unknown alt...";
	public String msgSuccessfullyDespawnedBot = "&6&lPalm &8❙ &aYou have successfully despawned {player} alt #{number}!";
	public String msgFactionIsSystem = "&6&lPalm &8❙ &cYou cannot use this command since you are in a system faction.";
	public String msgNeedCoOrLeader = "&6&lPalm &8❙ &cYou need leader or co-leader to use this command!";
	public List<String> checkForMaterials = MUtil.list("CACTUS", "MOB_SPAWNER");
	public int checkForMaterialsRadius = 5;
	public boolean checkRadiusForMaterial = true;
	public String msgDoesntIncludeMaterial = "&6&lPalm &8❙ &cYou cannot spawn this alt since the chunk doesn't include a spawners.";
	///////////////////////////////////////////////////
	// Inventory
	///////////////////////////////////////////////////
	public String msgGuiNameNotSet = "&6&lPalm &8❙ &7The gui name is not setup for that gui.";
	public String msgGuiFormatNotSet = "&6&lPalm &8❙ &7The gui format is not setup for that gui.";
	public String msgGuiRowLengthNotSame = "&6&lPalm &8❙ &7The gui is not setup correctly. Row lengths do not match.";
	public String msgGuiTooManyRows = "&6&lPalm &8❙ &7There is too many rows configured for this gui type.";
	public String msgGuiDesignNotSet = "&6&lPalm &8❙ &7The gui design is not set! Please contact the developer.";
	public String msgGuiLeaveWindowToEdit = "&6&lPalm &8❙ &7You cannot do that while you're within a gui window.";
	public String failedGuiDisplay = "&6&lPalm &8❙ &c&lERROR 404";
	public long guiClickThrottleDelayMs = 200L;
	///////////////////////////////////////////////////

	public static MConf get() {
		return i;
	}

	@Override
	public MConf load(MConf that) {
		super.load(that);
		return this;
	}
}
