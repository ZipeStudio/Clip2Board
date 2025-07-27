package me.zipestudio.clip2board.mixin;

import me.zipestudio.clip2board.Clip2Board;
import me.zipestudio.clip2board.config.LeafyConfig;
import me.zipestudio.clip2board.utils.ClipboardUtils;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.function.Consumer;

@Mixin(ScreenshotRecorder.class)
public class ScreenshotRecorderMixin {

    //? if >=1.21.6 {
    @Inject(method = "saveScreenshot(Ljava/io/File;Ljava/lang/String;Lnet/minecraft/client/gl/Framebuffer;ILjava/util/function/Consumer;)V", at = @At("HEAD"), cancellable = true)
    private static void injectScreenshotLogic(File gameDir, @Nullable String fileName, Framebuffer framebuffer, int downscale, Consumer<Text> msgReceiver, CallbackInfo ci) {
        LeafyConfig config = LeafyConfig.getInstance();

        if (!config.isEnableMod()) return;

        ScreenshotRecorder.takeScreenshot(framebuffer, downscale, image -> {
            File screenshotsDir = new File(gameDir, "screenshots");
            screenshotsDir.mkdir();
            File file = fileName == null ? ClipboardUtils.getScreenshotFilename(screenshotsDir) : new File(screenshotsDir, fileName);

            Util.getIoWorkerExecutor().execute(() -> {
                try {
                    ClipboardUtils.copyImageToClipboard(image);

                    if (config.isSaveToFolder()) {
                        image.writeTo(file);

                        Text fileText = Text.literal(file.getName())
                                .formatted(Formatting.UNDERLINE)
                                .styled(style -> style.withClickEvent(new ClickEvent.OpenFile(file.getAbsolutePath())));

                        msgReceiver.accept(Text.translatable("screenshot.success", fileText)
                                .append(" ")
                                .append(Text.translatable("screenshot.success+clipboard").formatted(Formatting.GRAY)));
                    } else {
                        Text fileText = Text.literal(file.getName()).formatted(Formatting.UNDERLINE);
                        msgReceiver.accept(Text.translatable("screenshot.clipboard", fileText));
                    }

                } catch (Exception e) {
                    Clip2Board.LOGGER.warn("Couldn't save clipboard screenshot", e);
                    msgReceiver.accept(Text.translatable("screenshot.failure", e.getMessage()));
                } finally {
                    image.close();
                }
            });
        });

        ci.cancel();
    }
    //?} else if =1.21.5 {
    /*@Inject(method = "saveScreenshot(Ljava/io/File;Ljava/lang/String;Lnet/minecraft/client/gl/Framebuffer;Ljava/util/function/Consumer;)V", at = @At("HEAD"), cancellable = true)
    private static void injectScreenshotLogic(File gameDirectory, String fileName, Framebuffer framebuffer, Consumer<Text> messageReceiver, CallbackInfo ci) {
        LeafyConfig config = LeafyConfig.getInstance();

        if (!config.isEnableMod()) return;

        ScreenshotRecorder.takeScreenshot(framebuffer, image -> {
            File screenshotsDir = new File(gameDirectory, "screenshots");
            screenshotsDir.mkdir();
            File file = fileName == null ? ClipboardUtils.getScreenshotFilename(screenshotsDir) : new File(screenshotsDir, fileName);

            Util.getIoWorkerExecutor().execute(() -> {
                try {
                    ClipboardUtils.copyImageToClipboard(image);

                    if (config.isSaveToFolder()) {
                        image.writeTo(file);

                        Text fileText = Text.literal(file.getName())
                                .formatted(Formatting.UNDERLINE)
                                .styled(style -> style.withClickEvent(new ClickEvent.OpenFile(file.getAbsolutePath())));

                        messageReceiver.accept(Text.translatable("screenshot.success", fileText)
                                .append(" ")
                                .append(Text.translatable("screenshot.success+clipboard").formatted(Formatting.GRAY)));
                    } else {
                        Text fileText = Text.literal(file.getName()).formatted(Formatting.UNDERLINE);
                        messageReceiver.accept(Text.translatable("screenshot.clipboard", fileText));
                    }

                } catch (Exception e) {
                    Clip2Board.LOGGER.warn("Couldn't save clipboard screenshot", e);
                    messageReceiver.accept(Text.translatable("screenshot.failure", e.getMessage()));
                } finally {
                    image.close();
                }
            });
        });

        ci.cancel();
    }
    *///?} else {
    /*@Inject(
            method = "saveScreenshotInner",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void onSaveScreenshotInner(File gameDirectory, @Nullable String fileName, Framebuffer framebuffer, Consumer<Text> messageReceiver, CallbackInfo ci) {

        LeafyConfig config = LeafyConfig.getInstance();

        if (!config.isEnableMod()) return;

        NativeImage nativeImage = ScreenshotRecorder.takeScreenshot(framebuffer);

        File screenshotsDir = new File(gameDirectory, "screenshots");
        screenshotsDir.mkdir();
        File file2 = fileName == null ? ClipboardUtils.getScreenshotFilename(screenshotsDir) : new File(screenshotsDir, fileName);


        Util.getIoWorkerExecutor().execute(() -> {
            try {

                ClipboardUtils.copyImageToClipboard(nativeImage);

                if (config.isSaveToFolder()) {
                    nativeImage.writeTo(file2);

                    Text fileLink = Text.literal(file2.getName())
                            .formatted(Formatting.UNDERLINE)
                            .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file2.getAbsolutePath())));

                    messageReceiver.accept(Text.translatable("screenshot.success", fileLink)
                            .append(" ")
                            .append(Text.translatable("screenshot.success+clipboard")
                                    .formatted(Formatting.GRAY))
                    );
                } else {

                    Text fileLink = Text.literal(file2.getName())
                            .formatted(Formatting.UNDERLINE);

                    messageReceiver.accept(Text.translatable("screenshot.clipboard", fileLink)
                    );

                }
            } catch (Exception exception) {
                Clip2Board.LOGGER.warn("Couldn't save screenshot", exception);
                messageReceiver.accept(Text.translatable("screenshot.failure", exception.getMessage()));
            } finally {
                nativeImage.close();
            }
        });

        ci.cancel();
    }
    *///?}

}
