package me.zipestudio.clip2board.utils;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import me.zipestudio.clip2board.Clip2Board;
import me.zipestudio.clip2board.config.yacl.utils.SimpleContent;

import java.util.function.Function;

public class ModMenuUtils {

	public static String getOptionKey(String optionId) {
		return String.format("modmenu.option.%s", optionId);
	}

	public static String getCategoryKey(String categoryId) {
		return String.format("modmenu.category.%s", categoryId);
	}

	public static String getGroupKey(String groupId) {
		return String.format("modmenu.group.%s", groupId);
	}

	public static Text getName(String key) {
		return Clip2Board.text(key + ".name");
	}

	public static Text getDescription(String key) {
		return Clip2Board.text(key + ".description");
	}

	public static Identifier getContentId(SimpleContent content, String contentId) {
		return Clip2Board.id(String.format("textures/config/%s.%s", contentId, content.getFileExtension()));
	}

	public static Text getModTitle() {
		return Clip2Board.text("modmenu.title");
	}

	public static Function<Boolean, Text> getEnabledOrDisabledFormatter() {
		return state -> Clip2Board.text("modmenu.formatter.enabled_or_disabled." + state);
	}

	public static Text getNoConfigScreenMessage() {
		return Clip2Board.text("modmenu.no_config_library_screen.message");
	}

	public static Text getOldConfigScreenMessage(String version) {
		return Clip2Board.text("modmenu.old_config_library_screen.message", version, Clip2Board.YACL_DEPEND_VERSION);
	}
}
