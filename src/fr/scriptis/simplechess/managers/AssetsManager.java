package fr.scriptis.simplechess.managers;

import fr.scriptis.simplechess.utils.ImageUtils;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class AssetsManager {

    private static final String DIRECTORY = "/img/";

    private static final HashMap<String, BufferedImage> images = new HashMap<>();

    /**
     * Load an image from the assets directory.
     *
     * @param name The name of the asset
     * @return The image
     * @throws IOException an exception if the image cannot be loaded
     */
    @Nullable
    public BufferedImage getImageAsset(String name) {
        if (images.containsKey(name))
            return images.get(name);
        try {
            images.put(name, ImageIO.read(Objects.requireNonNull(AssetsManager.class.getResourceAsStream(DIRECTORY + name))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return images.get(name);
    }

    public BufferedImage getSvgAsset(String name, int w, int h) {
        if (images.containsKey(name))
            return images.get(name);
        try {
            ImageUtils img = Objects.requireNonNull(
                            ImageUtils.fromSvg(
                                    Objects.requireNonNull(
                                            AssetsManager.class.getResourceAsStream(DIRECTORY + name)), w, h))
                    .applyAntiAliasingFilter();
            images.put(name, img.getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return images.get(name);
    }

    public void load(String name) {
        if (getImageAsset(name) == null)
            throw new NullPointerException("Image " + name + " not found");
    }

    public void load(String name, int w, int h) {
        if (!name.endsWith(".svg"))
            load(name);
        else if (getSvgAsset(name, w, h) == null)
            throw new NullPointerException("Image " + name + " not found");
    }

    public void removeImageAsset(String name) {
        images.remove(name);
    }
}
