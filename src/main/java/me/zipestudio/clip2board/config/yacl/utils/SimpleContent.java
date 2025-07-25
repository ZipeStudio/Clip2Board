package me.zipestudio.clip2board.config.yacl.utils;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public enum SimpleContent {
	NONE("none"),
	IMAGE("png"),
	WEBP("webp"),
	GIF("gif");

	private final String fileExtension;

	SimpleContent(String fileExtension) {
		this.fileExtension = fileExtension;
	}
}
