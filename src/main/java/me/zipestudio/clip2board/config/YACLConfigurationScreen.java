package me.zipestudio.clip2board.config;

import dev.isxander.yacl3.api.ConfigCategory;
import lombok.experimental.ExtensionMethod;
import me.zipestudio.clip2board.config.yacl.base.SimpleCategory;
import me.zipestudio.clip2board.config.yacl.base.SimpleOption;
import me.zipestudio.clip2board.config.yacl.utils.SimpleContent;
import net.minecraft.client.gui.screen.Screen;

import me.zipestudio.clip2board.config.yacl.extension.SimpleOptionExtension;
import me.zipestudio.clip2board.config.yacl.screen.SimpleYACLScreen;

@ExtensionMethod(SimpleOptionExtension.class)
public class YACLConfigurationScreen {

    private YACLConfigurationScreen() {
        throw new IllegalStateException("Screen class");
    }

    public static Screen createScreen(Screen parent) {
        LeafyConfig defConfig = LeafyConfig.getNewInstance();
        LeafyConfig config = LeafyConfig.getInstance();

        return SimpleYACLScreen.startBuilder(parent, config::saveAsync)
                .categories(getGeneralCategory(defConfig, config))
                .build();
    }

    private static ConfigCategory getGeneralCategory(LeafyConfig defConfig, LeafyConfig config) {
        return SimpleCategory.startBuilder("general")
                .options(
                        SimpleOption.<Boolean>startBuilder("enableMod")
                                .withDescription(SimpleContent.NONE)
                                .withBinding(defConfig.isEnableMod(), config::isEnableMod, config::setEnableMod, true)
                                .withController()
                                .build()
                        ,
                        SimpleOption.<Boolean>startBuilder("saveToFolder")
                                .withDescription(SimpleContent.NONE)
                                .withBinding(defConfig.isSaveToFolder(), config::isSaveToFolder, config::setSaveToFolder, true)
                                .withController()
                                .build()
                )
                .build();
    }

}


