package me.zipestudio.clip2board.utils;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;

import java.awt.Toolkit;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;

public class ClipboardUtils {

    public static void copyImageToClipboard(NativeImage image) {
        BufferedImage bufferedImage = nativeImageToBufferedImage(image);
        TransferableImage trans = new TransferableImage(bufferedImage);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans, null);
    }

    public static BufferedImage nativeImageToBufferedImage(NativeImage nativeImage) {
        BufferedImage image = new BufferedImage(nativeImage.getWidth(), nativeImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < nativeImage.getHeight(); y++) {
            for (int x = 0; x < nativeImage.getWidth(); x++) {
                //? if >=1.21.2 {
                image.setRGB(x, y, nativeImage.getColorArgb(x, y));
                //?} else {
                /*image.setRGB(x, y, nativeImage.getColor(x, y));
                *///?}
            }
        }
        return image;
    }

    public static File getScreenshotFilename(File directory) {
        String baseName = Util.getFormattedCurrentTime();
        int i = 1;
        while (true) {
            File file = new File(directory, baseName + (i == 1 ? "" : "_" + i) + ".png");
            if (!file.exists()) {
                return file;
            }
            ++i;
        }
    }

    static class TransferableImage implements Transferable {
        private final Image image;

        public TransferableImage(Image image) {
            this.image = image;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] { DataFlavor.imageFlavor };
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.imageFlavor.equals(flavor);
        }

        public @NotNull Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (!DataFlavor.imageFlavor.equals(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return image;
        }
    }

}