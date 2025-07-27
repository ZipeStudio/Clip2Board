package me.zipestudio.clip2board;

import net.minecraft.text.*;
import net.minecraft.util.Identifier;
import org.slf4j.*;

import net.fabricmc.api.ModInitializer;

public class Clip2Board implements ModInitializer {

	public static final String MOD_NAME = /*$ mod_name*/ "Clip2Board";
	public static final String MOD_ID = /*$ mod_id*/ "clip2board";
	public static final String YACL_DEPEND_VERSION = /*$ yacl*/ "3.7.1+1.21.6-fabric";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	public static MutableText text(String path, Object... args) {
		return Text.translatable(String.format("%s.%s", MOD_ID, path), args);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("{} Initialized", MOD_NAME);
	}
}