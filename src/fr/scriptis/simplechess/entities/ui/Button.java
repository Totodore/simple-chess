package fr.scriptis.simplechess.entities.ui;

import fr.scriptis.simplechess.entities.Entity;
import fr.scriptis.simplechess.utils.Vector2i;
import fr.scriptis.simplechess.windows.Window;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.function.Consumer;

@Builder
public class Button extends Entity {

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private float fontSize;

    @Getter
    @Setter
    private Color fontColor;

    @Getter
    @Setter
    private Color bgColor;

    @Getter
    @Setter
    private int width;

    @Getter
    @Setter
    private int height;

    @Getter
    @Setter
    private Vector2i position;

    @Getter
    private Consumer<Button> onClick;

    private Graphics2D graphics;

    private CenteredText textEntity;

    @Override
    public void init() {
        Vector2i textPosition = position.translate(width / 2, height / 2);
        textEntity = new CenteredText(window, getText(), textPosition, getFontSize(), getFontColor(), graphics);
        super.setPosition(position);
    }

    public void init(Graphics2D g) {
        init();
        textEntity.init(g);
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawRoundRect(getX() - 10, getY() - 5, getWidth() + 10, getHeight() + 5, 10, 10);
        textEntity.draw(g);
    }
}
