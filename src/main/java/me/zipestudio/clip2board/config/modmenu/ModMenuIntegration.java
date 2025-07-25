package me.zipestudio.clip2board.config.modmenu;

import com.terraformersmc.modmenu.api.*;

import net.fabricmc.loader.api.*;

import me.zipestudio.clip2board.Clip2Board;
import me.zipestudio.clip2board.client.Clip2BoardClient;
import me.zipestudio.clip2board.config.YACLConfigurationScreen;

public class ModMenuIntegration implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		FabricLoader fabricLoader = FabricLoader.getInstance();
		if (fabricLoader.isModLoaded("yet_another_config_lib_v3")) {
			ModContainer modContainer = fabricLoader.getModContainer("yet_another_config_lib_v3").orElseThrow();
			Version version = modContainer.getMetadata().getVersion();
			try {
				Version requestsVersion = Version.parse(Clip2Board.YACL_DEPEND_VERSION);
				if (version.compareTo(requestsVersion) >= 0) {
					return YACLConfigurationScreen::createScreen;
				}
			} catch (VersionParsingException e) {
				Clip2BoardClient.LOGGER.error("Failed to compare YACL version, tell mod author about this error: ", e);
			}
			return parent -> NoConfigLibraryScreen.createScreenAboutOldVersion(parent, version.getFriendlyString());
		}
		return NoConfigLibraryScreen::createScreen;
	}
}
