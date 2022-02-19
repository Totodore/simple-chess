package fr.scriptis.simplechess.entities;

import fr.scriptis.simplechess.managers.AssetsManager;
import fr.scriptis.simplechess.utils.MathUtils;
import fr.scriptis.simplechess.windows.Window;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.logging.Logger;

@RequiredArgsConstructor
public abstract class Entity {

    protected final Window window;
    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    protected static final AssetsManager assetsManager = new AssetsManager();
    @Getter
    private int x, y, width, height;

    @Getter
    @Setter
    private boolean dirty = true;

    @Getter
    private final int id;

    public Entity(Window window) {
        this.window = window;
        id = MathUtils.generateId();
    }

    public abstract void init();

    public abstract void draw(Graphics2D g);

    public void setPosition(Point point) {
        x = point.x;
        y = point.y;
        setDirty(true);
    }

    public void setX(int x) {
        this.x = x;
        setDirty(true);
    }

    public void setY(int y) {
        this.y = y;
        setDirty(true);
    }

    public void setWidth(int width) {
        this.width = width;
        setDirty(true);
    }

    public void setHeight(int height) {
        this.height = height;
        setDirty(true);
    }
}
