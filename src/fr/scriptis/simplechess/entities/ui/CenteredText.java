package fr.scriptis.simplechess.entities.ui;

import fr.scriptis.simplechess.entities.Entity;
import fr.scriptis.simplechess.utils.Vector2i;
import fr.scriptis.simplechess.windows.Window;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class CenteredText extends Entity {

    @Getter
    @Setter
    private String text;

    private Vector2i center;
    private float fontSize;
    private Font font;

    @Getter
    @Setter
    private Color color;

    @Setter
    protected Graphics2D graphics;

    public CenteredText(Window window, String text, Vector2i center, float fontSize, Color color, Graphics2D graphics) {
        super(window);
        this.text = text;
        this.center = center;
        this.fontSize = fontSize;
        this.graphics = graphics;
        this.color = color;
    }

    @Override
    public void init() {
        graphics.setFont(font.deriveFont(fontSize));
        computeSize();
    }

    public void init(Graphics2D g) {
        graphics = g;
        font = graphics.getFont();
        init();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setFont(font.deriveFont(fontSize));
        g.setColor(color);
        g.drawString(text, position.x, position.y);
    }

    public void setText(String text) {
        this.text = text;
        setDirty(true);
        computeSize();
    }

    @Override
    public void setPosition(Vector2i point) {
        center = new Vector2i(point);
        computeSize();
    }

    @Override
    public Vector2i getPosition() {
        return center;
    }

    @Override
    public int getX() {
        return center.x;
    }
    public int getY() {
        return center.y;
    }

    @Override
    public void setPosition(int x, int y) {
        center = new Vector2i(x, y);
        computeSize();
    }

    public void setColor(Color color) {
        this.color = color;
        setDirty(true);
    }

    private void computeSize() {
        if (graphics == null)
            return;
        setWidth(graphics.getFontMetrics().stringWidth(text));
        setHeight(graphics.getFontMetrics().getHeight());

        position = new Vector2i(center.x - getWidth() / 2, center.y - getHeight() / 2);
    }
}
