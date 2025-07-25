package me.zipestudio.clip2board.client;

import org.slf4j.*;

import net.fabricmc.api.ClientModInitializer;

import me.zipestudio.clip2board.Clip2Board;

public class Clip2BoardClient implements ClientModInitializer {

	public static Logger LOGGER = LoggerFactory.getLogger(Clip2Board.MOD_NAME + "/Client");

	static {
		System.setProperty("java.awt.headless", "false");
	}

	@Override
	public void onInitializeClient() {
		LOGGER.info("{} Client Initialized", Clip2Board.MOD_NAME);
	}

}