package me.zipestudio.clip2board.config;

import lombok.*;
import me.zipestudio.clip2board.utils.CodecUtils;
import me.zipestudio.clip2board.utils.ConfigUtils;
import org.slf4j.*;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.loader.api.FabricLoader;

import me.zipestudio.clip2board.Clip2Board;

import java.io.*;
import java.util.concurrent.CompletableFuture;

import static me.zipestudio.clip2board.utils.CodecUtils.option;

@Getter
@Setter
@AllArgsConstructor
public class LeafyConfig {

	public static final Codec<LeafyConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			option("enableMod", true, Codec.BOOL, LeafyConfig::isEnableMod),
			option("saveToFolder", false, Codec.BOOL, LeafyConfig::isSaveToFolder)
	).apply(instance, LeafyConfig::new));

	private boolean enableMod;
	private boolean saveToFolder;

	private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve(Clip2Board.MOD_ID + ".json5").toFile();
	private static final Logger LOGGER = LoggerFactory.getLogger(Clip2Board.MOD_NAME + "/Config");
	private static LeafyConfig INSTANCE;

	private LeafyConfig() {
		throw new IllegalArgumentException();
	}

	public static LeafyConfig getInstance() {
		return INSTANCE == null ? reload() : INSTANCE;
	}

	public static LeafyConfig reload() {
		return INSTANCE = LeafyConfig.read();
	}

	public static LeafyConfig getNewInstance() {
		return CodecUtils.parseNewInstanceHacky(CODEC);
	}

	private static LeafyConfig read() {
		return ConfigUtils.readConfig(CODEC, CONFIG_FILE, LOGGER);
	}

	public void saveAsync() {
		CompletableFuture.runAsync(this::save);
	}

	public void save() {
		ConfigUtils.saveConfig(this, CODEC, CONFIG_FILE, LOGGER);
	}
}
