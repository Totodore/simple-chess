package fr.scriptis.simplechess.entities;

import fr.scriptis.simplechess.managers.AssetsManager;
import fr.scriptis.simplechess.utils.MathUtils;
import fr.scriptis.simplechess.utils.Vector2;
import fr.scriptis.simplechess.utils.Vector2i;
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
    private Vector2i position;

    @Getter
    private int width, height;

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

    public void setPosition(int x, int y) {
        this.position = new Vector2i(x, y);
        setDirty(true);
    }

    public void setPosition(Vector2i point) {
        this.position = new Vector2i(point);
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

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }
}
