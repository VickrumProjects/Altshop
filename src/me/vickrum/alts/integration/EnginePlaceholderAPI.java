package me.vickrum.alts.integration;

public class EnginePlaceholderAPI extends PapiExpansionEngine {
	private static final EnginePlaceholderAPI i = new EnginePlaceholderAPI();

	public static EnginePlaceholderAPI get() {
		return i;
	}

	public RankUpExpansion getInstance() {
		return new RankUpAPIExpansion();
	}
}
