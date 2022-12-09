package me.vickrum.alts.integration;

public class IntegrationPlaceholderAPI extends PapiExpansionIntegration {
	private static final IntegrationPlaceholderAPI i = new IntegrationPlaceholderAPI();

	public static IntegrationPlaceholderAPI get() {
		return i;
	}

	public PapiExpansionEngine getEngineInstance() {
		return EnginePlaceholderAPI.get();
	}
}
