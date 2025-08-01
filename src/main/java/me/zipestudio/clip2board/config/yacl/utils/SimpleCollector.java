package me.zipestudio.clip2board.config.yacl.utils;

import java.util.function.BooleanSupplier;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class SimpleCollector {

	private SimpleCollector() {
	}

	@Nullable
	public static <T> T getIf(T value, BooleanSupplier condition) {
		if (condition.getAsBoolean()) {
			return value;
		}
		return null;
	}
}
