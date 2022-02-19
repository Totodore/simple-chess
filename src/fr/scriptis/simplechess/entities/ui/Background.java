package fr.scriptis.simplechess.entities.ui;

import fr.scriptis.simplechess.utils.ImageUtils;
import fr.scriptis.simplechess.windows.Window;
import fr.scriptis.simplechess.entities.Entity;
import fr.scriptis.simplechess.managers.AssetsManager;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Background extends Entity {

    private BufferedImage image;
    private static final String ASSET = "Background.png";

    public Background(Window window) {
        super(window);
    }

    @SuppressWarnings("unused")
    public static void load() throws IOException {
        assetsManager.load(ASSET);
    }

    @Override
    public void init() {
        image = new ImageUtils(assetsManager.getImageAsset(ASSET)).applyScale(0.5f)
                .applyGaussianBlur(30, 30f).getImage();
        setX(0);
        setY(0);
        setWidth(image.getWidth());
        setHeight(image.getHeight());
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
    }
}
